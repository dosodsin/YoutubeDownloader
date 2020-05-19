import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException, YoutubeException, InterruptedException {
        String videoId="dQw4w9WgXcQ";
        YoutubeDownloader downloader=new YoutubeDownloader();
        YoutubeVideo video=downloader.getVideo(videoId);
        VideoDetails details=video.details();
        System.out.println("title "+details.title());
        System.out.println("Description "+details.description());

        List<AudioVideoFormat> videoWithAudioFormats=video.videoWithAudioFormats();
        videoWithAudioFormats.forEach(it->{
            System.out.println("VideoAudio "+it.videoQuality()+": "+it.url());
        });

        List<AudioFormat> audioFormats=video.audioFormats();
        audioFormats.forEach(it->{
            System.out.println("Audio "+it.audioQuality()+": "+it.url());
        });

        File outDirVideoWithAudio=new File("video_audio");
        video.downloadAsync(videoWithAudioFormats.get(0), outDirVideoWithAudio, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("\b\b\b\b\b%d%%", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.printf("\nFinish video: %s",file);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.printf("Error: "+throwable.getMessage());
            }
        });

        File outDirAudio=new File("audio");
        video.downloadAsync(audioFormats.get(0), outDirAudio, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("\b\b\b\b\b%d%%", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.printf("\nFinish audio: %s",file);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.printf("Error: "+throwable.getMessage());
            }
        });

        Thread.currentThread().join();
    }


}

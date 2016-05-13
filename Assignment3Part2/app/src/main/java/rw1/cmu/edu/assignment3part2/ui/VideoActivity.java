package rw1.cmu.edu.assignment3part2.ui;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import rw1.cmu.edu.assignment3part2.R;

/**
 * Created by Rui on 4/1/16.
 */
public class VideoActivity extends AppCompatActivity {

    private VideoView vv_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        vv_video = (VideoView)findViewById(R.id.videoView);
    }

    // call buy VEDIO1 button
    public void playvideo(View view){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vedio1);
        vv_video.setVideoURI(uri);
        vv_video.start();
    }

    // call buy VEDIO2 button
    public void playvideo1(View view){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vedio2);
        vv_video.setVideoURI(uri);
        vv_video.start();
    }

    // call buy STOP button
    public void stopvideo(View view){

        vv_video.pause();

        if (vv_video != null && vv_video.isPlaying()) {
            //  stop vedio
        }
    }

}

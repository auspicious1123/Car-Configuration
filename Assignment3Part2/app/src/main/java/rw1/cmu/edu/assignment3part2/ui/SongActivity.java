package rw1.cmu.edu.assignment3part2.ui;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rw1.cmu.edu.assignment3part2.R;

/**
 * Created by Rui on 3/31/16.
 */
public class SongActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private TextView text;
    private Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        text = (TextView)findViewById(R.id.textView7);
//        button5 = (Button) findViewById(R.id.button5);
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                song1(v);
//            }
//        });

    }

    // call buy SONG1 button
    public void song1(View view){
        mp = MediaPlayer.create(this,R.raw.song1);
        try {
            if(mp != null)
            {
                mp.stop();
            }
            mp.prepare();
            mp.start();
            text.setText("Song1:Thinking you is palying");
        } catch (Exception e) {
            text.setText("Something Wrong");
            e.printStackTrace();
        }
    }

    // call buy SONG2 button
    public void song2(View view){
        mp = MediaPlayer.create(this,R.raw.song2);
        try {
            if(mp != null)
            {
                mp.stop();
            }
            mp.prepare();
            mp.start();
            text.setText("Song2:Cannot forget is playing");
        } catch (Exception e) {
            text.setText("Something Wrong");
            e.printStackTrace();
        }
    }

    // call buy SONG3 button
    public void song3(View view){
        mp = MediaPlayer.create(this,R.raw.song3);
        try {
            if(mp != null)
            {
                mp.stop();
            }
            mp.prepare();
            mp.start();
            text.setText("Song3:I am busy is playing");
        } catch (Exception e) {
            text.setText("Something Wrong");
            e.printStackTrace();
        }
    }

    public void stopplay(View view){
        try {
            if(mp !=null)
            {
                mp.stop();
                text.setText("Stop Playing");
            }
        } catch (Exception e) {
            text.setText("Something Wrong");
            e.printStackTrace();
        }

    }
}

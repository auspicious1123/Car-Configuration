package rw1.cmu.edu.assignment3part2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import rw1.cmu.edu.assignment3part2.R;

/**
 * Created by Rui on 3/31/16.
 */
public class ImageActivity extends AppCompatActivity {
    private ImageView image;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        image = (ImageView)findViewById(R.id.imageView);
        index = 0;
    }

    // call by prev button, show previous picture
    public void prevpic(View view){
        if (index == 0){
            index = 1;
            image.setImageResource(R.drawable.image2);
        } else {
            index = 0;
            image.setImageResource(R.drawable.image1);
        }
    }

    // call by next button, show next picture
    public void nextpic(View view){
        if (index == 1){
            index = 2;
            image.setImageResource(R.drawable.image1);
        } else {
            index = 1;
            image.setImageResource(R.drawable.image2);
        }
    }

}

package rw1.cmu.edu.assignment3part2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import rw1.cmu.edu.assignment3part2.R;


public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private int index;
    private Button button2;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String message = "Titile: Love A-lin " + "Content: Nice to meet you";
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Send Email to A-Lin@gmail.com").setMessage(message).setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();

                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("A-Lin@gmail.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "Love you");
                data.putExtra(Intent.EXTRA_TEXT, "This is content, Nice to meet you!");
//                startActivity(data);
            }
        });

        image = (ImageView)findViewById(R.id.imageView);
        index = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // CALL by NEXT Picture button
    public void changePic(View view){
        if (index == 0){
            image.setImageResource(R.drawable.image2);
            index = 1;
        } else if (index == 1){
            image.setImageResource(R.drawable.image1);
            index = 2;
        } else {
            image.setImageResource(R.drawable.image3);
            index = 0;
        }
    }

    // start song activity
    public void songpage(View view){
        Intent intent = new Intent(MainActivity.this, SongActivity.class);
        startActivity(intent);
    }

    // start image activity
    public void imagepage(View view){
        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
        startActivity(intent);
    }

    // start video activity
    public void videopage(View view){
        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        startActivity(intent);
    }
}

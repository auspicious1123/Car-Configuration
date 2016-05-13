package ds.cmu.edu.interestingtag;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class InterestingPictureTag extends AppCompatActivity {
    String searchTerm;
    String pictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPictureTag.
         */
        final InterestingPictureTag ma = this;

        /**
         *  New Button for api.
         */
        Button api = (Button)findViewById(R.id.api);
        api.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {

//                gp.search(searchTerm, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
                GetImageTag tag = new GetImageTag();
                tag.searchTagFromAPI(searchTerm, ma);
            }
        });

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);
        searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                GetPicture gp = new GetPicture();
                gp.search(searchTerm, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });


    }

    /*
     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void pictureReady(Bitmap picture) {
        ImageView pictureView = (ImageView)findViewById(R.id.interestingPicture);
        TextView searchView = (EditText)findViewById(R.id.searchTerm);
        TextView status = (TextView)findViewById(R.id.status);

//        TextView imageTagXML
        if (picture != null) {
            pictureView.setImageBitmap(picture);
            pictureView.setVisibility(View.VISIBLE);
            status.setText("Here is a picture of a " + searchView.getText().toString());

        } else {
            pictureView.setImageResource(R.mipmap.ic_launcher);
            pictureView.setVisibility(View.INVISIBLE);
            status.setText("Sorry, I could not find a picture of a " + searchView.getText().toString());

        }
        searchView.setText("");
        pictureView.invalidate();
    }

    /*
     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void tagReady(String tag) {
        TextView searchView = (EditText)findViewById(R.id.searchTerm);
        TextView imageTagXML = (TextView)findViewById(R.id.imageTagJson);
        if (tag != null) {
            imageTagXML.setText("Image " + searchView.getText().toString() + " tag is: \n" + tag);
        } else {
            imageTagXML.setText("Cannot find tag of " + searchView.getText().toString());
        }

    }
}

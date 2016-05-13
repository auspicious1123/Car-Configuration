package ds.cmu.edu.interestingtag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Rui on 3/29/16.
 */
public class GetImageTag {

    private String pictureURL = null;
    private InterestingPictureTag ip = null;

    public void searchTagFromAPI(String searchTerm, InterestingPictureTag ip) {
        this.ip = ip;
        new AsyncSearchTag().execute(searchTerm);
    }

    /*
     * AsyncSearchTag provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncSearchTag extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return search(urls[0]);
        }

        protected void onPostExecute(String tag) {
            ip.tagReady(tag);
        }

        /*
         * Search Flickr.com for the searchTerm argument, and return a image tag String.
         * First: I used the interesting pciture code as the first part to get the image url based on researchTerm
         * Second: I save the url and send it to AlchomyAPI to get the response and extract the image tag.
         * Third: I send the image tag to user UI.
         */
        private String search(String searchTerm) {

            // First: get url from flickr based on searchTerm.
            Document doc = getRemoteXML("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=fd710fe32973eca0b606e626e5fe56ba&is_getty=true&tags=" + searchTerm);
            NodeList nl = doc.getElementsByTagName("photo");
            if (nl.getLength() == 0) {
                return null; // no pictures found
            } else {
                int pic = new Random().nextInt(nl.getLength()); //choose a random picture
                Element e = (Element) nl.item(pic);
                String farm = e.getAttribute("farm");
                String server = e.getAttribute("server");
                String id = e.getAttribute("id");
                String secret = e.getAttribute("secret");
                pictureURL = "http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";

            }

            // Second: At this point, we have the URL of the picture that resulted from the search.  Now
            // I give it to my AlchomyAAPI as input and get my whole url to get response from AlchomyAPI.

            StringBuffer stringBuffer = null;
            String outPutTag = "";
            try {
                // this is the whole url to my heroku server.
                String apiUrl = "https://project4task2.herokuapp.com/imageTaggingSerlvet?imageURL=" + pictureURL;
                System.out.println("Tag image url is :" + pictureURL);
                URL url = new URL(apiUrl);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();


                // Third: get XML file of reponse page and extract tag.
                // Send back tag String to user.
                Document document = getRemoteXML(apiUrl);

                NodeList getUrl = document.getElementsByTagName("url");
                outPutTag += "Image url is:\n " + getUrl.item(0).getTextContent().trim() + "\n\n";

                NodeList usage = document.getElementsByTagName("usage");
                outPutTag += usage.item(0).getTextContent() + "\n\n";

                NodeList tag = document.getElementsByTagName("imageKeywords");
                outPutTag += "imageKey saved word is :\n" + tag.item(0).getTextContent().trim() + "\n";  // send to show on phone
                System.out.println("***********tag********" + usage.item(0).getTextContent());

//
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                return outPutTag;
            }

        /*
         * Given a url that will request XML, return a Document with that XML, else null
         */
        private Document getRemoteXML(String url) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(url);
                return db.parse(is);
            } catch (Exception e) {
                System.out.print("Yikes, hit the error: " + e);
                return null;
            }
        }

        private Bitmap getRemoteImage(URL url) {
            try {
                final URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


    }

}

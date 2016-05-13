package rw1.cmu.edu.mylocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView locationView;
    Location location = null;
    LocationManager locationManager;
    LocationListener locationListener;
    private IntentFilter sendFilter;
    //get Network location
    private String networkProvider = LocationManager.NETWORK_PROVIDER;
    //get gps location
    private String GpsProvider = LocationManager.GPS_PROVIDER;

    private static final String PHONENUM  = "4127261582"; // hardcode phone number


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define ui
        Button b1 = (Button) findViewById(R.id.button);
        locationView = (TextView) findViewById(R.id.position_text_view);

        //click button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update location
                initLocation(MainActivity.this);
                send();
            }
        });
    }

    //get location
    private void initLocation(Context mContext) {
        //get  LocationManager
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // frist check gps provider then network provider
        if (startLocation(networkProvider, mContext)) {
            updateLocation(location, mContext);
        } else
            // then check network provider
            if (startLocation(GpsProvider, mContext)) {
                updateLocation(location, mContext);
            } else {
                    new WrongException();
                    Log.e("No Location", "No location");
            }
    }

    /**
     * get location object
     *
     * @param provider
     * @param mContext
     * @return
     */
    private boolean startLocation(String provider, final Context mContext) {
        // Check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new WrongException();
            Log.e("Not permission", "No permission");
            return false;
        }
        Location location = locationManager.getLastKnownLocation(provider);

            // listening location
        locationListener = new LocationListener() {
            // if location change
            @Override
            public void onLocationChanged(Location location) {
                System.out.println(location.toString());
                updateLocation(location, mContext);
            }

            // exception for no valid provider
            @Override
            public void onProviderDisabled(String arg0) {
                new WrongException();
                Log.e("No valid provider", "No valid provider");
            }

            // provider valid
            @Override
            public void onProviderEnabled(String arg0) {
                System.out.println(arg0);
            }

            // provider change
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                System.out.println("onStatusChanged");
            }
        };

        // 500ms update and ignore change
        locationManager.requestLocationUpdates(provider, 500, 0, locationListener);

//		check location
        if (location != null) {
            this.location = location;
            return true;
        }
        return false;

    }
    // update location and show result in android textview
    private void updateLocation(Location location, Context mContext) {
        if (location != null) {
            showLocation(location);
            // check permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                new WrongException();
                Log.e("Not permission", "No permission");
                return;
            }
            locationManager.removeUpdates(locationListener);
        } else {
            new WrongException();
        }
    }

    protected void onDestroy() {
        //close lisener
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            return;
        }
        locationManager.removeUpdates(locationListener);
        super.onDestroy();
    }

    private void showLocation(Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        locationView.setText(currentPosition);
    }

    public void send(){
        SmsManager smsManager = SmsManager.getDefault();

        //if location is null, display a fail toast
        // or send the location to the PhoneNum and display success toast

        if (location == null){
            Toast.makeText(MainActivity.this, "Failed seed message!", Toast.LENGTH_LONG).show();
            Log.e("Failed", "No location");
        } else{
            smsManager.sendTextMessage(PHONENUM,null,locationView.getText().toString(),null,null);
            Toast.makeText(MainActivity.this, "Sent to " + PHONENUM, Toast.LENGTH_LONG).show();
        }
    }


}

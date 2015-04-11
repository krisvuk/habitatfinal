package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class profile extends Activity {

    GPSTracker gps;
    private double long1, lat1;
    ListView listView;

    public static double haversine(
            double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d*1000.00;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView image = (ImageView) findViewById(R.id.profileView);
        image.setImageResource(R.drawable.user_image);
        ImageView image2 = (ImageView) findViewById(R.id.carView);
        image2.setImageResource(R.drawable.car_image);
        ImageView image3 = (ImageView) findViewById(R.id.homeView);
        image3.setImageResource(R.drawable.home_image);
        gps = new GPSTracker(profile.this);
        if(gps.canGetLocation()) {

            lat1 = gps.getLatitude();
            long1 = gps.getLongitude();
        }
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if(gps.canGetLocation()) {
                                gps = new GPSTracker(profile.this);
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                double distance = haversine(lat1, long1, latitude, longitude);
                                Toast.makeText(
                                        getApplicationContext(),
                                        String.valueOf(distance) + " meters", Toast.LENGTH_LONG).show();
                                long1 = longitude;
                                lat1 = latitude;
                            } else {
                                gps.showSettingsAlert();
                            }
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, 10000);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_dashboard:
                Intent i = new Intent(this, dashboard.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

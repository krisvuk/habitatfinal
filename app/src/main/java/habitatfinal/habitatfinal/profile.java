package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class profile extends Activity {

    GPSTracker gps;
    private double long1, lat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Old Lat: " + lat1 + "Old Long: " + long1 +
                                        "\n" + "New Lat: " + latitude + "New Long: " + longitude, Toast.LENGTH_LONG).show();
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

        timer.schedule(doAsynchronousTask, 0, 30000);



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

package habitatfinal.habitatfinal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class profile extends Activity {

    GPSTracker gps;
    private double startingLongitude, startingLatitude;
    private double startingLong, startingLat, endingLong, endingLat;
    boolean moving = false;

    ListView listView;
    String emailAdd;

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
        return d;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        gps = new GPSTracker(profile.this);
        emailAdd = getIntent().getStringExtra("email");
        Toast.makeText(this, emailAdd, Toast.LENGTH_LONG).show();
        if(gps.canGetLocation()) {

            startingLatitude = gps.getLatitude();
            startingLongitude = gps.getLongitude();
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
                                double endingLatitude = gps.getLatitude();
                                double endingLongitude = gps.getLongitude();
                                double distance = haversine(startingLatitude, startingLongitude, endingLatitude, endingLongitude);
                                double speed = distance / 0.01666666666;

                                if(speed > 25.0){

                                    //check if this is the first time the user started to move
                                    if(moving == false){
                                        startingLong = startingLongitude;
                                        startingLat = startingLatitude;
                                    }

                                    moving = true;
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Moving at: " + String.valueOf(speed) + " km/h", Toast.LENGTH_LONG).show();

                                }
                                else{
                                    if(moving == true){
                                        endingLong = endingLongitude;
                                        endingLat = endingLatitude;
                                        /*
                                        Plug in the distances into the distance matrix to calculate distance
                                        */
                                    }
                                    moving = false;
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "User is not moving", Toast.LENGTH_LONG).show();
                                }
                                startingLongitude = endingLongitude;
                                startingLatitude = endingLatitude;
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

        UserData user = (UserData)getApplication();
        Log.d("Email: ", user.getEmail());
        Log.d("First name: ", user.getFirstName());
        Log.d("Last name: ", user.getLastName());
        ArrayList<Car> cars = new ArrayList<Car>();
        for(int i = 0; i < user.getCars().size(); i++){
            cars.add(user.getCars().get(i));
        }
        double emissionsPerKm = Double.parseDouble(cars.get(0).getEmissions());

        emissionsPerKm =  emissionsPerKm * 30.0;

        TextView email = (TextView) findViewById(R.id.email);
        TextView first_name = (TextView) findViewById(R.id.firstName);
        TextView last_name = (TextView) findViewById(R.id.lastName);
        TextView emissions = (TextView) findViewById(R.id.emissions);

        email.setText(user.getEmail());
        first_name.setText(user.getFirstName());
        last_name.setText(user.getLastName());
        emissions.setText("You have produced " + String.valueOf(emissionsPerKm) + " Grams of GHG today.");


        Log.d("Last name: ", user.getLastName());


    }
    public void userClick(View view){
        Intent i = new Intent(this, register.class);

        //addUser = new AddUser();
        i.putExtra("flag", 1);
        i.putExtras(getIntent());

        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void carClick(View view){
        Intent i = new Intent(this, transportation_survey.class);

        //addUser = new AddUser();
        i.putExtra("flag", 1);
        i.putExtras(getIntent());

        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void homeClick(View view){
        Intent i = new Intent(this, home_survey.class);

        //addUser = new AddUser();
        i.putExtra("flag", 1);
        i.putExtras(getIntent());

        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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

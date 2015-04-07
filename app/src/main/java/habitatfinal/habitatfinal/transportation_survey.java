package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;


public class transportation_survey extends Activity {


    Spinner manufacture, model, year, model_type;
    ImageView nextButton;
    CarAsyncTask lister;
    VehicleIdAsyncTask vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_survey);
        year = (Spinner) findViewById(R.id.year_spinner);
        manufacture = (Spinner) findViewById(R.id.manufacturers_spinner);
        model = (Spinner) findViewById(R.id.models_spinner);
        model_type = (Spinner) findViewById(R.id.model_type_spinner);
        nextButton = (ImageView) findViewById(R.id.transportation_next);
        lister = new CarAsyncTask(year, this);
        lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/year");
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                lister = new CarAsyncTask(manufacture, transportation_survey.this);
                lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/make?year="+year.getSelectedItem().toString());
                manufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        lister = new CarAsyncTask(model, transportation_survey.this);
                        String s = manufacture.getSelectedItem().toString();
                        String d[] = new String[10];
                        if(s.contains(" "))
                        {
                            d = s.split(" ");
                            s = d[0] +"%20" + d[1];
                        }
                        Log.i("Manufacture", s);
                        lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year="+year.getSelectedItem().toString()+"&make="+s);
                        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                vid = new VehicleIdAsyncTask(model_type, transportation_survey.this);
                                String s = manufacture.getSelectedItem().toString();
                                String d[] = new String[10];
                                if(s.contains(" "))
                                {
                                    d = s.split(" ");
                                    s = d[0] +"%20" + d[1];
                                }
                                String s2 = model.getSelectedItem().toString();
                                String d2[] = new String[10];
                                if(s2.contains(" "))
                                {
                                    d2 = s2.split(" ");
                                    s2 = d2[0] +"%20" + d2[1];
                                }
                                Log.i("Model", s2);
                                vid.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/options?year="+year.getSelectedItem().toString()+"&make="+ s +"&model="+s2);
                                nextButton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        model.setVisibility(View.INVISIBLE);

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                manufacture.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void onClick(View view) {

        // demo #2, *4: implicit intent (and use of an intent filter)
        // - action name declared by an intent filter: "net.learn2develop.SecondActivity"
        Intent dataIntent = new Intent(this, home_survey.class);
        dataIntent.putExtras(getIntent());
        List<Car> v = vid.getVehicle();
        int vehicleid = 0;
        for(Car i:v)
        {
            if(i.getCarType() == model_type.getSelectedItem().toString())
            {
                vehicleid = i.getCarId();
            }
        }
        Log.i("Vehicle Id", String.valueOf(vehicleid));
        // the putExtra( ) method
        dataIntent.putExtra( "Manufacture", manufacture.getSelectedItem().toString() );
        dataIntent.putExtra( "Model", model.getSelectedItem().toString() );
        dataIntent.putExtra("VehicleId", vehicleid);

        startActivity(dataIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transportation_survey, menu);
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
}

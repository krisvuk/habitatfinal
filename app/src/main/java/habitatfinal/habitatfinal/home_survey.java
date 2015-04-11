package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


public class home_survey extends Activity {

    private Spinner water, heat, electricity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_survey);
        water = (Spinner) findViewById(R.id.water_spinner);
        heat = (Spinner) findViewById(R.id.heat_spinner);
        electricity = (Spinner) findViewById(R.id.electricity_spinner);

        TypedArray gen_array = getResources().obtainTypedArray(R.array.pricerange);
        ArrayList<String> water_array= new ArrayList<String>(0);
        water_array.add("Your Monthly Water Bill");
        ArrayList<String> heat_array = new ArrayList<>(0);
        ArrayList<String> electricity_array = new ArrayList<>(0);

        heat_array.add("Your Monthly Heating Bill");
        electricity_array.add("Your Monthly Electricity Bill");
        for(int i = 0; i < gen_array.length(); i++)
        {
            water_array.add(gen_array.getString(i));
        }

        for(int i = 0; i < gen_array.length(); i++)
        {
            heat_array.add(gen_array.getString(i));
        }

        for(int i = 0; i < gen_array.length(); i++)
        {
            electricity_array.add(gen_array.getString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, water_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        water.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heat_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heat.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, electricity_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electricity.setAdapter(adapter);
    }

    public void click_finish(View view){
        Intent dataIntent = new Intent(this, dashboard.class);
        dataIntent.putExtras(getIntent());
        AddUser newUser = new AddUser();
        AddCar newCar = new AddCar();
        User u = (User) getIntent().getSerializableExtra("User Info");
        CarAdd c = (CarAdd) getIntent().getSerializableExtra("Car Info");
        Log.i("Car Id: ", u.getEmail());
        Log.i("Car Id: ", c.getCarId());
        newUser.execute(u);
        newCar.execute(c);
        startActivity(dataIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_survey, menu);
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

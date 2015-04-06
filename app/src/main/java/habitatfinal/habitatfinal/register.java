package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;


public class register extends Activity {
    CountryAPI country;
    AddUser addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Spinner country_spinner = (Spinner) findViewById(R.id.spinner);
        country = new CountryAPI(country_spinner, this);
        country.execute("https://projectearthspirit.appspot.com/_ah/api/countries/v1/countries");
        addUser = new AddUser();
        addUser.execute();
        Log.i("", "Well, you made it this far");
    }

    public void next_step_click(View view){
        Intent i = new Intent(this, transportation_survey.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

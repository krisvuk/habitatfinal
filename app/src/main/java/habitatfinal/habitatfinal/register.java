package habitatfinal.habitatfinal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;


public class register extends Activity {
    CountryAPI country;
    AddUser addUser;
    int flag;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_register);
        flag = i.getIntExtra("flag", 0);
        Spinner country_spinner = (Spinner) findViewById(R.id.country);
        country = new CountryAPI(country_spinner, this);
        country.execute("https://projectearthspirit.appspot.com/_ah/api/countries/v1/countries");
        if(flag == 1)
        {
            //userEmail = i.getStringExtra("email");
            /*

            TODO: Get email info and make it global to get user information and populate data fields with information
             */
        }
        Log.i("", "Well, you made it this far");
    }

    public void next_step_click(View view){
        Intent i = new Intent(this, transportation_survey.class);

        //addUser = new AddUser();
        ArrayList<String> newUser = new ArrayList<>(0);
        Spinner country_spinner = (Spinner) findViewById(R.id.country);
        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        EditText re_password = (EditText)findViewById(R.id.repassword);
        EditText first_name = (EditText)findViewById(R.id.first_name);
        EditText last_name = (EditText)findViewById(R.id.last_name);
        if(password.getText().toString().equals(re_password.getText().toString())) {
            newUser.add(email.getText().toString());
            newUser.add(password.getText().toString());
            newUser.add(first_name.getText().toString());
            newUser.add(last_name.getText().toString());
            newUser.add(country_spinner.getSelectedItem().toString());
        }
        if(flag == 1)
        {
            /*
            TODO: Edit Information accordingly
             */
            startActivity(new Intent(this, profile.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        else {
            User newCreate = new User(newUser);
            Bundle b = new Bundle();
            b.putSerializable("User Info", newCreate);
            i.putExtra("email", newCreate.getEmail());
            i.putExtras(b);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
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

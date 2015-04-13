package habitatfinal.habitatfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class loginpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
    }

    public void click_register(View view){
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }

    public void click_login(View view){
//        Intent i = new Intent(this, dashboard.class);
        EditText em = (EditText) findViewById(R.id.emailLogin);
        //i.putExtra("email", em.getText().toString());
        GetUserData loggedInUser = new GetUserData();
        loggedInUser.execute(em.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loginpage, menu);
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

    private void userDataFromAsyncTask(String results) {
//        UserData user = (UserData)getApplication();
//        user.setEmail(results);
//        Intent i = new Intent(this, dashboard.class);
//        startActivity(i);
    }

    public class GetUserData extends AsyncTask<String, Void, String> {

        public String email, firstName, lastName, country, result;
        InputStream inputStream = null;
        public ArrayList<String> cars;
        public ArrayList<Float> emissions;


        @Override
        protected String doInBackground(String... params) {
            email = params[0];
            String url = "https://projectearthspirit.appspot.com/_ah/api/users/v1/getuser?email="+email;
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    Log.d("InputStream", result);
                }
                else{
                    result = "Did not work!";
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent();
            UserData user = (UserData)getApplication();
            user.setEmail(result);
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                user.setEmail(jObject.getString("email"));
                user.setFirstName(jObject.getString("firstName"));
                user.setLastName(jObject.getString("lastName"));
                GetUserCars userCars = new GetUserCars();
                userCars.execute(jObject.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public class GetUserCars extends AsyncTask<String, Void, String> {

        public String email, result;
        InputStream inputStream = null;
        public ArrayList<String> cars;
        public ArrayList<Float> emissions;


        @Override
        protected String doInBackground(String... params) {
            email = params[0];
            String url = "https://projectearthspirit.appspot.com/_ah/api/users/v1/cars?email="+email;
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    Log.d("InputStream", result);
                }
                else{
                    result = "Did not work!";
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent();
            UserData user = (UserData)getApplication();
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                JSONArray jsonCars = jObject.getJSONArray("cars");
                for(int a = 0; a < jsonCars.length(); a++){
                    Car car = new Car();
                    JSONObject objectInArray = jsonCars.getJSONObject(a);
                    car.setMake(objectInArray.getString("make"));
                    car.setModel(objectInArray.getString("model"));
                    car.setYear(objectInArray.getString("year"));
                    car.setEmissions(objectInArray.getString("emissions"));
                    Log.d("1",objectInArray.getString("make"));
                    user.addCar(car);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i.setClass(getApplicationContext(),dashboard.class);
            startActivity(i);
        }


    }
}
//the path that the program takes is backwards - fix that
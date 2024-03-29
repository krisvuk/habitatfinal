package habitatfinal.habitatfinal;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by admin on 15-04-12.
 */
public class LoggedInUser extends AsyncTask<String, Void, String> {

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
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

    }


}

package habitatfinal.habitatfinal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by admin on 4/3/2015.
 */

public class CarsAPI extends AsyncTask<String, Void, String> {

    public String response;
    public String url2 = "https://projectearthspirit.appspot.com/_ah/api/cars/v1/allcars";

    @Override
    protected String doInBackground(String... url) {
        String json = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url2);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            json = client.execute(httpget, responseHandler);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response = json;
        return response;
    }

    @Override
    protected void onPostExecute(String response) {

    }


}

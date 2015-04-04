package habitatfinal.habitatfinal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by sandeep on 2015-04-03.
 */
public class CountryAPI extends AsyncTask<String, Void, ArrayList<String>> {
    private Spinner s4;
    private Context parent;

    public CountryAPI(Spinner s, Context c)
    {
        s4 = s;
        parent = c;

    }
    @Override
    protected ArrayList<String> doInBackground(String... url) {
        String json = null;
        JSONArray res = new JSONArray();
        ArrayList<String> result = new ArrayList<>(0);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url[0]);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            json = client.execute(httpget, responseHandler);
            try {
                JSONObject jObject = new JSONObject(json);
                res = jObject.getJSONArray("countries");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < res.length(); i++)
        {
            try {
                result.add(res.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> response) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_list_item_1, response);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(adapter);
    }
}
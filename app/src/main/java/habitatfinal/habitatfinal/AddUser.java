package habitatfinal.habitatfinal;

/**
 * Created by admin on 4/4/2015.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AddUser extends AsyncTask<String, Void, String> {

   public String url = "https://projectearthspirit.appspot.com/_ah/api/users/v1/createuser";

    @Override
    protected String doInBackground(String... params) {
        JSONObject jsonobj = new JSONObject();
        try {
            jsonobj.put("email", "kris@username.com");
            jsonobj.put("firstName", "Kris");
            jsonobj.put("lastName", "Vukasinovic");
            jsonobj.put("pw_hash", "password");
            jsonobj.put("country", "Canada");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(url);
            StringEntity se = new StringEntity(jsonobj.toString());
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
            httppostreq.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppostreq);

            Log.i("Json", jsonobj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

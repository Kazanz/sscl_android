package kazanski.com.sscl_app;

import android.content.Context;
import android.content.Entity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazanz on 5/1/16.
 */
public class RetrieveWaivers extends AsyncTask<Void, Void, String> {

    private ArrayList<Waiver> waivers;
    private WaiverDelegate delegate;

    public RetrieveWaivers(WaiverDelegate delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        String HostUrl="http://sscl.info/api/waivers/";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(HostUrl);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity data = response.getEntity();
            String result;
            result = EntityUtils.toString(data);
            JSONArray jArray = new JSONArray(result);
            this.waivers = createWaivers(jArray);
        } catch (IOException e) {
            Log.d("DEBUG", String.valueOf(e));
            Log.d("DEBUG", "COULD NOT GET FROM SERVER!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Waiver> createWaivers(JSONArray jArray) {
        List<Waiver> waiverObjects = new ArrayList<Waiver>();
        for (int i=0; i < jArray.length(); i++)
        {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                // Pulling items from the array
                int id = oneObject.getInt("pk");
                String name = oneObject.getString("name");
                boolean confirmed = oneObject.getBoolean("confirmed");
                String image = oneObject.getString("image");
                waiverObjects.add(new Waiver(id, name, confirmed, image));
            } catch (JSONException e) {
                Log.d("DEBUG", "JSON Object failed to parse: " + String.valueOf(i));
            }
        }
        return (ArrayList<Waiver>) waiverObjects;
    }

    @Override
    protected void onPostExecute(String message) {
        try {
            delegate.asyncComplete(this.waivers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

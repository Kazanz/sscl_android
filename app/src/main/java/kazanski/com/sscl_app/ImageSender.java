package kazanski.com.sscl_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazanz on 5/1/16.
 */
public class ImageSender extends AsyncTask<Void, Void, String> {

    private int pk;
    private String image;

    public ImageSender(int pk, Bitmap image) {
        super();
        this.pk = pk;
        this.image = convertImageToBase64(image);
    }

    @Override
    protected String doInBackground(Void... params) {
        String HostUrl="http://sscl.info/api/image/";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(HostUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("image", this.image));
        nameValuePairs.add(new BasicNameValuePair("pk", String.valueOf(this.pk)));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse response = httpClient.execute(httpPost);
        } catch (IOException e) {
            Log.d("DEBUG", String.valueOf(e));
            Log.d("DEBUG", "COULD NOT SEND TO SERVER!");
        }
        return null;
    }

    private String convertImageToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}

package com.example.emergency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AddressThread extends AsyncTask<String,String,String> {

	double latNE;
	double lngNE; 
	double latSW; 
	double lngSW;
	
	String url;
	
	public AddressThread(String url) {
		/**this.latNE=latNE;
		this.lngNE=lngNE;
		this.latSW=latSW;
		this.lngSW=lngSW;*/
		this.url = url;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
        	
        	
            // Add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //nameValuePairs.add(new BasicNameValuePair("data", xml));
            
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            
            HttpEntity entity = response.getEntity();
            
            if(entity != null){
            	String x = EntityUtils.toString(entity);
            	//Log.i("test", x);
                return x;
            }
            else{
                return "No string.";
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        	return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	return null;
        }
	}

}

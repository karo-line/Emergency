package com.example.emergency.functions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutionException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

 
import android.annotation.SuppressLint;
import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    @SuppressLint("NewApi")
	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
 
        // Making HTTP request
        /**try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    	String s ="";
    	
    	try {
    		HttpThread http = new HttpThread(url, params);
    		return http.execute().get();
    		//Log.i("jsonparser", is.toString());
    		//is = http.getInputStream();
    		
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
    	//is = new ByteArrayInputStream(s.getBytes(Charset.forName("iso-8859-1")));
        /**try {
        	Log.i("try", "try");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            //reader.readLine();
            Log.i("sb", "sb");
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
                
            }
            Log.i("line", "line");
            is.close();
            json = sb.toString();
            
            Log.e("JSONeigen", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
            
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
       
        // return JSON String
        return jObj;*/
 
    }
}

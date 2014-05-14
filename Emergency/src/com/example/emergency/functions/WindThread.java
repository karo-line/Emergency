package com.example.emergency.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class WindThread extends AsyncTask<String,String,JSONObject> {

	String url;
	
	InputStream is ;
	static JSONObject jObj = null;
	static String json = "";
	
	public WindThread(String url) {
		
		this.url = url;
	}
	@Override
	protected JSONObject doInBackground(String... arg0) {
		 try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            //httpPost.setEntity(new UrlEncodedFormEntity(params));
	 
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	            Log.i("string s", "KEINEE");
	            //return is;
	 
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	            Log.i("string s", "uee");
	            return null;
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	            Log.i("string s", "cpe");
	            return null;
	        } catch (IOException e) {
	            e.printStackTrace();
	            Log.i("string s", "ioe");
	            return null;
	        }
		 
		  try {
	        	
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
	        return jObj;
		
	}
	
	public InputStream getInputStream() {
		
		return is;
	}
	
	

}

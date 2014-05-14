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
 
public class JSONWindParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONWindParser() {
 
    }
 
    @SuppressLint("NewApi")
	public JSONObject getJSONFromUrl(String url) {
 

    	String s ="";
    	
    	try {
    		WindThread http = new WindThread(url);
    		return http.execute().get();
    	
    		
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
    
 
    }
}

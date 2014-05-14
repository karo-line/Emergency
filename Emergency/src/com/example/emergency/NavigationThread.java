package com.example.emergency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.provider.DocumentsContract.Document;
import android.util.Log;

public class NavigationThread extends AsyncTask<String,String,org.w3c.dom.Document> {

	String url;
	List<NameValuePair> params;
	InputStream is ;
	static JSONObject jObj = null;
	static String json = "";
	
	public NavigationThread(String url) {
		
		this.url = url;
		
	}
	@Override
	protected org.w3c.dom.Document doInBackground(String... arg0) {
		org.w3c.dom.Document doc = null;
		 try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            //httpPost.setEntity(new UrlEncodedFormEntity(params));
	 
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	            Log.i("string s", "KEINEE");
	           
	            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            doc = builder.parse(is);
	 
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
	        } catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // return JSON String
	        return doc;
		
	}
	
	public InputStream getInputStream() {
		
		return is;
	}
	
	

}

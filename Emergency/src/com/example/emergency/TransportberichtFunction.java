package com.example.emergency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
 
public class TransportberichtFunction {
     
    private JSONParser jsonParser;
    private JSONParser jsonTodoParser;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://lmattano.dyndns-at-home.com:8443/android_berichte_api/";
    private static String storeURL = "http://lmattano.dyndns-at-home.com:8443/android_todo_api/";
     
    private static String load_tag = "load";
    private static String store_tag = "include";
    private static String done_tag = "done";
    private static String delete_tag = "delete";
     
    // constructor
    public TransportberichtFunction(){
    	jsonParser = new JSONParser();
        jsonTodoParser = new JSONParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("local")+"android_berichte_api/";
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject saveBericht(HashMap<String, String> bericht, boolean rettung, boolean vu, boolean naw, boolean msd, boolean krankentr, boolean aend, boolean nah, boolean leerf){
        // Building Parameters
    	String strRettung;
    	String strVu;
    	String strNaw;
    	String strMsd;
    	String strKrankentr;
    	String strAend;
    	String strNah;
    	String strLeerf;
    	
    	if(rettung) {
    		strRettung="1";
    	} else {
    		strRettung="0";
    	} if(vu) {
    		strVu="1";
    	} else {
    		strVu="0";
    	} if(naw) {
    		strNaw="1";
    	} else {
    		strNaw="0";
    	} if(msd) {
    		strMsd="1";
    	} else {
    		strMsd="0";
    	} if(krankentr) {
    		strKrankentr="1";
    	} else {
    		strKrankentr="0";
    	} if(aend) {
    		strAend="1";
    	} else {
    		strAend="0";
    	} if(nah) {
    		strNah="1";
    	} else {
    		strNah="0";
    	} if(leerf) {
    		strLeerf="1";
    	} else {
    		strLeerf="0";
    	}
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", store_tag));
        params.add(new BasicNameValuePair("rettung", strRettung));
        params.add(new BasicNameValuePair("vu", strVu));
        params.add(new BasicNameValuePair("naw", strNaw));
        params.add(new BasicNameValuePair("msd", strMsd));
        params.add(new BasicNameValuePair("krankentr", strKrankentr));
        params.add(new BasicNameValuePair("aend", strAend));
        params.add(new BasicNameValuePair("nah", strNah));
        params.add(new BasicNameValuePair("leerf", strLeerf));

        Set<String> keys = bericht.keySet();
        Iterator iterator = keys.iterator();
       
        while(iterator.hasNext()) {
        	String key = (String) iterator.next();
        	params.add(new BasicNameValuePair(key, bericht.get(key)));
        }
        
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject loadBericht(String nummer){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", load_tag));
        params.add(new BasicNameValuePair("nummer", nummer));
  

        
        
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
 
     
     
}

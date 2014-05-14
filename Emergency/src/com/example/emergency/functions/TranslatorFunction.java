package com.example.emergency.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.emergency.ServerConnection;
 
import android.content.Context;
 
public class TranslatorFunction {
     
    private JSONParser jsonParser;
    private JSONParser jsonTodoParser;
    private static String loginURL;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://lmattano.dyndns-at-home.com:8443/android_translator_api/";
    
     
    private static String load_tag = "getTranslator";
    private static String set_tag = "setFrei";
    private static String done_tag = "done";
    private static String delete_tag = "delete";
     
    // constructor
    public TranslatorFunction(){
    	jsonParser = new JSONParser();
        jsonTodoParser = new JSONParser();
        
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_translator_api/";
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject getTranslator(String sprache){

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", load_tag));
        params.add(new BasicNameValuePair("sprache", sprache));
      
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject setFrei(String frei, String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", set_tag));
        params.add(new BasicNameValuePair("frei", frei));
        params.add(new BasicNameValuePair("id", id));

        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
 
     
     
}

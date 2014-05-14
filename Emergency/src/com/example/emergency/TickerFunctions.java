package com.example.emergency;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
 
public class TickerFunctions {
     
    private JSONParser jsonParser;
    private JSONTodoParser jsonTodoParser;
    private static String loginURL;
    private static String registerURL;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://lmattano.dyndns-at-home.com:8443/android_user_api/";
    //private static String registerURL = "http://lmattano.dyndns-at-home.com:8443/android_login_api/";
     
    private static String login_tag = "saveFeed";
    private static String register_tag = "getFeed";
     
    // constructor
    public TickerFunctions(){
        jsonParser = new JSONParser();
        jsonTodoParser = new JSONTodoParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_ticker_api/";
        
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject storeFeed(String action){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("action", action));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject loadFeed(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
       
        // getting JSON Object
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
     

     
}

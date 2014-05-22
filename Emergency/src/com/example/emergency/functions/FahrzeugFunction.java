package com.example.emergency.functions;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.emergency.ServerConnection;
 
import android.content.Context;
 
public class FahrzeugFunction {
     
    private JSONTodoParser jsonParser;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://lmattano.dyndns-at-home.com:8443/android_kennzeichen_api/";
    //private static String registerURL = "http://10.0.0.7/android_kennzeichen_api/";
     
    private static String login_tag = "login";
    //private static String register_tag = "register";
     
    // constructor
    public FahrzeugFunction(){
        jsonParser = new JSONTodoParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_fahrzeug_api/";
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject getFahrzeuge(String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "getAll"));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject putFahrzeuge(String einsatzID, String name, String lat, String lon, String rotation, String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "putFahrzeug"));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("lat", lat));
        params.add(new BasicNameValuePair("lon", lon));
        params.add(new BasicNameValuePair("rotation", rotation));
        params.add(new BasicNameValuePair("id", id));
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
     
     
}

package com.example.emergency.functions;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.emergency.ServerConnection;
 
import android.content.Context;
import android.util.Log;
 
public class ElgaFunction {
     
    private JSONParser jsonParser;
    private JSONTodoParser jsonTodoParser;
    private static String loginURL;
    private static String registerURL;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    //private static String loginURL = "http://lmattano.dyndns-at-home.com:8443/android_user_api/";
    //private static String registerURL = "http://lmattano.dyndns-at-home.com:8443/android_login_api/";
     
    private static String login_tag = "getData";
    private static String detail_tag = "getDetail";
     
    // constructor
    public ElgaFunction(){
        jsonParser = new JSONParser();
        jsonTodoParser = new JSONTodoParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_elga_api/";
        
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject getElgaData(String befunde, String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("befunde", befunde));
        params.add(new BasicNameValuePair("id", id));
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);

        return json;
    }
     
    public JSONObject getElgaDetail(String befunde, String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", detail_tag));
        params.add(new BasicNameValuePair("befunde", befunde));
        params.add(new BasicNameValuePair("id", id));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

        return json;
    }
 
     

     
}

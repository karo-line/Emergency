package com.example.emergency.functions;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.emergency.ServerConnection;
 
import android.content.Context;
 
public class TodoFunction {
     
    private JSONParser jsonParser;
    private JSONTodoParser jsonTodoParser;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL;
    private static String storeURL = "http://lmattano.dyndns-at-home.com:8443/android_todo_api/";
     
    private static String login_tag = "login";
    private static String store_tag = "include";
    private static String done_tag = "done";
    private static String delete_tag = "delete";
     
    // constructor
    public TodoFunction(){
    	jsonParser = new JSONParser();
        jsonTodoParser = new JSONTodoParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_todo_api/";
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("einsatzID", "1"));
        
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject storeUser(String kommandant, String todo, String einsatzID, String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", store_tag));
        params.add(new BasicNameValuePair("kommandant", kommandant));
        params.add(new BasicNameValuePair("todo", todo));
        params.add(new BasicNameValuePair("done", "0"));
        params.add(new BasicNameValuePair("einsatzID", "1"));
        params.add(new BasicNameValuePair("id", id));
         
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
    
    public JSONObject doneCheckbox(String checkID, String done){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", done_tag));
        params.add(new BasicNameValuePair("id", checkID));
        params.add(new BasicNameValuePair("done", done));
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject deleteTodo(String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", delete_tag));
        params.add(new BasicNameValuePair("id", id));
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
     
     
}

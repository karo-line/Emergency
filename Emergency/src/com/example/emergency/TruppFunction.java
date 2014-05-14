package com.example.emergency;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
 
public class TruppFunction {
     
    private JSONParser jsonParser;
    private JSONTodoParser jsonTodoParser;
     
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL;
    private static String storeURL = "http://lmattano.dyndns-at-home.com:8443/android_todo_api/";
     
    private static String login_tag = "login";
    private static String store_tag = "include";
    private static String done_tag = "done";
    private static String back_tag = "back";
    private static String start_tag = "start";
     
    // constructor
    public TruppFunction(){
    	jsonParser = new JSONParser();
        jsonTodoParser = new JSONTodoParser();
        ServerConnection sC = new ServerConnection();
        loginURL = sC.getServerAdr("dyndns")+"android_truppen_api/";
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject getTruppen(String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        
        JSONObject json = jsonTodoParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject getTruppenDiff(String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "getTruppDiff"));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        
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
    
    public JSONObject setBack(String id){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", back_tag));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("back", "1"));
        java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		params.add(new BasicNameValuePair("timeBack", now.toString()));
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject startEinsatz(String dauer, ArrayList<TruppMann> men, String trupp, String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", start_tag));
        params.add(new BasicNameValuePair("trupp", trupp));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        params.add(new BasicNameValuePair("menSize", String.valueOf(men.size())));
        java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		params.add(new BasicNameValuePair("timeIn", now.toString()));
		
		int d = Integer.valueOf(dauer)*60*1000;
		now.setTime(now.getTime()+d);
		
		params.add(new BasicNameValuePair("timeOut", now.toString()));
		
        for(int i=0; i<men.size();i++) {
        	String man = "men"+String.valueOf(i);
        	params.add(new BasicNameValuePair(man, men.get(i).getEditText().getText().toString()));
        }
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject startEinsatzTextView(String dauer, ArrayList<TruppMann> men, String trupp, String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "startUpdate"));
        params.add(new BasicNameValuePair("trupp", trupp));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
        params.add(new BasicNameValuePair("menSize", String.valueOf(men.size())));
        java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		params.add(new BasicNameValuePair("timeIn", now.toString()));
		
		int d = Integer.valueOf(dauer)*60*1000;
		now.setTime(now.getTime()+d);
		
		params.add(new BasicNameValuePair("timeOut", now.toString()));
		
        for(int i=0; i<men.size();i++) {
        	String man = "men"+String.valueOf(i);
        	params.add(new BasicNameValuePair(man, men.get(i).getName()));
        }
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject updateTruppen(String einsatzID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "diff"));
        params.add(new BasicNameValuePair("einsatzID", einsatzID));
      
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
    
    public JSONObject deleteTrupp(String trupp){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "delete"));
        params.add(new BasicNameValuePair("trupp", trupp));
      
        
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
     
        return json;
    }
     
}

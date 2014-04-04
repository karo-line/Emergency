package com.example.emergency;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
 
public class WindFunction {
     
    private JSONWindParser jsonWindParser;
     
   
    private String windURL;
   
     
    // constructor
    public WindFunction(){
        jsonWindParser = new JSONWindParser();
    }
     
   
    public JSONObject getWind(String windURL, double lat, double lon){
        // Building Parameters
    	windURL = windURL + "lat="+Double.valueOf(lat)+"&lon="+Double.valueOf(lon);
        JSONObject json = jsonWindParser.getJSONFromUrl(windURL);
        Log.i("getWind","getWind");
        return json;
    }
     
     
}

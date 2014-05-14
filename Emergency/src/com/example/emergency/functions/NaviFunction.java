package com.example.emergency.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Document;

 
import android.content.Context;
import android.util.Log;
 
public class NaviFunction {
     
    private JSONWindParser jsonWindParser;
     
   
    private String windURL;
   
     
    // constructor
    public NaviFunction(){
       
    }
     
   
    public org.w3c.dom.Document getDocument(String url) throws InterruptedException, ExecutionException{
        // Building Parameters
    	NavigationThread nhttp = new NavigationThread(url);
        Document doc = nhttp.execute().get();
        return doc;
    }
     
     
}

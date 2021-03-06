package com.example.emergency;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.entities.EinsatzInfo;
import com.example.emergency.functions.OperationFunction;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RefreshInfo {

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_ID = "id";
	private static String KEY_EINSATZORT= "einsatzort";
	private static String KEY_PLZ = "plz";
	private static String KEY_STRASSE = "strasse";
	private static String KEY_HAUSNUMMER = "hausnummer";
	private static String KEY_STOCKWERK = "stockwerk";
	private static String KEY_ORTBEI = "ortBei";
	private static String KEY_PATIENTNAME = "patientName";
	private static String KEY_PATIENTSEX = "patientSex";
	private static String KEY_CALLER = "anruferName";
	private static String KEY_CALLERNR = "anruferNummer";
	private static String KEY_ALARMIERTRETTUNG = "alarmiertRettung";
	private static String KEY_ALARMIERTPOLIZEI = "alarmiertPolizei";
	private static String KEY_ALARMIERTFEUERWEHR = "alarmiertFeuerwehr";
	private static String KEY_ALARMIERUNGWEITERE = "alamierungWeitere";
	private static String KEY_EINSATZART = "einsatzart";
	private static String KEY_INFO = "info";
	private static String KEY_VIDEOLINK = "videoLink";
	public static String videoLink;
	public static EinsatzInfo einsatz = null;
	
	TextView einsatzinfosView;
	TextView refreshView;
	
	public void refresh(View v, String id) { 
		
		einsatzinfosView = (TextView) v.findViewById(R.id.einsatzinformation);
		refreshView = (TextView) v.findViewById(R.id.aktualisiert);
		einsatz = new EinsatzInfo();
	     
	     OperationFunction operationFunction = new OperationFunction();
	     JSONObject json;

	     try {
	    	 json = operationFunction.loginUser(id);
	     
	
	     // check for login response
	     try {
	         if (json.getString(KEY_SUCCESS) != null) {
	             
	             String res = json.getString(KEY_SUCCESS);
	             if(Integer.parseInt(res) == 1){
	                 // user successfully logged in
	                 // Store user details in SQLite Database
	                 
	                 JSONObject json_user = json.getJSONObject("user");
	                 
	                 String rettung = "";
	                 String polizei = "";
	                 String feuerwehr = "";
	                 
	                 if(json_user.getString(KEY_ALARMIERTRETTUNG).matches("1")) {
	              	   rettung = "Rettung";
	                 }
	                 if(json_user.getString(KEY_ALARMIERTPOLIZEI).matches("1")) {
	              	   polizei = "Polizei";
	                 }
	                 if(json_user.getString(KEY_ALARMIERTFEUERWEHR).matches("1")) {
	              	   feuerwehr = "Feuerwehr";
	                 }
	                 
	                videoLink = json_user.getString(KEY_VIDEOLINK);
	                  
	                 einsatzinfosView.setText("Einsatzort: "+ json_user.getString(KEY_EINSATZORT)
	              		   +" "+json_user.getString(KEY_PLZ)
	              		   +"\n"+"Straße: "+json_user.getString(KEY_STRASSE)+" "+json_user.getString(KEY_HAUSNUMMER)
	              		   +"\n"+"Stockwerk: "+json_user.getString(KEY_STOCKWERK)
	              		   +"\n"+"Bei: "+json_user.getString(KEY_ORTBEI)
	              		   +"\n"+"Patient: "+json_user.getString(KEY_PATIENTNAME)
	              		   +"\n"+"Geschlecht: "+json_user.getString(KEY_PATIENTSEX)
	              		   +"\n"+"Anrufer: "+json_user.getString(KEY_CALLER)
	              		   +"\n"+"Zurückrufnummer: "+json_user.getString(KEY_CALLERNR)
	              		   +"\n"+"Weitere Alarmierung: "+rettung+" "+polizei+" "+feuerwehr
	              		   +"\n"+"Einsatzart: "+json_user.getString(KEY_EINSATZART)
	              		   +"\n" +json_user.getString(KEY_INFO));
	                 
	                 Time now = new Time();
	                 now.setToNow();
	                 String timeAct = "Zuletzt aktualisiert: "+now.format("%k:%M:%S");
	                 refreshView.setText(timeAct);
	                 // Clear all previous data in database
	                 //userFunction.logoutUser(getApplicationContext());
	                 //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_SEX), json.getString(KEY_UID), json_user.getString(KEY_NATIONALITY), json_user.getString(KEY_BIRTHDAY), json_user.getString(KEY_REASON), json_user.getString(KEY_ALIAS));                       
	                 //Log.i("username", json_user.getString(KEY_NAME));
	                 // Launch Dashboard Screen
	                 /**Intent carList = new Intent(getApplicationContext(), CarListActivity.class);
	                 carList.putExtra("person", json_user.getString(KEY_PERSON));
	                 carList.putExtra("zulassungsdatum", json_user.getString(KEY_ZULASSUNGSDATUM));
	                 carList.putExtra("zulassungsort", json_user.getString(KEY_ZULASSUNGSORT));
	                 carList.putExtra("kennzeichen", json_user.getString(KEY_KENNZEICHEN));
	                 
	                 // Close all views before launching Dashboard
	                 carList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                 startActivity(carList);*/
	                  
	                 // Close Login Screen
	                 
	                 /**
	                  * aktuellen einsatzinfos in einsatzifo object speichern
	                  */
	                String ort = json_user.getString(KEY_EINSATZORT);
	              	String plz = json_user.getString(KEY_PLZ);
	              	String str = json_user.getString(KEY_STRASSE)+" "+json_user.getString(KEY_HAUSNUMMER);
	              	String stockwerk =json_user.getString(KEY_STOCKWERK);
	              	String bei = json_user.getString(KEY_ORTBEI);
	              	String patient = json_user.getString(KEY_PATIENTNAME);
	              	String geschlecht = json_user.getString(KEY_PATIENTSEX);
	              	String anrufer = json_user.getString(KEY_CALLER);
	              	String telNummer = json_user.getString(KEY_CALLERNR);
	              	String weitereAlarmierung = rettung+" "+polizei+" "+feuerwehr;
	              	String einsatzArt = json_user.getString(KEY_EINSATZART);
	              	String info = json_user.getString(KEY_INFO);
	                
	                 einsatz.actualize(ort,  plz,  str,  stockwerk,  bei,  patient,  geschlecht,  anrufer,  telNummer,  weitereAlarmierung,  einsatzArt,  info);
	                 einsatz.setAktualisiert(timeAct);
	             }else{
	            	 Time now = new Time();
	                 now.setToNow();
	                 String timeAct = "Zuletzt aktualisiert: "+now.format("%k:%M:%S");
	                 refreshView.setText(timeAct);
	            	 einsatzinfosView.setText("kein Einsatz");
	            	 einsatz.setTerminate(true);
	            	 einsatz.setAktualisiert(timeAct);
	                 // Error in login
	                 //loginErrorMsg.setText("Incorrect license number");
	             }
	         } else {
	        	 Time now = new Time();
                 now.setToNow();
                 String timeAct = "Zuletzt aktualisiert: "+now.format("%k:%M:%S");
                 refreshView.setText(timeAct);
            	 einsatzinfosView.setText("kein Einsatz");
            	 einsatz.setTerminate(true);
            	 einsatz.setAktualisiert(timeAct);
            	 
	         }
	     } catch (JSONException e) {
	         e.printStackTrace();
	     }
	     
	     } catch (Exception e) {
	    	 
		    	// Toast.makeText(c.getApplicationContext(), "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
		    	Log.i("noconnection","noconnection");
		     }
	}
}

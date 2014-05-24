package com.example.emergency.activities.police;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.R.menu;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.LoginFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Menu extends Activity {
	
	private Intent i;
	
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
	
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_nexus);
		einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
		refresh = (TextView) findViewById(R.id.aktualisiert);
		if(RefreshInfo.einsatz.isTerminate()) {
			einsatzinfos.setText("Kein Einsatz");
		} else {
		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		}
		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		s = new scheduleEinsatz();
		s.scheduleUpdateText(einsatzinfos, refresh);
	}
	
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}
	
	public void startMap(View v) {
		i= new Intent(this, FullscreenActivity.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
	}
	
	public void startTranslator(View v) {
		i= new Intent(this, Translator.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startPerson(View v) {
		i= new Intent(this, PersonActivity.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
	}
	
	public void startCar(View v) {
		i= new Intent(this, Car.class);
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoPolice.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");
		 String username = settings.getString("username", "nosuchvalue");
		 Log.i("einsatzrefresh",einsatzID);
		 
		 
		 
		 LoginFunctions func = new LoginFunctions();
		 JSONObject json = func.getEinsatz(username);
		 try {
				if (json.getString("success") != null) {
				     String res = json.getString("success");
				     if(Integer.parseInt(res) == 1){
				    	 JSONObject jObj = json.getJSONObject("user");
				    	 einsatzID = jObj.getString("einsatzID");
				    	
				    	 
				    	 SharedPreferences.Editor editor = settings.edit();
		            	   editor.remove("einsatzID");
		            	   editor.putString("einsatzID", einsatzID);
		            	   editor.commit();
				     }
				 }
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfos),einsatzID);
		 }
		 /**  String id = "1";
           
           OperationFunction operationFunction = new OperationFunction();
           JSONObject json = operationFunction.loginUser(id);

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
                       
                      
                        
                       einsatzinfos.setText("Einsatzort: "+ json_user.getString(KEY_EINSATZORT)
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
                       refresh.setText("Zuletzt aktualisiert: "+now.format("%k:%M:%S"));
                       // Clear all previous data in database
                       //userFunction.logoutUser(getApplicationContext());
                       //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_SEX), json.getString(KEY_UID), json_user.getString(KEY_NATIONALITY), json_user.getString(KEY_BIRTHDAY), json_user.getString(KEY_REASON), json_user.getString(KEY_ALIAS));                       
                       //Log.i("username", json_user.getString(KEY_NAME));
                       // Launch Dashboard Screen
                    
                        
                       // Close Login Screen
                       
                   }else{
                       // Error in login
                       //loginErrorMsg.setText("Incorrect license number");
                   }
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }*/
	}
		
		public void back(View v) {
			 finish();
					
		}
	
		@SuppressLint("NewApi")
		public void startDropdown(View v) {
			PopupMenu popup = new PopupMenu(this, v);
		    MenuInflater inflater = popup.getMenuInflater();
		    inflater.inflate(R.menu.popupmenu, popup.getMenu());
		    final View menu = this.findViewById(R.id.einsatzinfos);
		    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
		    	   
		    	   @SuppressLint("CommitPrefEdits")
				@Override
		    	   public boolean onMenuItemClick(MenuItem item) {
		    		   switch(item.getItemId()){  
		               case R.id.menu1: 
		            	   SharedPreferences settings = getSharedPreferences("shares",0);
		          		 	String username = settings.getString("username", "nosuchvalue");
		          		 	Log.i("usernameDrop",username);
		            	   LoginFunctions func = new LoginFunctions();
		            	   JSONObject json = func.terminate(username);
		            	   
		            	   
		            	   SharedPreferences.Editor editor = settings.edit();
		            	   editor.remove("einsatzID");
		            	   editor.putString("einsatzID", "0");
		            	   editor.commit();
		            	   
		            	   RefreshInfo refreshInfo = new RefreshInfo();
		   				refreshInfo.refresh(menu,"0");
		            	   
		            	   return true;
		               case R.id.menu2:
		            	   i= new Intent(getApplicationContext(), StartChoice.class);
		            	   s.stopHandlerText();
		            	   startActivity(i);	
		            	   overridePendingTransition(R.layout.fadeout, R.layout.fadein);
		            	   SharedPreferences settings2 = getSharedPreferences("shares",0);
		            	   SharedPreferences.Editor editor2 = settings2.edit();
		            	   editor2.clear();
		            	   editor2.commit();
		            	   finish();
		            	   return true;
		    		   }
					return false;
		    	   }

		    	  });
		    popup.show();

		}
}

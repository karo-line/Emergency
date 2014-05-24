package com.example.emergency.activities.fire;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.BaseActivity;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MenuFire extends BaseActivity {
	
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
	private final int FIVE_SECONDS = 120000;
	
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_fire_nexus);
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
		
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		Context c = getApplicationContext();
		String windSchedule = s.scheduleWind(layoutInflater, findViewById(R.id.textview), c);
	}
	
	/**protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}*/
	
	public void startMap(View v) {
		i= new Intent(this, StartFire.class);
		s.stopHandlerText();
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startGefahrengut(View v) {
		i= new Intent(this, GefahrengutFire.class);
		s.stopHandlerText();
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
	}
	
	public void startWind(View v) {
		i= new Intent(this, WindFire.class);
		SharedPreferences settings = getSharedPreferences("shares",0);
		String einsatzID2 = settings.getString("einsatzID", "nosuchvalue");
		s.stopHandlerText();
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
	
				        	startActivity(i);		
				
	}
	
	public void startKoordination(View v) {
		
		SharedPreferences settings = getSharedPreferences("shares",0);
		String einsatzID2 = settings.getString("einsatzID", "nosuchvalue");
		String taskforce = settings.getString("taskforce", "taskforce");
		s.stopHandlerText();
		if(taskforce.equals("oberkommandant")) {
			i= new Intent(this, KoordinationFire.class);
			startActivity(i);		
			overridePendingTransition(R.layout.fadeout, R.layout.fadein);
		} else if(taskforce.equals("feuerwehr")) {
			i= new Intent(this, TruppKoordination.class);
			startActivity(i);		
			overridePendingTransition(R.layout.fadeout, R.layout.fadein);
		}
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		s.stopHandlerText();
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
	}
	
	public void startTodo(View v) {
		i= new Intent(this, ToDoFire.class);
		s.stopHandlerText();
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startTicker(View v) {
		i= new Intent(this, TickerFire.class);
		s.stopHandlerText();
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");
		 String username = settings.getString("username", "nosuchvalue");
		 
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
	
			
	}
		
		public void back(View v) {
			s.stopHandlerText();
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

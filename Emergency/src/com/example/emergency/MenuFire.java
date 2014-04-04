package com.example.emergency;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuFire extends Activity {
	
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
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_fire_nexus);
		einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
		refresh = (TextView) findViewById(R.id.aktualisiert);
	}
	
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}
	
	public void startMap(View v) {
		i= new Intent(this, StartFire.class);
		finish();
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startGefahrengut(View v) {
		i= new Intent(this, GefahrengutFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startWind(View v) {
		i= new Intent(this, WindFire.class);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
	
				        	startActivity(i);		
				
	}
	
	public void startKoordination(View v) {
		i= new Intent(this, KoordinationFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
	}
	
	public void startTodo(View v) {
		i= new Intent(this, ToDoFire.class);
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosmenu));
	}
		
		public void back(View v) {
			 finish();
			 
		}
}

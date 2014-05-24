package com.example.emergency.activities.ems;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unused.VideoEms;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.ElgaFunction;
import com.example.emergency.functions.LoginFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ElgaDetailEms extends Activity {

	private Intent i;
	Button btnWeitere;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	boolean briefArzt;
	boolean briefPflege;
	boolean labor;
	boolean diagnostik;
	boolean eMed;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.elga_list_nexus);
			
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
		
			TextView title = (TextView) findViewById(R.id.elgaTitle);
			LinearLayout ll = (LinearLayout) findViewById(R.id.llElga);
			
			String briefId = getIntent().getExtras().getString("briefId");
			String befund = getIntent().getExtras().getString("befund");
			
			title.setText("Details");
			
			ElgaFunction elgaFunction = new ElgaFunction();
			JSONObject json = elgaFunction.getElgaDetail(befund, briefId);
			
			try {
				JSONObject json_user=json.getJSONObject("user");
				
				if(befund.equals("briefArzt")) {
					String datum = json_user.getString("datum");
					String sprachcode = json_user.getString("sprachcode");
					String brieftext = json_user.getString("brieftext");
					String aufnahmegrund = json_user.getString("aufnahmegrund");
					String diagnoseentlassung = json_user.getString("diagnoseentlassung");
					String massnahmen = json_user.getString("massnahmen");
					String letzteMed = json_user.getString("letzteMed");
					String empfMed = json_user.getString("empfMed");
					String weitereMassnahmen = json_user.getString("weitereMassnahmen");
					String zusammenfassung = json_user.getString("zusammenfassung");
					String abschluss = json_user.getString("abschluss");
				
					
					TextView datumView = new TextView(getApplicationContext());
					datumView.setText("Erstellungsdatum");
					datumView.setTextColor(Color.BLACK);
					datumView.setTextSize(20);
					datumView.setPadding(30, 20, 5, 5);
					ll.addView(datumView);
					
					TextView datumText = new TextView(getApplicationContext());
					datumText.setText(datum);
					datumText.setTextColor(Color.BLACK);
					datumText.setTextSize(15);
					datumText.setPadding(30, 5, 5, 5);
					ll.addView(datumText);
					
					TextView sprachView = new TextView(getApplicationContext());
					sprachView.setText("Sprache");
					sprachView.setTextColor(Color.BLACK);
					sprachView.setTextSize(20);
					sprachView.setPadding(30, 20, 5, 5);
					ll.addView(sprachView);
					
					TextView sprachText = new TextView(getApplicationContext());
					sprachText.setText(sprachcode);
					sprachText.setTextColor(Color.BLACK);
					sprachText.setTextSize(15);
					sprachText.setPadding(30, 5, 5, 5);
					ll.addView(sprachText);
					
					TextView brieftextView = new TextView(getApplicationContext());
					brieftextView.setText("Brieftext");
					brieftextView.setTextColor(Color.BLACK);
					brieftextView.setTextSize(20);
					brieftextView.setPadding(30, 20, 5, 5);
					ll.addView(brieftextView);
					
					TextView briefText = new TextView(getApplicationContext());
					briefText.setText(brieftext);
					briefText.setTextColor(Color.BLACK);
					briefText.setTextSize(15);
					briefText.setPadding(30, 5, 5, 5);
					ll.addView(briefText);
					
					TextView aufnahmeView = new TextView(getApplicationContext());
					aufnahmeView.setText("Aufnahmegrund");
					aufnahmeView.setTextColor(Color.BLACK);
					aufnahmeView.setTextSize(20);
					aufnahmeView.setPadding(30, 20, 5, 5);
					ll.addView(aufnahmeView);
					
					TextView aufnahmeText = new TextView(getApplicationContext());
					aufnahmeText.setText(aufnahmegrund);
					aufnahmeText.setTextColor(Color.BLACK);
					aufnahmeText.setTextSize(15);
					aufnahmeText.setPadding(30, 5, 5, 5);
					ll.addView(aufnahmeText);
					
					TextView diagnoseView = new TextView(getApplicationContext());
					diagnoseView.setText("Diagnose bei Entlassung");
					diagnoseView.setTextColor(Color.BLACK);
					diagnoseView.setTextSize(20);
					diagnoseView.setPadding(30, 20, 5, 5);
					ll.addView(diagnoseView);
					
					TextView diagnoseText = new TextView(getApplicationContext());
					diagnoseText.setText(diagnoseentlassung);
					diagnoseText.setTextColor(Color.BLACK);
					diagnoseText.setTextSize(15);
					diagnoseText.setPadding(30, 5, 5, 5);
					ll.addView(diagnoseText);
					
					
					TextView massnahmenView = new TextView(getApplicationContext());
					massnahmenView.setText("Durchgef¸hrte Maﬂnahmen");
					massnahmenView.setTextColor(Color.BLACK);
					massnahmenView.setTextSize(20);
					massnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(massnahmenView);
					
					TextView massnahmenText = new TextView(getApplicationContext());
					massnahmenText.setText(massnahmen);
					massnahmenText.setTextColor(Color.BLACK);
					massnahmenText.setTextSize(15);
					massnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(massnahmenText);
					
					TextView letzteMedView = new TextView(getApplicationContext());
					letzteMedView.setText("Letzte Medikation");
					letzteMedView.setTextColor(Color.BLACK);
					letzteMedView.setTextSize(20);
					letzteMedView.setPadding(30, 20, 5, 5);
					ll.addView(letzteMedView);
					
					TextView letzteMedText = new TextView(getApplicationContext());
					letzteMedText.setText(letzteMed);
					letzteMedText.setTextColor(Color.BLACK);
					letzteMedText.setTextSize(15);
					letzteMedText.setPadding(30, 5, 5, 5);
					ll.addView(letzteMedText);
					
					TextView empfMedView = new TextView(getApplicationContext());
					empfMedView.setText("Empfohlene Medikation");
					empfMedView.setTextColor(Color.BLACK);
					empfMedView.setTextSize(20);
					empfMedView.setPadding(30, 20, 5, 5);
					ll.addView(empfMedView);
					
					TextView empfMedText = new TextView(getApplicationContext());
					empfMedText.setText(empfMed);
					empfMedText.setTextColor(Color.BLACK);
					empfMedText.setTextSize(15);
					empfMedText.setPadding(30, 5, 5, 5);
					ll.addView(empfMedText);
					
					TextView weitereMassnahmenView = new TextView(getApplicationContext());
					weitereMassnahmenView.setText("Weitere empfohlene Maﬂnahmen");
					weitereMassnahmenView.setTextColor(Color.BLACK);
					weitereMassnahmenView.setTextSize(20);
					weitereMassnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(weitereMassnahmenView);
					
					TextView weitereMassnahmenText = new TextView(getApplicationContext());
					weitereMassnahmenText.setText(weitereMassnahmen);
					weitereMassnahmenText.setTextColor(Color.BLACK);
					weitereMassnahmenText.setTextSize(15);
					weitereMassnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(weitereMassnahmenText);
					
					TextView zusammenfassungView = new TextView(getApplicationContext());
					zusammenfassungView.setText("Zusammenfassung des Aufenthalts");
					zusammenfassungView.setTextColor(Color.BLACK);
					zusammenfassungView.setTextSize(20);
					zusammenfassungView.setPadding(30, 20, 5, 5);
					ll.addView(zusammenfassungView);
					
					TextView zusammenfassungText = new TextView(getApplicationContext());
					zusammenfassungText.setText(zusammenfassung);
					zusammenfassungText.setTextColor(Color.BLACK);
					zusammenfassungText.setTextSize(15);
					zusammenfassungText.setPadding(30, 5, 5, 5);
					ll.addView(zusammenfassungText);
					
					TextView abschlussView = new TextView(getApplicationContext());
					abschlussView.setText("Abschlieﬂende Bemerkungen");
					abschlussView.setTextColor(Color.BLACK);
					abschlussView.setTextSize(20);
					abschlussView.setPadding(30, 20, 5, 5);
					ll.addView(abschlussView);
					
					TextView abschlussText = new TextView(getApplicationContext());
					abschlussText.setText(abschluss);
					abschlussText.setTextColor(Color.BLACK);
					abschlussText.setTextSize(15);
					abschlussText.setPadding(30, 5, 5, 5);
					ll.addView(abschlussText);
					
					
					
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuEms.class);
		startActivity(i);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoEms.class);
		startActivity(i);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startReport(View v) {
		i= new Intent(this, Berichte.class);
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

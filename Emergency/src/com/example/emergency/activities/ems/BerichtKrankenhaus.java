package com.example.emergency.activities.ems;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.BaseActivity;
import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.activities.police.Menu;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TransportberichtFunction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class BerichtKrankenhaus extends BaseActivity {

	private Intent i;
	Button btnSpeichern;
	EditText name;
	EditText gebdat;
	EditText tel;
	EditText staatsbuergerschaft;
	EditText krankenkassa;
	EditText versicherungsnr;
	EditText nameV;
	EditText gebdatV;
	EditText plzV;
	EditText strasseV;
	EditText versNr;
	EditText beruf;
	EditText beschaeftigt;
	EditText anschrift;
	EditText beschwerden;
	EditText beschwerdenSeit;
	EditText strasse;
	EditText unternommen;
	EditText plz;
	EditText ort;
	EditText arbeitgeber;
	EditText abholungsort;
	EditText bestimmungsort;
	EditText veranlassung;
	EditText diagnose;
	CheckBox eigeninitiative;
	CheckBox kinderarzt;
	CheckBox praktischerarzt;
	CheckBox facharzt;
	CheckBox privat;
	CheckBox rettung;
	CheckBox notarzt;
	CheckBox taxi;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.bericht_krankenhaus);
			
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
			
			name = (EditText) findViewById(R.id.name);
			gebdat = (EditText) findViewById(R.id.gebdat);
			 plz = (EditText) findViewById(R.id.plz);
			 strasse = (EditText) findViewById(R.id.strasse);
			 tel = (EditText) findViewById(R.id.tel);
			 staatsbuergerschaft = (EditText) findViewById(R.id.staatsbuergerschaft);
			 krankenkassa = (EditText) findViewById(R.id.krankenkassa);
			 versicherungsnr = (EditText) findViewById(R.id.versicherungsnr);
			 nameV = (EditText) findViewById(R.id.nameV);
			 gebdatV = (EditText) findViewById(R.id.gebdatV);
			 plzV = (EditText) findViewById(R.id.plzV);
			 strasseV = (EditText) findViewById(R.id.strasseV);
			 versNr = (EditText) findViewById(R.id.versNr);
			 beruf = (EditText) findViewById(R.id.beruf);
			 beschaeftigt = (EditText) findViewById(R.id.beschaeftigt);
			 anschrift = (EditText) findViewById(R.id.anschrift);
			 beschwerden = (EditText) findViewById(R.id.beschwerden);
			 beschwerdenSeit = (EditText) findViewById(R.id.beschwerdenSeit);
			 unternommen = (EditText) findViewById(R.id.unternommen);
			
			 eigeninitiative = (CheckBox) findViewById(R.id.eigeninitiative);
			 kinderarzt = (CheckBox) findViewById(R.id.kinderarzt);
			 praktischerarzt = (CheckBox) findViewById(R.id.praktischerarzt);
			 facharzt = (CheckBox) findViewById(R.id.facharzt);
			 privat = (CheckBox) findViewById(R.id.privat);
			 rettung = (CheckBox) findViewById(R.id.rettung);
			 notarzt = (CheckBox) findViewById(R.id.notarzt);
			 taxi = (CheckBox) findViewById(R.id.taxi);
			
			 //sollte man eigentlich mit eindeutiger zuordnung zum einsatz machen da es pro einsatz nur einen gibt
			 SharedPreferences settings = getSharedPreferences("shares",0);
			 String nrBericht = settings.getString("transportBericht", "nosuchvalue");
			 Log.i("nrBericht", nrBericht);

			 /*if(!nrBericht.equals("nosuchvalue")) {
				 TransportberichtFunction berichtFunction = new TransportberichtFunction();
				 JSONObject json = berichtFunction.loadBericht(nrBericht);
				 try {
					JSONObject jObj = json.getJSONObject("user");
	
					nummer.setText(jObj.getString("nummer"));
					 datum.setText(jObj.getString("datum"));
					 abfahrt.setText(jObj.getString("abfahrt"));
					 rueckkehr.setText(jObj.getString("rueckkehr"));
					 dauer.setText(jObj.getString("dauer"));
					 wagen.setText(jObj.getString("wagen"));
					 kilometerstand.setText(jObj.getString("kilometerstand"));
					 rueckkehrkm.setText(jObj.getString("rueckkehrkm"));
					 gefahrenekm.setText(jObj.getString("gefahrenekm"));
					 familienname.setText(jObj.getString("familienname"));
					 vorname.setText(jObj.getString("vorname"));
					 familiennameAngehoerige.setText(jObj.getString("fnAngehoerige"));
					 vornameAngehoerige.setText(jObj.getString("vnAngehoerige"));
					 verwandtschaft.setText(jObj.getString("verwandtschaft"));
					 versnrVersicherter.setText(jObj.getString("versnrVersicherter"));
					 versnrTransportierter.setText(jObj.getString("versnrTransp"));
					 strasse.setText(jObj.getString("strasse"));
					 krankenkasse.setText(jObj.getString("krankenkasse"));
					 plz.setText(jObj.getString("plz"));
					 ort.setText(jObj.getString("ort"));
					 arbeitgeber.setText(jObj.getString("arbeitgeber"));
					 abholungsort.setText(jObj.getString("abholungsort"));
					 bestimmungsort.setText(jObj.getString("bestimmungsort"));
					 veranlassung.setText(jObj.getString("veranlassung"));
					 diagnose.setText(jObj.getString("diagnose"));
					 
					 if(jObj.getString("rettung").equals("0")) {
						 rettung.setChecked(false);
					 } else {
						 rettung.setChecked(true); 
					 }
					 
					 if(jObj.getString("vu").equals("0")) {
						 vu.setChecked(false);
					 } else {
						 vu.setChecked(true); 
					 }
					 
					 if(jObj.getString("naw").equals("0")) {
						 naw.setChecked(false);
					 } else {
						 naw.setChecked(true); 
					 }
					 
					 if(jObj.getString("msd").equals("0")) {
						 msd.setChecked(false);
					 } else {
						 msd.setChecked(true); 
					 }
					 
					 if(jObj.getString("krankentr").equals("0")) {
						 krankentr.setChecked(false);
					 } else {
						 krankentr.setChecked(true); 
					 }
					 
					 if(jObj.getString("aend").equals("0")) {
						 aend.setChecked(false);
					 } else {
						 aend.setChecked(true); 
					 }
					 
					 if(jObj.getString("nah").equals("0")) {
						 nah.setChecked(false);
					 } else {
						 nah.setChecked(true); 
					 }
					 
					 if(jObj.getString("leerf").equals("0")) {
						 leerf.setChecked(false);
					 } else {
						 leerf.setChecked(true); 
					 }
					 
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	*/
			 
			 
			 btnSpeichern = (Button) findViewById(R.id.btnSpeichern);
				btnSpeichern.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			 
			    
							 String nameTxt = name.getText().toString();
							 String gebdatTxt = gebdat.getText().toString();
							 String plzTxt = plz.getText().toString();
							 String strasseTxt = strasse.getText().toString();
							 String telTxt = tel.getText().toString();
							 String staatsbuergerschaftTxt = staatsbuergerschaft.getText().toString();
							 String krankenkassaTxt = krankenkassa.getText().toString();
							 String versicherungsnrTxt = versicherungsnr.getText().toString();
							 String nameVTxt = nameV.getText().toString();
							 String gebdatVTxt = gebdatV.getText().toString();
							 String plzVTxt = plzV.getText().toString();
							 String versNrTxt = versNr.getText().toString();
							 String berufTxt = beruf.getText().toString();
							 String beschaeftigtTxt = beschaeftigt.getText().toString();
							 String anschriftTxt = anschrift.getText().toString();
							 String beschwerdenTxt = beschwerden.getText().toString();
							 String beschwerdenSeitTxt = beschwerdenSeit.getText().toString();
							 String unternommenTxt = unternommen.getText().toString();
							 
							 boolean eigeninitiativeCheck = eigeninitiative.isChecked();
							 boolean kinderarztCheck = kinderarzt.isChecked();
							 boolean praktischerarztCheck = praktischerarzt.isChecked();
							 boolean facharztCheck = facharzt.isChecked();
							 boolean privatCheck = privat.isChecked();
							 boolean rettungCheck = rettung.isChecked();
							 boolean notarztCheck = notarzt.isChecked();
							 boolean taxiCheck = taxi.isChecked();
					
							 HashMap<String, String> bericht = new HashMap<String, String>();
							 bericht.put("name", nameTxt);
							 bericht.put("gebdat", gebdatTxt);
							 bericht.put("plz", plzTxt);
							 bericht.put("strasse", strasseTxt);
							 bericht.put("tel", telTxt);
							 bericht.put("staatsbuergerschaft", staatsbuergerschaftTxt);
							 bericht.put("krankenkassa", krankenkassaTxt);
							 bericht.put("versicherungsnr", versicherungsnrTxt);
							 bericht.put("nameV", nameVTxt);
							 bericht.put("gebdatV", gebdatVTxt);
							 bericht.put("plzV", plzVTxt);
							 bericht.put("versNr", versNrTxt);
							 bericht.put("beruf", berufTxt);
							 bericht.put("beschaeftigt", beschaeftigtTxt);
							 bericht.put("anschrift", anschriftTxt);
							 bericht.put("beschwerden", beschwerdenTxt);
							 bericht.put("beschwerdenSeit", beschwerdenSeitTxt);
							 bericht.put("unternommen", unternommenTxt);
							
							 
							 
							 TransportberichtFunction berichtFunction = new TransportberichtFunction();
							 JSONObject json = berichtFunction.saveBericht(bericht, eigeninitiativeCheck, kinderarztCheck, praktischerarztCheck, facharztCheck, privatCheck, rettungCheck, notarztCheck, taxiCheck);
							
							 //hat keine nummer
							/** SharedPreferences settings = getSharedPreferences("shares",0);
						     SharedPreferences.Editor editor = settings.edit();
						     editor.putString("transportBericht", nummerTxt);
						     editor.commit();*/

				
			        }
				});
					
				 
				
			
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startVideo(View v) {
		i= new Intent(this, Menu.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startReport(View v) {
		i= new Intent(this, BerichtKrankenhaus.class);
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

package com.example.emergency.activities.ems;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

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

public class BerichtTransport extends Activity {

	private Intent i;
	Button btnSpeichern;
	EditText nummer;
	EditText datum;
	EditText abfahrt;
	EditText rueckkehr;
	EditText dauer;
	EditText wagen;
	EditText kilometerstand;
	EditText rueckkehrkm;
	EditText gefahrenekm;
	EditText familienname;
	EditText vorname;
	EditText familiennameAngehoerige;
	EditText vornameAngehoerige;
	EditText verwandtschaft;
	EditText versnrVersicherter;
	EditText versnrTransportierter;
	EditText strasse;
	EditText krankenkasse;
	EditText plz;
	EditText ort;
	EditText arbeitgeber;
	EditText abholungsort;
	EditText bestimmungsort;
	EditText veranlassung;
	EditText diagnose;
	CheckBox rettung;
	CheckBox vu;
	CheckBox naw;
	CheckBox msd;
	CheckBox krankentr;
	CheckBox aend;
	CheckBox nah;
	CheckBox leerf;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.bericht_transport);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			nummer = (EditText) findViewById(R.id.nummer);
			datum = (EditText) findViewById(R.id.datum);
			 abfahrt = (EditText) findViewById(R.id.abfahrt);
			 rueckkehr = (EditText) findViewById(R.id.rueckkehr);
			 dauer = (EditText) findViewById(R.id.dauer);
			 wagen = (EditText) findViewById(R.id.wagen);
			 kilometerstand = (EditText) findViewById(R.id.kmAbfahrt);
			 rueckkehrkm = (EditText) findViewById(R.id.kmRueckkehr);
			 gefahrenekm = (EditText) findViewById(R.id.gefahreneKM);
			 familienname = (EditText) findViewById(R.id.familienname);
			 vorname = (EditText) findViewById(R.id.vorname);
			 familiennameAngehoerige = (EditText) findViewById(R.id.angehoerige);
			 vornameAngehoerige = (EditText) findViewById(R.id.angehoerigevorname);
			 verwandtschaft = (EditText) findViewById(R.id.verwandtschaft);
			 versnrVersicherter = (EditText) findViewById(R.id.versicherungsnummer1);
			 versnrTransportierter = (EditText) findViewById(R.id.versicherungsnummer2);
			 strasse = (EditText) findViewById(R.id.strasse);
			 krankenkasse = (EditText) findViewById(R.id.krankenkasse);
			 plz = (EditText) findViewById(R.id.postleitzahl);
			 ort = (EditText) findViewById(R.id.ort);
			 arbeitgeber = (EditText) findViewById(R.id.arbeitgeber);
			 abholungsort = (EditText) findViewById(R.id.abholungsort);
			 bestimmungsort = (EditText) findViewById(R.id.bestimmungsort);
			 veranlassung = (EditText) findViewById(R.id.veranlassung);
			 diagnose = (EditText) findViewById(R.id.diagnose);
			 rettung = (CheckBox) findViewById(R.id.rettung);
			 vu = (CheckBox) findViewById(R.id.vu);
			 naw = (CheckBox) findViewById(R.id.naw);
			 msd = (CheckBox) findViewById(R.id.msd);
			 krankentr = (CheckBox) findViewById(R.id.krankentr);
			 aend = (CheckBox) findViewById(R.id.aend);
			 nah = (CheckBox) findViewById(R.id.nah);
			 leerf = (CheckBox) findViewById(R.id.leerf);
			 
			 SharedPreferences settings = getSharedPreferences("shares",0);
			 String nrBericht = settings.getString("transportBericht", "nosuchvalue");
			 Log.i("nrBericht", nrBericht);

			 if(!nrBericht.equals("nosuchvalue")) {
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
			}	
			 
			 
			 btnSpeichern = (Button) findViewById(R.id.btnSpeichern);
				btnSpeichern.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            		
			    
							 String nummerTxt = nummer.getText().toString();
							 String datumTxt = datum.getText().toString();
							 String abfahrtTxt = abfahrt.getText().toString();
							 String rueckkehrTxt = rueckkehr.getText().toString();
							 String dauerTxt = dauer.getText().toString();
							 String wagenTxt = wagen.getText().toString();
							 String kilometerstandTxt = kilometerstand.getText().toString();
							 String rueckkehrkmTxt = rueckkehrkm.getText().toString();
							 String gefahrenekmTxt = gefahrenekm.getText().toString();
							 String familiennameTxt = familienname.getText().toString();
							 String vornameTxt = vorname.getText().toString();
							 String fnAngehoerigeTxt = familiennameAngehoerige.getText().toString();
							 String vnAngehoerigeTxt = vornameAngehoerige.getText().toString();
							 String verwandtschaftTxt = verwandtschaft.getText().toString();
							 String versnrVersicherterTxt = versnrVersicherter.getText().toString();
							 String versnrTranspTxt = versnrTransportierter.getText().toString();
							 String strasseTxt = strasse.getText().toString();
							 String krankenkasseTxt = krankenkasse.getText().toString();
							 String plzTxt = plz.getText().toString();
							 String ortTxt = ort.getText().toString();
							 String arbeitgeberTxt = arbeitgeber.getText().toString();
							 String abholungsortTxt = abholungsort.getText().toString();
							 String bestimmungsortTxt = bestimmungsort.getText().toString();
							 String veranlassungTxt = veranlassung.getText().toString();
							 String diagnoseTxt = diagnose.getText().toString();
							 boolean rettungTxt = rettung.isChecked();
							 boolean vuTxt = vu.isChecked();
							 boolean nawTxt = naw.isChecked();
							 boolean msdTxt = msd.isChecked();
							 boolean krankentrTxt = krankentr.isChecked();
							 boolean aendTxt = aend.isChecked();
							 boolean nahTxt = nah.isChecked();
							 boolean leerfTxt = leerf.isChecked();
							 
						HashMap<String, String> bericht = new HashMap<String, String>();
							 bericht.put("nummer", nummerTxt);
							 bericht.put("datum", datumTxt);
							 bericht.put("abfahrt", abfahrtTxt);
							 bericht.put("rueckkehr", rueckkehrTxt);
							 bericht.put("dauer", dauerTxt);
							 bericht.put("gefahrenekm", gefahrenekmTxt);
							 bericht.put("wagen", wagenTxt);
							 bericht.put("kilometerstand", kilometerstandTxt);
							 bericht.put("rueckkehrkm", rueckkehrkmTxt);
							 bericht.put("gefahrenekm", gefahrenekmTxt);
							 bericht.put("familienname", familiennameTxt);
							 bericht.put("vorname", vornameTxt);
							 bericht.put("fnAngehoerige", fnAngehoerigeTxt);
							 bericht.put("vnAngehoerige", vnAngehoerigeTxt);
							 bericht.put("verwandtschaft", verwandtschaftTxt);
							 bericht.put("versnrVersicherter", versnrVersicherterTxt);
							 bericht.put("versnrTransp", versnrTranspTxt);
							 bericht.put("strasse", strasseTxt);
							 bericht.put("krankenkasse", krankenkasseTxt);
							 bericht.put("plz", plzTxt);
							 bericht.put("ort", ortTxt);
							 bericht.put("arbeitgeber", arbeitgeberTxt);
							 bericht.put("abholungsort", abholungsortTxt);
							 bericht.put("bestimmungsort", bestimmungsortTxt);
							 bericht.put("veranlassung", veranlassungTxt);
							 bericht.put("diagnose", diagnoseTxt);
							 
							 
							 TransportberichtFunction berichtFunction = new TransportberichtFunction();
							 JSONObject json = berichtFunction.saveBericht(bericht, rettungTxt, vuTxt, nawTxt, msdTxt, krankentrTxt, aendTxt, nahTxt, leerfTxt);
							
							 SharedPreferences settings = getSharedPreferences("shares",0);
						     SharedPreferences.Editor editor = settings.edit();
						     editor.putString("transportBericht", nummerTxt);
						     editor.commit();

				
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
		i= new Intent(this, BerichtTransport.class);
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
	            	   return true;
	    		   }
				return false;
	    	   }

	    	  });
	    popup.show();

	}
}

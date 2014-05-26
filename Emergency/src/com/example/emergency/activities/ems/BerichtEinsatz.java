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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

public class BerichtEinsatz extends BaseActivity {

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
			setContentView(R.layout.bericht_einsatz);
			
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
			
			Spinner spinner = (Spinner) findViewById(R.id.naca_spinner);
			// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			        R.array.naca, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner.setAdapter(adapter);
			
			/**name = (EditText) findViewById(R.id.name);
			gebdat = (EditText) findViewById(R.id.gebdat);
			radiobtn
			radiobtn
			 editWeiteres = (EditText) findViewById(R.id.editWeiteres);
			 radiobtn
			 editWeiteresTransport = (EditText) findViewById(R.id.editWeiteresTransport);
			 zustand = (Checkbox) findViewById(R.id.zustand);
			 exekutive = (Checkbox) findViewById(R.id.exekutive);
			 exekutiveTxt = (EditText) findViewById(R.id.exekutiveTxt);
			 arzt = (Checkbox) findViewById(R.id.arzt);
			 arztTxt = (EditText) findViewById(R.id.arztTxt);
			 feuerwehr = (Checkbox) findViewById(R.id.feuerwehr);
			 feuerwehrTxt = (EditText) findViewById(R.id.feuerwehrTxt);
			 sonst = (Checkbox) findViewById(R.id.sonst);
			 sonstTxt = (EditText) findViewById(R.id.sonstTxt);
			 arztTrans = (Checkbox) findViewById(R.id.arztTrans);
			 arztTransTxt = (EditText) findViewById(R.id.arztTransTxt);
			 exekutiveTrans = (Checkbox) findViewById(R.id.exekutiveTrans);
			 exekutiveTransTxt = (EditText) findViewById(R.id.exekutiveTransTxt);
			 verweigert = (Checkbox) findViewById(R.id.verweigert);
			 naNachgefordert = (Checkbox) findViewById(R.id.naNachgefordert);
			
			 lageGehend = (CheckBox) findViewById(R.id.lageGehend);
			 lageSitzend = (CheckBox) findViewById(R.id.lageSitzend);
			 lageLiegend = (CheckBox) findViewById(R.id.lageLiegend);
			 lageSitzend = (CheckBox) findViewById(R.id.lageSitzend);
			 lageWeitere = (EditText) findViewById(R.id.lageWeitere);
			 radiogr
			 sonstigesBewusstseinTxt = (EditText) findViewById(R.id.sonstigesBewusstseinTxt);
			 radiogr
			 sonstigesAtmungTxt = (EditText) findViewById(R.id.sonstigesAtmungTxt);
			 radiogr
			 sonstigesKreislaufTxt = (EditText) findViewById(R.id.sonstigesKreislaufTxt);
			 radiogr
			 sonstigesKreislaufTxt = (EditText) findViewById(R.id.sonstigesKreislaufTxt);
			 lichtstarrR = (CheckBox) findViewById(R.id.lichtstarrR);
			 radiogr
			 lichtstarrL = (CheckBox) findViewById(R.id.lichtstarrL);
			 atemfrequenz = (EditText) findViewById(R.id.atemfrequenz);
			 herzfrequenz = (EditText) findViewById(R.id.herzfrequenz);
			 blutdruck = (EditText) findViewById(R.id.blutdruck);
			 mds = (CheckBox) findViewById(R.id.mds);
			 radio
			 polytrauma = (CheckBox) findViewById(R.id.polytrauma);
			 akutesAbdomen = (CheckBox) findViewById(R.id.akutesAbdomen);
			 starkeBlutung = (CheckBox) findViewById(R.id.starkeBlutung);
			 veraetzung = (CheckBox) findViewById(R.id.veraetzung);
			 erfrierung = (CheckBox) findViewById(R.id.erfrierung);
			 inhalationstrauma = (CheckBox) findViewById(R.id.inhalationstrauma);
			 verbrennung = (CheckBox) findViewById(R.id.verbrennung);
			 einGrad = (EditText) findViewById(R.id.einGrad);
			 zweiGrad = (EditText) findViewById(R.id.zweiGrad);
			 dreiGrad = (EditText) findViewById(R.id.dreiGrad);
			 sonstigesSchmerzen = (EditText) findViewById(R.id.sonstigesSchmerzen);
			 
			 massnahmenE = (CheckBox) findViewById(R.id.massnahmenE);
			 massnahmenT = (CheckBox) findViewById(R.id.massnahmenT);
			 freimachenE = (CheckBox) findViewById(R.id.freimachenE);
			 freimachenT = (CheckBox) findViewById(R.id.freimachenT);
			 absaugenE = (CheckBox) findViewById(R.id.absaugenE);
			 absaugenT = (CheckBox) findViewById(R.id.absaugenT);
			 beatmungE = (CheckBox) findViewById(R.id.beatmungE);
			 beatmungT = (CheckBox) findViewById(R.id.beatmungT);
			 sauerstoffE = (CheckBox) findViewById(R.id.sauerstoffE);
			 sauerstoffT = (CheckBox) findViewById(R.id.sauerstoffT);
			 sauerstoffEdit = (EditText) findViewById(R.id.sauerstoffEdit);
			 
			  massnahmenKreislaufE = (CheckBox) findViewById(R.id.massnahmenKreislaufE);
			 massnahmenKreislaufE = (CheckBox) findViewById(R.id.massnahmenKreislaufE);
			 herzdruckE = (CheckBox) findViewById(R.id.herzdruckE);
			 herzdruckT = (CheckBox) findViewById(R.id.herzdruckT);
			 defE = (CheckBox) findViewById(R.id.defE);
			 defT = (CheckBox) findViewById(R.id.defT);
			 blutstillungE = (CheckBox) findViewById(R.id.blutstillungE);
			 blutstillungT = (CheckBox) findViewById(R.id.blutstillungT);
			 abbindungE = (CheckBox) findViewById(R.id.abbindungE);
			 abbindungT = (CheckBox) findViewById(R.id.abbindungT);
			 abbindungEdit = (EditText) findViewById(R.id.abbindungEdit);
			 
			  blutdruckE = (CheckBox) findViewById(R.id.blutdruckE);
			 blutdruckT = (CheckBox) findViewById(R.id.blutdruckT);
			 infusionE = (CheckBox) findViewById(R.id.infusionE);
			 infusionT = (CheckBox) findViewById(R.id.infusionT);
			 uerberwachungWE = (CheckBox) findViewById(R.id.uerberwachungWE);
			 uerberwachungWT = (CheckBox) findViewById(R.id.uerberwachungWT);
			 uerberwachungWzE = (CheckBox) findViewById(R.id.uerberwachungWzE);
			 uerberwachungWzT = (CheckBox) findViewById(R.id.uerberwachungWzT);
			 uerberwachungWEdit = (EditText) findViewById(R.id.uerberwachungWEdit);
			 uerberwachungWzEdit = (EditText) findViewById(R.id.uerberwachungWzEdit);
			 
			  bergungKeine = (CheckBox) findViewById(R.id.bergungKeine);
			 bergegriff = (CheckBox) findViewById(R.id.bergegriff);
			 bergetuch = (CheckBox) findViewById(R.id.bergetuch);
			 schaufeltrage = (CheckBox) findViewById(R.id.schaufeltrage);
			 bergungWeitere = (CheckBox) findViewById(R.id.bergungWeitere);
			 bergungEdit = (EditText) findViewById(R.id.bergungEdit);
			 
			  keineImmobilisation = (CheckBox) findViewById(R.id.keineImmobilisation);
			 hws = (CheckBox) findViewById(R.id.hws);
			 vakuumschiene = (CheckBox) findViewById(R.id.vakuumschiene);
			 vakMatraze = (CheckBox) findViewById(R.id.vakMatraze);
			 ked = (CheckBox) findViewById(R.id.ked);
			 immobilisationWeitere = (CheckBox) findViewById(R.id.immobilisationWeitere);
			 immobilisationEdit = (EditText) findViewById(R.id.immobilisationEdit);
			 
			  massnahmenKeine = (CheckBox) findViewById(R.id.massnahmenKeine);
			 helmabnahme = (CheckBox) findViewById(R.id.helmabnahme);
			 wundversorgung = (CheckBox) findViewById(R.id.wundversorgung);
			 
			  lagerung1E = (CheckBox) findViewById(R.id.lagerung1E);
			 lagerung1T = (CheckBox) findViewById(R.id.lagerung1T);
			 lagerung2E = (CheckBox) findViewById(R.id.lagerung2E);
			 lagerung2T = (CheckBox) findViewById(R.id.lagerung2T);
			 lagerung3E = (CheckBox) findViewById(R.id.lagerung3E);
			 lagerung3T = (CheckBox) findViewById(R.id.lagerung3T);
			 lagerung4E = (CheckBox) findViewById(R.id.lagerung4E);
			 lagerung4T = (CheckBox) findViewById(R.id.lagerung4T);
			 lagerung5E = (CheckBox) findViewById(R.id.lagerung5E);
			 lagerung5T = (CheckBox) findViewById(R.id.lagerung5T);
			 lagerung6E = (CheckBox) findViewById(R.id.lagerung6E);
			 lagerung6T = (CheckBox) findViewById(R.id.lagerung6T);
			 lagerung7E = (CheckBox) findViewById(R.id.lagerung7E);
			 lagerung7T = (CheckBox) findViewById(R.id.lagerung7T);
			 lagerung8E = (CheckBox) findViewById(R.id.lagerung8E);
			 lagerung8T = (CheckBox) findViewById(R.id.lagerung8T);
			 lagerung8Edit = (EditText) findViewById(R.id.lagerung8Edit);
			 
			 sonstigeMassnahmen = (EditText) findViewById(R.id.sonstigeMassnahmen);
			 
			 klstillstand = (EditText) findViewById(R.id.klstillstand);
			 naeingetroffen = (EditText) findViewById(R.id.naeingetroffen);
			 uebergabeNa = (EditText) findViewById(R.id.uebergabeNa);
			 endeReanim = (EditText) findViewById(R.id.endeReanim);
			 radio
			 keineEH = (CheckBox) findViewById(R.id.keineEH);
			 hlw = (CheckBox) findViewById(R.id.hlw);
			 padefi = (CheckBox) findViewById(R.id.padefi);
			 sonstEHMassnahmen = (EditText) findViewById(R.id.sonstEHMassnahmen);
			 nichtbeurteilbar = (CheckBox) findViewById(R.id.nichtbeurteilbar);
			 
			 arztAnwesend = (CheckBox) findViewById(R.id.arztAnwesend);
			 arztAnwesendTxt = (EditText) findViewById(R.id.arztAnwesendTxt);
			 angeordnet = (CheckBox) findViewById(R.id.angeordnet);
			 angeordnetTxt = (EditText) findViewById(R.id.angeordnetTxt);
			 venZugang = (CheckBox) findViewById(R.id.venZugang);
			 kristalloid = (CheckBox) findViewById(R.id.kristalloid);
			 intubation = (CheckBox) findViewById(R.id.intubation);
			 artDosierung = (EditText) findViewById(R.id.artDosierung);
			 
			 gleich = (CheckBox) findViewById(R.id.gleich);
			 gebessert = (CheckBox) findViewById(R.id.gebessert);
			 verschlechtert = (CheckBox) findViewById(R.id.verschlechtert);
			 anmerkungenVerlauf = (EditText) findViewById(R.id.anmerkungenVerlauf);
			 spinner
			 anmerkungen = (EditText) findViewById(R.id.anmerkungen);
			 material = (EditText) findViewById(R.id.material);
			 
			
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
			 
			 
			/** btnSpeichern = (Button) findViewById(R.id.btnSpeichern);
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

				
			       /** }
				});*/
					
				 
				
			
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
		i= new Intent(this, BerichtEinsatz.class);
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

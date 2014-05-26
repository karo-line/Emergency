package com.example.emergency.activities.ems;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unused.VideoEms;

import com.example.emergency.BaseActivity;
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

public class ElgaDetailEms extends BaseActivity {

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
	
	public void onCreate(Bundle savedInstanceState) {
			
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
					massnahmenView.setText("Durchgeführte Maßnahmen");
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
					weitereMassnahmenView.setText("Weitere empfohlene Maßnahmen");
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
					abschlussView.setText("Abschließende Bemerkungen");
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
					
					
					
				} else if(befund.equals("briefPflege")) {
					String datum = json_user.getString("datum");
					String sprachcode = json_user.getString("sprache");
					String brieftext = json_user.getString("brieftext");
					String pflegediagnose = json_user.getString("pflegediagnose");
					String mobilitaet = json_user.getString("mobilitaet");
					String koerperpflege = json_user.getString("koerperpflege");
					String ernaehrung = json_user.getString("ernaehrung");
					String ausscheidung = json_user.getString("ausscheidung");
					String hautzustand = json_user.getString("hautzustand");
					String atmung = json_user.getString("atmung");
					String schlafen = json_user.getString("schlafen");
					String schmerz = json_user.getString("schmerz");
					String orientierung = json_user.getString("orientierung");
					String verhalten = json_user.getString("verhalten");
					String kommunikation = json_user.getString("kommunikation");
					String rollenwahrnehmung = json_user.getString("rollenwahrnehmung");
					String vitalparameter = json_user.getString("vitalparameter");
					String medverabreichung = json_user.getString("medverabreichung");
					String anmerkungen = json_user.getString("anmerkungen");
					String entlassungsmngt = json_user.getString("entlassungsmngt");
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
					aufnahmeView.setText("Pflegediagnose:");
					aufnahmeView.setTextColor(Color.BLACK);
					aufnahmeView.setTextSize(20);
					aufnahmeView.setPadding(30, 20, 5, 5);
					ll.addView(aufnahmeView);
					
					TextView aufnahmeText = new TextView(getApplicationContext());
					aufnahmeText.setText(pflegediagnose);
					aufnahmeText.setTextColor(Color.BLACK);
					aufnahmeText.setTextSize(15);
					aufnahmeText.setPadding(30, 5, 5, 5);
					ll.addView(aufnahmeText);
					
					TextView diagnoseView = new TextView(getApplicationContext());
					diagnoseView.setText("Mobilität");
					diagnoseView.setTextColor(Color.BLACK);
					diagnoseView.setTextSize(20);
					diagnoseView.setPadding(30, 20, 5, 5);
					ll.addView(diagnoseView);
					
					TextView diagnoseText = new TextView(getApplicationContext());
					diagnoseText.setText(mobilitaet);
					diagnoseText.setTextColor(Color.BLACK);
					diagnoseText.setTextSize(15);
					diagnoseText.setPadding(30, 5, 5, 5);
					ll.addView(diagnoseText);
					
					
					TextView massnahmenView = new TextView(getApplicationContext());
					massnahmenView.setText("Körperpflege und Kleidung");
					massnahmenView.setTextColor(Color.BLACK);
					massnahmenView.setTextSize(20);
					massnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(massnahmenView);
					
					TextView massnahmenText = new TextView(getApplicationContext());
					massnahmenText.setText(koerperpflege);
					massnahmenText.setTextColor(Color.BLACK);
					massnahmenText.setTextSize(15);
					massnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(massnahmenText);
					
					TextView letzteMedView = new TextView(getApplicationContext());
					letzteMedView.setText("Ernährung");
					letzteMedView.setTextColor(Color.BLACK);
					letzteMedView.setTextSize(20);
					letzteMedView.setPadding(30, 20, 5, 5);
					ll.addView(letzteMedView);
					
					TextView letzteMedText = new TextView(getApplicationContext());
					letzteMedText.setText(ernaehrung);
					letzteMedText.setTextColor(Color.BLACK);
					letzteMedText.setTextSize(15);
					letzteMedText.setPadding(30, 5, 5, 5);
					ll.addView(letzteMedText);
					
					TextView empfMedView = new TextView(getApplicationContext());
					empfMedView.setText("Ausscheidung");
					empfMedView.setTextColor(Color.BLACK);
					empfMedView.setTextSize(20);
					empfMedView.setPadding(30, 20, 5, 5);
					ll.addView(empfMedView);
					
					TextView empfMedText = new TextView(getApplicationContext());
					empfMedText.setText(ausscheidung);
					empfMedText.setTextColor(Color.BLACK);
					empfMedText.setTextSize(15);
					empfMedText.setPadding(30, 5, 5, 5);
					ll.addView(empfMedText);
					
					TextView weitereMassnahmenView = new TextView(getApplicationContext());
					weitereMassnahmenView.setText("Hautzustand");
					weitereMassnahmenView.setTextColor(Color.BLACK);
					weitereMassnahmenView.setTextSize(20);
					weitereMassnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(weitereMassnahmenView);
					
					TextView weitereMassnahmenText = new TextView(getApplicationContext());
					weitereMassnahmenText.setText(hautzustand);
					weitereMassnahmenText.setTextColor(Color.BLACK);
					weitereMassnahmenText.setTextSize(15);
					weitereMassnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(weitereMassnahmenText);
					
					TextView zusammenfassungView = new TextView(getApplicationContext());
					zusammenfassungView.setText("Atmung");
					zusammenfassungView.setTextColor(Color.BLACK);
					zusammenfassungView.setTextSize(20);
					zusammenfassungView.setPadding(30, 20, 5, 5);
					ll.addView(zusammenfassungView);
					
					TextView zusammenfassungText = new TextView(getApplicationContext());
					zusammenfassungText.setText(atmung);
					zusammenfassungText.setTextColor(Color.BLACK);
					zusammenfassungText.setTextSize(15);
					zusammenfassungText.setPadding(30, 5, 5, 5);
					ll.addView(zusammenfassungText);
					
					TextView schlafenView = new TextView(getApplicationContext());
					schlafenView.setText("Schlafen");
					schlafenView.setTextColor(Color.BLACK);
					schlafenView.setTextSize(20);
					schlafenView.setPadding(30, 20, 5, 5);
					ll.addView(schlafenView);
					
					TextView schlafenText = new TextView(getApplicationContext());
					schlafenText.setText(schlafen);
					schlafenText.setTextColor(Color.BLACK);
					schlafenText.setTextSize(15);
					schlafenText.setPadding(30, 5, 5, 5);
					ll.addView(schlafenText);
					
					TextView schmerzView = new TextView(getApplicationContext());
					schmerzView.setText("Schmerz");
					schmerzView.setTextColor(Color.BLACK);
					schmerzView.setTextSize(20);
					schmerzView.setPadding(30, 20, 5, 5);
					ll.addView(schmerzView);
					
					TextView schmerzText = new TextView(getApplicationContext());
					schmerzText.setText(schmerz);
					schmerzText.setTextColor(Color.BLACK);
					schmerzText.setTextSize(15);
					schmerzText.setPadding(30, 5, 5, 5);
					ll.addView(schmerzText);
					
					TextView orientierungView = new TextView(getApplicationContext());
					orientierungView.setText("Orientierung und Bewusstseinslage");
					orientierungView.setTextColor(Color.BLACK);
					orientierungView.setTextSize(20);
					orientierungView.setPadding(30, 20, 5, 5);
					ll.addView(orientierungView);
					
					TextView orientierungText = new TextView(getApplicationContext());
					orientierungText.setText(orientierung);
					orientierungText.setTextColor(Color.BLACK);
					orientierungText.setTextSize(15);
					orientierungText.setPadding(30, 5, 5, 5);
					ll.addView(orientierungText);
					
					TextView verhaltenView = new TextView(getApplicationContext());
					verhaltenView.setText("Soziale Umstände und Verhalten");
					verhaltenView.setTextColor(Color.BLACK);
					verhaltenView.setTextSize(20);
					verhaltenView.setPadding(30, 20, 5, 5);
					ll.addView(verhaltenView);
					
					TextView verhaltenText = new TextView(getApplicationContext());
					verhaltenText.setText(verhalten);
					verhaltenText.setTextColor(Color.BLACK);
					verhaltenText.setTextSize(15);
					verhaltenText.setPadding(30, 5, 5, 5);
					ll.addView(verhaltenText);
					
					TextView kommView = new TextView(getApplicationContext());
					kommView.setText("Kommunikation");
					kommView.setTextColor(Color.BLACK);
					kommView.setTextSize(20);
					kommView.setPadding(30, 20, 5, 5);
					ll.addView(kommView);
					
					TextView kommText = new TextView(getApplicationContext());
					kommText.setText(kommunikation);
					kommText.setTextColor(Color.BLACK);
					kommText.setTextSize(15);
					kommText.setPadding(30, 5, 5, 5);
					ll.addView(kommText);
					
					TextView rollenView = new TextView(getApplicationContext());
					rollenView.setText("Rollenwahrnehmung und Sinnfindung");
					rollenView.setTextColor(Color.BLACK);
					rollenView.setTextSize(20);
					rollenView.setPadding(30, 20, 5, 5);
					ll.addView(rollenView);
					
					TextView rollenText = new TextView(getApplicationContext());
					rollenText.setText(rollenwahrnehmung);
					rollenText.setTextColor(Color.BLACK);
					rollenText.setTextSize(15);
					rollenText.setPadding(30, 5, 5, 5);
					ll.addView(rollenText);
					
					TextView vitalView = new TextView(getApplicationContext());
					vitalView.setText("Vitalparameter");
					vitalView.setTextColor(Color.BLACK);
					vitalView.setTextSize(20);
					vitalView.setPadding(30, 20, 5, 5);
					ll.addView(vitalView);
					
					TextView vitalText = new TextView(getApplicationContext());
					vitalText.setText(vitalparameter);
					vitalText.setTextColor(Color.BLACK);
					vitalText.setTextSize(15);
					vitalText.setPadding(30, 5, 5, 5);
					ll.addView(vitalText);
					
					TextView medView = new TextView(getApplicationContext());
					medView.setText("Medikamentenverabreichung");
					medView.setTextColor(Color.BLACK);
					medView.setTextSize(20);
					medView.setPadding(30, 20, 5, 5);
					ll.addView(medView);
					
					TextView medText = new TextView(getApplicationContext());
					medText.setText(medverabreichung);
					medText.setTextColor(Color.BLACK);
					medText.setTextSize(15);
					medText.setPadding(30, 5, 5, 5);
					ll.addView(medText);
					
					TextView anmerkungView = new TextView(getApplicationContext());
					anmerkungView.setText("Anmerkungen");
					anmerkungView.setTextColor(Color.BLACK);
					anmerkungView.setTextSize(20);
					anmerkungView.setPadding(30, 20, 5, 5);
					ll.addView(anmerkungView);
					
					TextView anmerkungText = new TextView(getApplicationContext());
					anmerkungText.setText(anmerkungen);
					anmerkungText.setTextColor(Color.BLACK);
					anmerkungText.setTextSize(15);
					anmerkungText.setPadding(30, 5, 5, 5);
					ll.addView(anmerkungText);
					
					TextView entlassungsmngtView = new TextView(getApplicationContext());
					entlassungsmngtView.setText("Entlassungsmanagement");
					entlassungsmngtView.setTextColor(Color.BLACK);
					entlassungsmngtView.setTextSize(20);
					entlassungsmngtView.setPadding(30, 20, 5, 5);
					ll.addView(entlassungsmngtView);
					
					TextView entlassungsmngtText = new TextView(getApplicationContext());
					entlassungsmngtText.setText(entlassungsmngt);
					entlassungsmngtText.setTextColor(Color.BLACK);
					entlassungsmngtText.setTextSize(15);
					entlassungsmngtText.setPadding(30, 5, 5, 5);
					ll.addView(entlassungsmngtText);
					
					TextView abschlussView = new TextView(getApplicationContext());
					abschlussView.setText("Abschluss");
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
					
					
				} else if(befund.equals("labor")) {
					String zeitAuftragserfassung = json_user.getString("zeitAuftragserfassung");
					String auftragsdiagnose = json_user.getString("auftragsdiagnose");
					String fragestellung = json_user.getString("fragestellung");
					String befundtext = json_user.getString("befundtext");
					
			
				
					
					TextView datumView = new TextView(getApplicationContext());
					datumView.setText("Erstellungsdatum");
					datumView.setTextColor(Color.BLACK);
					datumView.setTextSize(20);
					datumView.setPadding(30, 20, 5, 5);
					ll.addView(datumView);
					
					TextView datumText = new TextView(getApplicationContext());
					datumText.setText(zeitAuftragserfassung);
					datumText.setTextColor(Color.BLACK);
					datumText.setTextSize(15);
					datumText.setPadding(30, 5, 5, 5);
					ll.addView(datumText);
					
					TextView sprachView = new TextView(getApplicationContext());
					sprachView.setText("Auftragsdiagnose");
					sprachView.setTextColor(Color.BLACK);
					sprachView.setTextSize(20);
					sprachView.setPadding(30, 20, 5, 5);
					ll.addView(sprachView);
					
					TextView sprachText = new TextView(getApplicationContext());
					sprachText.setText(auftragsdiagnose);
					sprachText.setTextColor(Color.BLACK);
					sprachText.setTextSize(15);
					sprachText.setPadding(30, 5, 5, 5);
					ll.addView(sprachText);
					
					TextView brieftextView = new TextView(getApplicationContext());
					brieftextView.setText("Fragestellung");
					brieftextView.setTextColor(Color.BLACK);
					brieftextView.setTextSize(20);
					brieftextView.setPadding(30, 20, 5, 5);
					ll.addView(brieftextView);
					
					TextView briefText = new TextView(getApplicationContext());
					briefText.setText(fragestellung);
					briefText.setTextColor(Color.BLACK);
					briefText.setTextSize(15);
					briefText.setPadding(30, 5, 5, 5);
					ll.addView(briefText);
					
					TextView aufnahmeView = new TextView(getApplicationContext());
					aufnahmeView.setText("Befundtext");
					aufnahmeView.setTextColor(Color.BLACK);
					aufnahmeView.setTextSize(20);
					aufnahmeView.setPadding(30, 20, 5, 5);
					ll.addView(aufnahmeView);
					
					TextView aufnahmeText = new TextView(getApplicationContext());
					aufnahmeText.setText(befundtext);
					aufnahmeText.setTextColor(Color.BLACK);
					aufnahmeText.setTextSize(15);
					aufnahmeText.setPadding(30, 5, 5, 5);
					ll.addView(aufnahmeText);
					
					
					JSONArray json_array=json.getJSONArray("spezimen");
					int arrayLength = json_array.length();
					
					TextView spezimenView = new TextView(getApplicationContext());
					spezimenView.setText("Spezimen");
					spezimenView.setTextColor(Color.BLACK);
					spezimenView.setTextSize(20);
					spezimenView.setPadding(30, 20, 5, 5);
					ll.addView(spezimenView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String probe = jsonNext.getString("probe");
						String zeitpunkt = jsonNext.getString("zeitpunkt");
						String materialart = jsonNext.getString("materialart");
						String entnehmer = jsonNext.getString("entnehmer");
						String einlangenProbe = jsonNext.getString("einlangenProbe");
						String bemerkung = jsonNext.getString("bemerkung");
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(probe);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(zeitpunkt);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(materialart);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
						
						TextView entnehmerTxt = new TextView(getApplicationContext());
						entnehmerTxt.setText(entnehmer);
						entnehmerTxt.setTextColor(Color.BLACK);
						entnehmerTxt.setTextSize(15);
						entnehmerTxt.setPadding(30, 5, 5, 5);
						ll.addView(entnehmerTxt);
						
						TextView einlangenProbeTxt = new TextView(getApplicationContext());
						einlangenProbeTxt.setText(einlangenProbe);
						einlangenProbeTxt.setTextColor(Color.BLACK);
						einlangenProbeTxt.setTextSize(15);
						einlangenProbeTxt.setPadding(30, 5, 5, 5);
						ll.addView(einlangenProbeTxt);
						
						TextView bemerkungTxt = new TextView(getApplicationContext());
						bemerkungTxt.setText(bemerkung);
						bemerkungTxt.setTextColor(Color.BLACK);
						bemerkungTxt.setTextSize(15);
						bemerkungTxt.setPadding(30, 5, 5, 5);
						ll.addView(bemerkungTxt);
						
					}
					
					json_array=json.getJSONArray("antibiogramm");
					arrayLength = json_array.length();
					
					TextView antibiogrammView = new TextView(getApplicationContext());
					antibiogrammView.setText("Antibiogramm");
					antibiogrammView.setTextColor(Color.BLACK);
					antibiogrammView.setTextSize(20);
					antibiogrammView.setPadding(30, 20, 5, 5);
					ll.addView(antibiogrammView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String nameErreger = jsonNext.getString("nameErreger");
						String wirkstoff = jsonNext.getString("wirkstoff");
						String resistenzkennung = jsonNext.getString("resistenzkennung");
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(nameErreger);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(wirkstoff);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(resistenzkennung);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
					}
					
					json_array=json.getJSONArray("erregernachweis");
					arrayLength = json_array.length();
					
					TextView erregernachweisView = new TextView(getApplicationContext());
					erregernachweisView.setText("Erregernachweis");
					erregernachweisView.setTextColor(Color.BLACK);
					erregernachweisView.setTextSize(20);
					erregernachweisView.setPadding(30, 20, 5, 5);
					ll.addView(erregernachweisView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String erreger = jsonNext.getString("erreger");
						String methode = jsonNext.getString("methode");
						String keimzahl = jsonNext.getString("keimzahl");
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(erreger);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(methode);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(keimzahl);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
					}
					
					json_array=json.getJSONArray("hemmkonzentration");
					arrayLength = json_array.length();
					
					TextView hemmkonzView = new TextView(getApplicationContext());
					hemmkonzView.setText("Hemmkonzentration");
					hemmkonzView.setTextColor(Color.BLACK);
					hemmkonzView.setTextSize(20);
					hemmkonzView.setPadding(30, 20, 5, 5);
					ll.addView(hemmkonzView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String nameErreger = jsonNext.getString("nameErreger");
						String wirkstoff = jsonNext.getString("wirkstoff");
						String konzentration = jsonNext.getString("konzentration");
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(nameErreger);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(wirkstoff);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(konzentration);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
					}
					
					json_array=json.getJSONArray("mikroskopie");
					arrayLength = json_array.length();
					
					TextView mikroView = new TextView(getApplicationContext());
					mikroView.setText("Mikroskopie");
					mikroView.setTextColor(Color.BLACK);
					mikroView.setTextSize(20);
					mikroView.setPadding(30, 20, 5, 5);
					ll.addView(mikroView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String eigenschaft = jsonNext.getString("eigenschaft");
						String ergebnis = jsonNext.getString("ergebnis");
						String einheit = jsonNext.getString("einheit");
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(eigenschaft);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(ergebnis);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(einheit);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
					}
					
					json_array=json.getJSONArray("molekular");
					arrayLength = json_array.length();
					
					TextView molekularView = new TextView(getApplicationContext());
					molekularView.setText("Molekular");
					molekularView.setTextColor(Color.BLACK);
					molekularView.setTextSize(20);
					molekularView.setPadding(30, 20, 5, 5);
					ll.addView(molekularView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String analyse = jsonNext.getString("analyse");
						String ergebnis = jsonNext.getString("ergebnis");
						String einheit = jsonNext.getString("einheit");
						String referenzbereich = jsonNext.getString("referenzbereich");
						String interpretation = jsonNext.getString("interpretation");
						
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(analyse);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(ergebnis);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(einheit);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
						
						TextView refTxt = new TextView(getApplicationContext());
						refTxt.setText(referenzbereich);
						refTxt.setTextColor(Color.BLACK);
						refTxt.setTextSize(15);
						refTxt.setPadding(30, 5, 5, 5);
						ll.addView(refTxt);
						
						TextView intTxt = new TextView(getApplicationContext());
						intTxt.setText(interpretation);
						intTxt.setTextColor(Color.BLACK);
						intTxt.setTextSize(15);
						intTxt.setPadding(30, 5, 5, 5);
						ll.addView(intTxt);
					}
					
					json_array=json.getJSONArray("ergebnis");
					arrayLength = json_array.length();
					
					TextView ergebnisView = new TextView(getApplicationContext());
					ergebnisView.setText("Ergebnis");
					ergebnisView.setTextColor(Color.BLACK);
					ergebnisView.setTextSize(20);
					ergebnisView.setPadding(30, 20, 5, 5);
					ll.addView(ergebnisView);

					for(int i=0; i<arrayLength; i++) {
						JSONObject jsonNext = json_array.getJSONObject(i);
						String analyse = jsonNext.getString("analyse");
						String ergebnis = jsonNext.getString("ergebnis");
						String einheit = jsonNext.getString("einheit");
						String referenzbereich = jsonNext.getString("referenzbereich");
						String interpretation = jsonNext.getString("interpretation");
						String delta = jsonNext.getString("delta");
						String externesLabor = jsonNext.getString("externesLabor");
						
						
						TextView nameErregerTxt = new TextView(getApplicationContext());
						nameErregerTxt.setText(analyse);
						nameErregerTxt.setTextColor(Color.BLACK);
						nameErregerTxt.setTextSize(15);
						nameErregerTxt.setPadding(30, 10, 5, 5);
						ll.addView(nameErregerTxt);
						
						TextView wirkstoffTxt = new TextView(getApplicationContext());
						wirkstoffTxt.setText(ergebnis);
						wirkstoffTxt.setTextColor(Color.BLACK);
						wirkstoffTxt.setTextSize(15);
						wirkstoffTxt.setPadding(30, 5, 5, 5);
						ll.addView(wirkstoffTxt);
						
						TextView resistenzkennungTxt = new TextView(getApplicationContext());
						resistenzkennungTxt.setText(einheit);
						resistenzkennungTxt.setTextColor(Color.BLACK);
						resistenzkennungTxt.setTextSize(15);
						resistenzkennungTxt.setPadding(30, 5, 5, 5);
						ll.addView(resistenzkennungTxt);
						
						TextView refTxt = new TextView(getApplicationContext());
						refTxt.setText(referenzbereich);
						refTxt.setTextColor(Color.BLACK);
						refTxt.setTextSize(15);
						refTxt.setPadding(30, 5, 5, 5);
						ll.addView(refTxt);
						
						TextView intTxt = new TextView(getApplicationContext());
						intTxt.setText(interpretation);
						intTxt.setTextColor(Color.BLACK);
						intTxt.setTextSize(15);
						intTxt.setPadding(30, 5, 5, 5);
						ll.addView(intTxt);
						
						TextView deltaTxt = new TextView(getApplicationContext());
						deltaTxt.setText(delta);
						deltaTxt.setTextColor(Color.BLACK);
						deltaTxt.setTextSize(15);
						deltaTxt.setPadding(30, 5, 5, 5);
						ll.addView(deltaTxt);
						
						TextView extLabTxt = new TextView(getApplicationContext());
						extLabTxt.setText(externesLabor);
						extLabTxt.setTextColor(Color.BLACK);
						extLabTxt.setTextSize(15);
						extLabTxt.setPadding(30, 5, 5, 5);
						ll.addView(extLabTxt);
					}
					
					
					
				} else if(befund.equals("diagnostik")) {
					String datum = json_user.getString("datum");
					String sprachcode = json_user.getString("sprache");
					String brieftext = json_user.getString("brieftext");
					String pflegediagnose = json_user.getString("anforderung");
					String mobilitaet = json_user.getString("anamnese");
					String koerperpflege = json_user.getString("indikation");
					String ernaehrung = json_user.getString("patientenstatus");
					String ausscheidung = json_user.getString("aktUntersuchungen");
					String hautzustand = json_user.getString("fruehUntersuchungen");
					String atmung = json_user.getString("fruehBefunde");
					String schlafen = json_user.getString("komplikationen");
					String schmerz = json_user.getString("befund");
					String orientierung = json_user.getString("zusammenfassung");
					String verhalten = json_user.getString("verdachtsdiagnose");
					String kommunikation = json_user.getString("schlussfolgerung");
					String rollenwahrnehmung = json_user.getString("empfehlung");
					String vitalparameter = json_user.getString("addendum");
				
					
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
					aufnahmeView.setText("Anforderung");
					aufnahmeView.setTextColor(Color.BLACK);
					aufnahmeView.setTextSize(20);
					aufnahmeView.setPadding(30, 20, 5, 5);
					ll.addView(aufnahmeView);
					
					TextView aufnahmeText = new TextView(getApplicationContext());
					aufnahmeText.setText(pflegediagnose);
					aufnahmeText.setTextColor(Color.BLACK);
					aufnahmeText.setTextSize(15);
					aufnahmeText.setPadding(30, 5, 5, 5);
					ll.addView(aufnahmeText);
					
					TextView diagnoseView = new TextView(getApplicationContext());
					diagnoseView.setText("Anamnese");
					diagnoseView.setTextColor(Color.BLACK);
					diagnoseView.setTextSize(20);
					diagnoseView.setPadding(30, 20, 5, 5);
					ll.addView(diagnoseView);
					
					TextView diagnoseText = new TextView(getApplicationContext());
					diagnoseText.setText(mobilitaet);
					diagnoseText.setTextColor(Color.BLACK);
					diagnoseText.setTextSize(15);
					diagnoseText.setPadding(30, 5, 5, 5);
					ll.addView(diagnoseText);
					
					
					TextView massnahmenView = new TextView(getApplicationContext());
					massnahmenView.setText("Indikation");
					massnahmenView.setTextColor(Color.BLACK);
					massnahmenView.setTextSize(20);
					massnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(massnahmenView);
					
					TextView massnahmenText = new TextView(getApplicationContext());
					massnahmenText.setText(koerperpflege);
					massnahmenText.setTextColor(Color.BLACK);
					massnahmenText.setTextSize(15);
					massnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(massnahmenText);
					
					TextView letzteMedView = new TextView(getApplicationContext());
					letzteMedView.setText("Patientenstatus");
					letzteMedView.setTextColor(Color.BLACK);
					letzteMedView.setTextSize(20);
					letzteMedView.setPadding(30, 20, 5, 5);
					ll.addView(letzteMedView);
					
					TextView letzteMedText = new TextView(getApplicationContext());
					letzteMedText.setText(ernaehrung);
					letzteMedText.setTextColor(Color.BLACK);
					letzteMedText.setTextSize(15);
					letzteMedText.setPadding(30, 5, 5, 5);
					ll.addView(letzteMedText);
					
					TextView empfMedView = new TextView(getApplicationContext());
					empfMedView.setText("Aktuelle Untersuchung");
					empfMedView.setTextColor(Color.BLACK);
					empfMedView.setTextSize(20);
					empfMedView.setPadding(30, 20, 5, 5);
					ll.addView(empfMedView);
					
					TextView empfMedText = new TextView(getApplicationContext());
					empfMedText.setText(ausscheidung);
					empfMedText.setTextColor(Color.BLACK);
					empfMedText.setTextSize(15);
					empfMedText.setPadding(30, 5, 5, 5);
					ll.addView(empfMedText);
					
					TextView weitereMassnahmenView = new TextView(getApplicationContext());
					weitereMassnahmenView.setText("Frühere Untersuchungen");
					weitereMassnahmenView.setTextColor(Color.BLACK);
					weitereMassnahmenView.setTextSize(20);
					weitereMassnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(weitereMassnahmenView);
					
					TextView weitereMassnahmenText = new TextView(getApplicationContext());
					weitereMassnahmenText.setText(hautzustand);
					weitereMassnahmenText.setTextColor(Color.BLACK);
					weitereMassnahmenText.setTextSize(15);
					weitereMassnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(weitereMassnahmenText);
					
					TextView zusammenfassungView = new TextView(getApplicationContext());
					zusammenfassungView.setText("Frühere Befunde");
					zusammenfassungView.setTextColor(Color.BLACK);
					zusammenfassungView.setTextSize(20);
					zusammenfassungView.setPadding(30, 20, 5, 5);
					ll.addView(zusammenfassungView);
					
					TextView zusammenfassungText = new TextView(getApplicationContext());
					zusammenfassungText.setText(atmung);
					zusammenfassungText.setTextColor(Color.BLACK);
					zusammenfassungText.setTextSize(15);
					zusammenfassungText.setPadding(30, 5, 5, 5);
					ll.addView(zusammenfassungText);
					
					TextView schlafenView = new TextView(getApplicationContext());
					schlafenView.setText("Komplikationen");
					schlafenView.setTextColor(Color.BLACK);
					schlafenView.setTextSize(20);
					schlafenView.setPadding(30, 20, 5, 5);
					ll.addView(schlafenView);
					
					TextView schlafenText = new TextView(getApplicationContext());
					schlafenText.setText(schlafen);
					schlafenText.setTextColor(Color.BLACK);
					schlafenText.setTextSize(15);
					schlafenText.setPadding(30, 5, 5, 5);
					ll.addView(schlafenText);
					
					TextView schmerzView = new TextView(getApplicationContext());
					schmerzView.setText("Befund");
					schmerzView.setTextColor(Color.BLACK);
					schmerzView.setTextSize(20);
					schmerzView.setPadding(30, 20, 5, 5);
					ll.addView(schmerzView);
					
					TextView schmerzText = new TextView(getApplicationContext());
					schmerzText.setText(schmerz);
					schmerzText.setTextColor(Color.BLACK);
					schmerzText.setTextSize(15);
					schmerzText.setPadding(30, 5, 5, 5);
					ll.addView(schmerzText);
					
					TextView orientierungView = new TextView(getApplicationContext());
					orientierungView.setText("Zusammenfassung");
					orientierungView.setTextColor(Color.BLACK);
					orientierungView.setTextSize(20);
					orientierungView.setPadding(30, 20, 5, 5);
					ll.addView(orientierungView);
					
					TextView orientierungText = new TextView(getApplicationContext());
					orientierungText.setText(orientierung);
					orientierungText.setTextColor(Color.BLACK);
					orientierungText.setTextSize(15);
					orientierungText.setPadding(30, 5, 5, 5);
					ll.addView(orientierungText);
					
					TextView verhaltenView = new TextView(getApplicationContext());
					verhaltenView.setText("Verdachtsdiagnose");
					verhaltenView.setTextColor(Color.BLACK);
					verhaltenView.setTextSize(20);
					verhaltenView.setPadding(30, 20, 5, 5);
					ll.addView(verhaltenView);
					
					TextView verhaltenText = new TextView(getApplicationContext());
					verhaltenText.setText(verhalten);
					verhaltenText.setTextColor(Color.BLACK);
					verhaltenText.setTextSize(15);
					verhaltenText.setPadding(30, 5, 5, 5);
					ll.addView(verhaltenText);
					
					TextView kommView = new TextView(getApplicationContext());
					kommView.setText("Schlussfolgerung");
					kommView.setTextColor(Color.BLACK);
					kommView.setTextSize(20);
					kommView.setPadding(30, 20, 5, 5);
					ll.addView(kommView);
					
					TextView kommText = new TextView(getApplicationContext());
					kommText.setText(kommunikation);
					kommText.setTextColor(Color.BLACK);
					kommText.setTextSize(15);
					kommText.setPadding(30, 5, 5, 5);
					ll.addView(kommText);
					
					TextView rollenView = new TextView(getApplicationContext());
					rollenView.setText("Empfehlung");
					rollenView.setTextColor(Color.BLACK);
					rollenView.setTextSize(20);
					rollenView.setPadding(30, 20, 5, 5);
					ll.addView(rollenView);
					
					TextView rollenText = new TextView(getApplicationContext());
					rollenText.setText(rollenwahrnehmung);
					rollenText.setTextColor(Color.BLACK);
					rollenText.setTextSize(15);
					rollenText.setPadding(30, 5, 5, 5);
					ll.addView(rollenText);
					
					TextView vitalView = new TextView(getApplicationContext());
					vitalView.setText("Addendum");
					vitalView.setTextColor(Color.BLACK);
					vitalView.setTextSize(20);
					vitalView.setPadding(30, 20, 5, 5);
					ll.addView(vitalView);
					
					TextView vitalText = new TextView(getApplicationContext());
					vitalText.setText(vitalparameter);
					vitalText.setTextColor(Color.BLACK);
					vitalText.setTextSize(15);
					vitalText.setPadding(30, 5, 5, 5);
					ll.addView(vitalText);
					
					
					
					
				} else if(befund.equals("eMed")) {
					String datum = json_user.getString("einnahmestart");
					String sprachcode = json_user.getString("einnahmeende");
					String brieftext = json_user.getString("einnahmedauer");
					String pflegediagnose = json_user.getString("dosierung");
					String mobilitaet = json_user.getString("artAnwendung");
					String koerperpflege = json_user.getString("therapieart");
					String ernaehrung = json_user.getString("anzahlEinloesung");
					String ausscheidung = json_user.getString("anzahlPackung");
					String hautzustand = json_user.getString("zusatzinfo");
					String atmung = json_user.getString("ergaenzendeInfo");
					String schlafen = json_user.getString("handelsname");
					String schmerz = json_user.getString("pharmazentralnr");
					String orientierung = json_user.getString("zulassungsnr");
					String verhalten = json_user.getString("darreichungsform");
					String kommunikation = json_user.getString("angabenPackung");
					String rollenwahrnehmung = json_user.getString("wirkstoffname");
					String vitalparameter = json_user.getString("weitereWirkstoffe");
				
					
					TextView datumView = new TextView(getApplicationContext());
					datumView.setText("Einnahmestart");
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
					sprachView.setText("Einnahmeende");
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
					brieftextView.setText("Einnahmedauer");
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
					aufnahmeView.setText("Dosierung");
					aufnahmeView.setTextColor(Color.BLACK);
					aufnahmeView.setTextSize(20);
					aufnahmeView.setPadding(30, 20, 5, 5);
					ll.addView(aufnahmeView);
					
					TextView aufnahmeText = new TextView(getApplicationContext());
					aufnahmeText.setText(pflegediagnose);
					aufnahmeText.setTextColor(Color.BLACK);
					aufnahmeText.setTextSize(15);
					aufnahmeText.setPadding(30, 5, 5, 5);
					ll.addView(aufnahmeText);
					
					TextView diagnoseView = new TextView(getApplicationContext());
					diagnoseView.setText("Art der Anwendung");
					diagnoseView.setTextColor(Color.BLACK);
					diagnoseView.setTextSize(20);
					diagnoseView.setPadding(30, 20, 5, 5);
					ll.addView(diagnoseView);
					
					TextView diagnoseText = new TextView(getApplicationContext());
					diagnoseText.setText(mobilitaet);
					diagnoseText.setTextColor(Color.BLACK);
					diagnoseText.setTextSize(15);
					diagnoseText.setPadding(30, 5, 5, 5);
					ll.addView(diagnoseText);
					
					
					TextView massnahmenView = new TextView(getApplicationContext());
					massnahmenView.setText("Therapieart");
					massnahmenView.setTextColor(Color.BLACK);
					massnahmenView.setTextSize(20);
					massnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(massnahmenView);
					
					TextView massnahmenText = new TextView(getApplicationContext());
					massnahmenText.setText(koerperpflege);
					massnahmenText.setTextColor(Color.BLACK);
					massnahmenText.setTextSize(15);
					massnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(massnahmenText);
					
					TextView letzteMedView = new TextView(getApplicationContext());
					letzteMedView.setText("Anzahl der Einlösungen");
					letzteMedView.setTextColor(Color.BLACK);
					letzteMedView.setTextSize(20);
					letzteMedView.setPadding(30, 20, 5, 5);
					ll.addView(letzteMedView);
					
					TextView letzteMedText = new TextView(getApplicationContext());
					letzteMedText.setText(ernaehrung);
					letzteMedText.setTextColor(Color.BLACK);
					letzteMedText.setTextSize(15);
					letzteMedText.setPadding(30, 5, 5, 5);
					ll.addView(letzteMedText);
					
					TextView empfMedView = new TextView(getApplicationContext());
					empfMedView.setText("Anzahl der Packungen");
					empfMedView.setTextColor(Color.BLACK);
					empfMedView.setTextSize(20);
					empfMedView.setPadding(30, 20, 5, 5);
					ll.addView(empfMedView);
					
					TextView empfMedText = new TextView(getApplicationContext());
					empfMedText.setText(ausscheidung);
					empfMedText.setTextColor(Color.BLACK);
					empfMedText.setTextSize(15);
					empfMedText.setPadding(30, 5, 5, 5);
					ll.addView(empfMedText);
					
					TextView weitereMassnahmenView = new TextView(getApplicationContext());
					weitereMassnahmenView.setText("Zusatzinformationen");
					weitereMassnahmenView.setTextColor(Color.BLACK);
					weitereMassnahmenView.setTextSize(20);
					weitereMassnahmenView.setPadding(30, 20, 5, 5);
					ll.addView(weitereMassnahmenView);
					
					TextView weitereMassnahmenText = new TextView(getApplicationContext());
					weitereMassnahmenText.setText(hautzustand);
					weitereMassnahmenText.setTextColor(Color.BLACK);
					weitereMassnahmenText.setTextSize(15);
					weitereMassnahmenText.setPadding(30, 5, 5, 5);
					ll.addView(weitereMassnahmenText);
					
					TextView zusammenfassungView = new TextView(getApplicationContext());
					zusammenfassungView.setText("Ergänzende Informationen");
					zusammenfassungView.setTextColor(Color.BLACK);
					zusammenfassungView.setTextSize(20);
					zusammenfassungView.setPadding(30, 20, 5, 5);
					ll.addView(zusammenfassungView);
					
					TextView zusammenfassungText = new TextView(getApplicationContext());
					zusammenfassungText.setText(atmung);
					zusammenfassungText.setTextColor(Color.BLACK);
					zusammenfassungText.setTextSize(15);
					zusammenfassungText.setPadding(30, 5, 5, 5);
					ll.addView(zusammenfassungText);
					
					TextView schlafenView = new TextView(getApplicationContext());
					schlafenView.setText("Handelsname");
					schlafenView.setTextColor(Color.BLACK);
					schlafenView.setTextSize(20);
					schlafenView.setPadding(30, 20, 5, 5);
					ll.addView(schlafenView);
					
					TextView schlafenText = new TextView(getApplicationContext());
					schlafenText.setText(schlafen);
					schlafenText.setTextColor(Color.BLACK);
					schlafenText.setTextSize(15);
					schlafenText.setPadding(30, 5, 5, 5);
					ll.addView(schlafenText);
					
					TextView schmerzView = new TextView(getApplicationContext());
					schmerzView.setText("Pharmazentralnummer");
					schmerzView.setTextColor(Color.BLACK);
					schmerzView.setTextSize(20);
					schmerzView.setPadding(30, 20, 5, 5);
					ll.addView(schmerzView);
					
					TextView schmerzText = new TextView(getApplicationContext());
					schmerzText.setText(schmerz);
					schmerzText.setTextColor(Color.BLACK);
					schmerzText.setTextSize(15);
					schmerzText.setPadding(30, 5, 5, 5);
					ll.addView(schmerzText);
					
					TextView orientierungView = new TextView(getApplicationContext());
					orientierungView.setText("Zulassungsnummer");
					orientierungView.setTextColor(Color.BLACK);
					orientierungView.setTextSize(20);
					orientierungView.setPadding(30, 20, 5, 5);
					ll.addView(orientierungView);
					
					TextView orientierungText = new TextView(getApplicationContext());
					orientierungText.setText(orientierung);
					orientierungText.setTextColor(Color.BLACK);
					orientierungText.setTextSize(15);
					orientierungText.setPadding(30, 5, 5, 5);
					ll.addView(orientierungText);
					
					TextView verhaltenView = new TextView(getApplicationContext());
					verhaltenView.setText("Darreichungsform");
					verhaltenView.setTextColor(Color.BLACK);
					verhaltenView.setTextSize(20);
					verhaltenView.setPadding(30, 20, 5, 5);
					ll.addView(verhaltenView);
					
					TextView verhaltenText = new TextView(getApplicationContext());
					verhaltenText.setText(verhalten);
					verhaltenText.setTextColor(Color.BLACK);
					verhaltenText.setTextSize(15);
					verhaltenText.setPadding(30, 5, 5, 5);
					ll.addView(verhaltenText);
					
					TextView kommView = new TextView(getApplicationContext());
					kommView.setText("Angabe zur Packung");
					kommView.setTextColor(Color.BLACK);
					kommView.setTextSize(20);
					kommView.setPadding(30, 20, 5, 5);
					ll.addView(kommView);
					
					TextView kommText = new TextView(getApplicationContext());
					kommText.setText(kommunikation);
					kommText.setTextColor(Color.BLACK);
					kommText.setTextSize(15);
					kommText.setPadding(30, 5, 5, 5);
					ll.addView(kommText);
					
					TextView rollenView = new TextView(getApplicationContext());
					rollenView.setText("Wirkstoff");
					rollenView.setTextColor(Color.BLACK);
					rollenView.setTextSize(20);
					rollenView.setPadding(30, 20, 5, 5);
					ll.addView(rollenView);
					
					TextView rollenText = new TextView(getApplicationContext());
					rollenText.setText(rollenwahrnehmung);
					rollenText.setTextColor(Color.BLACK);
					rollenText.setTextSize(15);
					rollenText.setPadding(30, 5, 5, 5);
					ll.addView(rollenText);
					
					TextView vitalView = new TextView(getApplicationContext());
					vitalView.setText("Weitere Wirkstoffe");
					vitalView.setTextColor(Color.BLACK);
					vitalView.setTextSize(20);
					vitalView.setPadding(30, 20, 5, 5);
					ll.addView(vitalView);
					
					TextView vitalText = new TextView(getApplicationContext());
					vitalText.setText(vitalparameter);
					vitalText.setTextColor(Color.BLACK);
					vitalText.setTextSize(15);
					vitalText.setPadding(30, 5, 5, 5);
					ll.addView(vitalText);
					
					
					
					
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

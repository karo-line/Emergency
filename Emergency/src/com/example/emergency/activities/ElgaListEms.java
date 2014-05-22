package com.example.emergency.activities;

import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.LoginFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ElgaListEms extends Activity {

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
		 	einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		 	refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		 	s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
		
			
			/*boolean briefArzt = getIntent().getExtras().getBoolean("briefArzt");
			boolean briefPflege = getIntent().getExtras().getBoolean("briefPflege");
			boolean labor = getIntent().getExtras().getBoolean("labor");
			boolean diagnostik = getIntent().getExtras().getBoolean("diagnostik");
			boolean eMed = getIntent().getExtras().getBoolean("eMed");*/
			
			LinearLayout ll = (LinearLayout) findViewById(R.id.llElga);
			
			 
				Button btnBriefArzt = new Button(getApplicationContext());
				btnBriefArzt.setText("Entlassungsbriefe Arzt");
				ll.addView(btnBriefArzt);
				btnBriefArzt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//�bergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDataListEms.class);
					i.putExtra("server", "briefArzt");
	            	startActivity(i);
	            	
				}
					
				});
			 
				Button btnBriefPflege = new Button(getApplicationContext());
				btnBriefPflege.setText("Entlassungsbriefe Pflege");
				ll.addView(btnBriefPflege);
				btnBriefPflege.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//�bergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDataListEms.class);
					i.putExtra("server", "briefPflege");
	            	startActivity(i);
				}
					
				});
			
				Button btnLabor = new Button(getApplicationContext());
				btnLabor.setText("Laborbefunde");
				ll.addView(btnLabor);
				btnLabor.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//�bergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDataListEms.class);
					i.putExtra("server", "labor");
	            	startActivity(i);
				}
					
				});
			
				Button btnDiagnostik = new Button(getApplicationContext());
				btnDiagnostik.setText("Befunde bildgebende Diagnostik");
				ll.addView(btnDiagnostik);
				btnDiagnostik.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//�bergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDataListEms.class);
					i.putExtra("server", "diagnostik");
	            	startActivity(i);
				}
					
				});
			
				Button btnEMed = new Button(getApplicationContext());
				btnEMed.setText("e-Medikation");
				ll.addView(btnEMed);
				btnEMed.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//�bergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDataListEms.class);
					i.putExtra("server", "eMed");
	            	startActivity(i);
				}
					
				});
			
			
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

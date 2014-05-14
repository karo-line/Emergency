package com.example.emergency.activities;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ElgaChoiceEms extends Activity {

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
			setContentView(R.layout.elga_choice_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
		 	refresh = (TextView) findViewById(R.id.aktualisiert);
		 	einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		 	refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		 	s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			Button btnAuslesen = (Button) findViewById(R.id.btnAuslesen);
			btnAuslesen.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					i= new Intent(getApplicationContext(), ElgaListEms.class);
					i.putExtra("briefArzt", briefArzt);
					i.putExtra("briefPflege", briefPflege);
					i.putExtra("labor", labor);
					i.putExtra("diagnostik", diagnostik);
					i.putExtra("eMed", eMed);
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
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosElga),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
	
	public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.checkbox_briefArzt:
	            if (checked)
	            	briefArzt = true;
	            else
	            	briefArzt = false;
	            break;
	            
	        case R.id.checkbox_briefPflege:
	            if (checked)
	               briefPflege = true;
	            else
	            	briefPflege = false;
	            break;
	            
	        case R.id.checkbox_labor:
	            if (checked)
	               labor = true;
	            else
	            	labor = false;
	            break;
	            
	        case R.id.checkbox_diagnostik:
	            if (checked)
	                diagnostik=true;
	            else
	            	diagnostik=false;
	            break;
	            
	        case R.id.checkbox_eMed:
	            if (checked)
	                eMed=true;
	            else
	            	eMed=false;
	            break;
	    }
	}
}

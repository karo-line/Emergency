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
import android.widget.ImageView;
import android.widget.TextView;

public class Berichte extends Activity {

	private Intent i;
	Button btnWeitere;
	Button btnTransport;
	Button btnEinsatz;
	Button btnKrankenhaus;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.berichte_ems_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			btnWeitere = (Button) findViewById(R.id.btnWeitere);
			btnWeitere.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	i= new Intent(getApplicationContext(), BerichteList.class);
		            	startActivity(i);
		            	overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
		            }
			});
			
			btnTransport = (Button) findViewById(R.id.btnTransport);
			btnTransport.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	i= new Intent(getApplicationContext(), BerichtTransport.class);
		            	startActivity(i);
		            	overridePendingTransition(R.layout.fadeout, R.layout.fadein);	
		            }
			});
			
			btnEinsatz = (Button) findViewById(R.id.btnEinsatz);
			btnEinsatz.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	/**i= new Intent(getApplicationContext(), BerichtEinsatz.class);
		            	startActivity(i);
		            	overridePendingTransition(R.layout.fadeout, R.layout.fadein);	*/
		            }
			});
			
			btnKrankenhaus = (Button) findViewById(R.id.btnKrankenhaus);
			btnKrankenhaus.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	/**i= new Intent(getApplicationContext(), BerichtKrankenhaus.class);
		            	startActivity(i);
		            	overridePendingTransition(R.layout.fadeout, R.layout.fadein);	*/
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
		i= new Intent(this, Berichte.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosBerichte),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}

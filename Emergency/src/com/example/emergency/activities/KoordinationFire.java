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

public class KoordinationFire extends Activity {

	private Intent i;
	Button btnWeitere;
	Button btnOberkommandant;
	Button btnTruppen;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.koordination_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			btnOberkommandant = (Button) findViewById(R.id.oberkommandant);
			btnTruppen = (Button) findViewById(R.id.truppkoordinieren);
			
			btnOberkommandant.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					i= new Intent(getApplicationContext(), OberkommandantKoor.class);
					startActivity(i);		
					overridePendingTransition(R.layout.fadeout, R.layout.fadein);
					
				}
			});
			
			btnTruppen.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					i= new Intent(getApplicationContext(), TruppKoordination.class);
					startActivity(i);		
					overridePendingTransition(R.layout.fadeout, R.layout.fadein);
					
				}
			});
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuFire.class);
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
	
	public void startTicker(View v) {
		i= new Intent(this, TickerFire.class);
		startActivity(i);	
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosKoordination),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}

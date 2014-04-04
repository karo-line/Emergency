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

public class MenuEms extends Activity {
	
	private Intent i;
	
	
	TextView einsatzinfos;
	TextView refresh;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_ems_nexus);
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
		i= new Intent(this, StartEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startTranslator(View v) {
		i= new Intent(this, TranslatorEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startElga(View v) {
		i= new Intent(this, ElgaEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startEkg(View v) {
		i= new Intent(this, EkgEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startGefahrengut(View v) {
		i= new Intent(this, GefahrengutActivity.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startEinsatzprotokoll(View v) {
		i= new Intent(this, Berichte.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosmenu));
	}
	
	public void startReport(View v) {
		i= new Intent(this, Berichte.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void back(View v) {
		 finish();
				
	}
}

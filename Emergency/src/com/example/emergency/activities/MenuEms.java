package com.example.emergency.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.R.menu;
import com.example.emergency.functions.LoginFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MenuEms extends Activity {
	
	private Intent i;
	
	
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu_ems_nexus);
		einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
		refresh = (TextView) findViewById(R.id.aktualisiert);
		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		s = new scheduleEinsatz();
		s.scheduleUpdateText(einsatzinfos, refresh);
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
		SharedPreferences settings = getSharedPreferences("shares",0);
		String einsatzID2 = settings.getString("einsatzID", "nosuchvalue");
		Log.i("einsatz",einsatzID2);
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
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");
		 Log.i("einsatzrefresh",einsatzID);
		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosmenu),einsatzID);
		 }
	}
	
	public void startReport(View v) {
		i= new Intent(this, Berichte.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void back(View v) {
		 finish();
				
	}
	
	public void startDropdown(View v) {
		PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.popupmenu, popup.getMenu());
	    final View menu = this.findViewById(R.id.einsatzinfosmenu);
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

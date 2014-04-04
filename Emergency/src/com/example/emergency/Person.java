package com.example.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Person extends Activity {
	
	private Intent i;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.person);
	}
	
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}
	public void startMenu(View v) {
		i= new Intent(this, Menu.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoPolice.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfos));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

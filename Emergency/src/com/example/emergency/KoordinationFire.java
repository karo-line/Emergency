package com.example.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class KoordinationFire extends Activity {

	private Intent i;
	Button btnWeitere;
	Button btnOberkommandant;
	Button btnTruppen;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.koordination_nexus);
			btnOberkommandant = (Button) findViewById(R.id.oberkommandant);
			btnTruppen = (Button) findViewById(R.id.truppkoordinieren);
			
			btnOberkommandant.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					i= new Intent(getApplicationContext(), OberkommandantKoor.class);
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
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosKoordination));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

package com.example.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Translator extends Activity {

	private Intent i;
	Button btnWeitere;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.translator_nexus);
			btnWeitere = (Button) findViewById(R.id.btnWeitere);
			btnWeitere.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	i= new Intent(getApplicationContext(), TranslatorList.class);
		            	startActivity(i);
		            }
			});
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
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosTranslator));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

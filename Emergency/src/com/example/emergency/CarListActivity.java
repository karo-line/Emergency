package com.example.emergency;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 

 
public class CarListActivity extends Activity {
   
	Intent i;
	UserFunctions userFunctions;
    Button btnLogout;
 
    TextView kennzeichenView;
    TextView personView;
    TextView zulassungsdatumView;
    TextView zulassungsortView;
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         String person = getIntent().getExtras().getString("person");
         String zulassungsdatum = getIntent().getExtras().getString("zulassungsdatum");
         String zulassungsort = getIntent().getExtras().getString("zulassungsort");
         String kennzeichen = getIntent().getExtras().getString("kennzeichen");
         
        
        /**
         * Dashboard Screen for the application
         * */       
        // Check login status in database
        userFunctions = new UserFunctions();
        //if(userFunctions.isUserLoggedIn(getApplicationContext())){
       // user already logged in show databoard
        	requestWindowFeature(Window.FEATURE_NO_TITLE);
        	//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	          //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.carlist);
            
        	einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
	 		refresh = (TextView) findViewById(R.id.aktualisiert);
	 		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
	 		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
	 		s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
            
            kennzeichenView = (TextView) findViewById(R.id.kennzeichen);
            personView = (TextView) findViewById(R.id.person);
            zulassungsdatumView = (TextView) findViewById(R.id.zulassungsdatum);
            zulassungsortView = (TextView) findViewById(R.id.zulassungsort);
            
            
            kennzeichenView.setText("Kennzeichen: "+kennzeichen);
            personView.setText("Zugelassen auf: "+person);
            zulassungsdatumView.setText("Erstzulassung: "+zulassungsdatum);
            zulassungsortView.setText("Zugelassungsort: "+zulassungsort);
         
            
         
            
            btnLogout = (Button) findViewById(R.id.btnLogout);
             
            btnLogout.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), Car.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                    // Closing dashboard screen
                    finish();
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
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosCarList),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}

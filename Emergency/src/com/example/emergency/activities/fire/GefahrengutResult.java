package com.example.emergency.activities.fire;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.UserFunctions;

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
 

 
public class GefahrengutResult extends Activity {
    Intent i;
	
	UserFunctions userFunctions;
    Button btnSuche;
    TextView nummerView;
    TextView nameView;
    TextView brandbekaempfungView;
    TextView leckageView;
    TextView erstehilfeView;
    TextView brandexplosionView;
    TextView gesundheitView;
    TextView anfahrenView;
    TextView schutzvorkehrungView;
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         String nummer = getIntent().getExtras().getString("nummer");
         String name = getIntent().getExtras().getString("name");
         String brandbekaempfung = getIntent().getExtras().getString("brandbekaempfung");
         String leckage = getIntent().getExtras().getString("leckage");
         String erstehilfe = getIntent().getExtras().getString("erstehilfe");
         String brandexplosion = getIntent().getExtras().getString("brandexplosion");
         String gesundheit = getIntent().getExtras().getString("gesundheit");
         String anfahren = getIntent().getExtras().getString("anfahren");
         String schutzvorkehrung = getIntent().getExtras().getString("schutzvorkehrung");

         
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
            setContentView(R.layout.gefahrengutresult_fire_nexus);
            
            einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
    		refresh = (TextView) findViewById(R.id.aktualisiert);
    		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
    		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
    		s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
            
            nummerView = (TextView) findViewById(R.id.nummer);
            nameView = (TextView) findViewById(R.id.name);
            brandbekaempfungView = (TextView) findViewById(R.id.brandbekaempfungBody);
            leckageView = (TextView) findViewById(R.id.leckageBody);
            erstehilfeView = (TextView) findViewById(R.id.erstehilfeBody);
            brandexplosionView = (TextView) findViewById(R.id.brandexplosionBody);
            gesundheitView = (TextView) findViewById(R.id.gesundheitBody);
            anfahrenView = (TextView) findViewById(R.id.anfahrenBody);
            schutzvorkehrungView = (TextView) findViewById(R.id.schutzvorkehrungBody);
            
            
            nummerView.setText("Nummer: "+nummer);
            nameView.setText("Name: "+name);
            brandbekaempfungView.setText(brandbekaempfung);
            leckageView.setText(leckage);
            erstehilfeView.setText(erstehilfe);
            brandexplosionView.setText(brandexplosion);
            gesundheitView.setText(gesundheit);
            anfahrenView.setText(anfahren);
            schutzvorkehrungView.setText(schutzvorkehrung);
            

            
         
            
            btnSuche = (Button) findViewById(R.id.btnSuche);
             
            btnSuche.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    //userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), GefahrengutFire.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                    // Closing dashboard screen
                    finish();
                }
            });
             
        
    }
    
    protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
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
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosGefahrgutResult),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}

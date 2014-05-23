package com.example.emergency.activities.ems;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.UserFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
 

 
public class GefahrengutEmsResult extends Activity {
    Intent i;
	
	UserFunctions userFunctions;
    Button btnSuche;
    TextView nummerView;
    TextView nameView;
    TextView kategorieView;
    TextView klasseView;
    TextView tunnelcodeView;
    TextView eigenschaftenView;
    TextView gefahrenView;
    TextView schutzView;
    TextView allgmassnahmenView;
    TextView stoffaustrittView;
    TextView feuerView;
    TextView erstehilfeView;
    TextView bergungView;
    TextView kleidungView;
    TextView ausruestungView;
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         String nummer = getIntent().getExtras().getString("nummer");
         String name = getIntent().getExtras().getString("name");
         String kategorie = getIntent().getExtras().getString("kategorie");
         String klasse = getIntent().getExtras().getString("klasse");
         String tunnelcode = getIntent().getExtras().getString("tunnelcode");
         String eigenschaften = getIntent().getExtras().getString("eigenschaften");
         String gefahren = getIntent().getExtras().getString("gefahren");
         String schutz = getIntent().getExtras().getString("schutz");
         String allgmassnahmen = getIntent().getExtras().getString("allgmassnahmen");
         String stoffaustritt = getIntent().getExtras().getString("stoffaustritt");
         String feuer = getIntent().getExtras().getString("feuer");
         String erstehilfe = getIntent().getExtras().getString("erstehilfe");
         String bergung = getIntent().getExtras().getString("bergung");
         String kleidung = getIntent().getExtras().getString("kleidung");
         String ausruestung = getIntent().getExtras().getString("ausruestung");
         
         

         
        /**
         * Dashboard Screen for the application
         * */       
        // Check login status in database
       
        //if(userFunctions.isUserLoggedIn(getApplicationContext())){
       // user already logged in show databoard
        	requestWindowFeature(Window.FEATURE_NO_TITLE);
        	//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	          //  WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.gefahrengutresult_ems_nexus);
            
            einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
    		refresh = (TextView) findViewById(R.id.aktualisiert);
    		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
    		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
    		s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
    		
            nummerView = (TextView) findViewById(R.id.nummer);
            nameView = (TextView) findViewById(R.id.name);
            kategorieView = (TextView) findViewById(R.id.kategorieBody);
            klasseView = (TextView) findViewById(R.id.klasseBody);
            tunnelcodeView = (TextView) findViewById(R.id.tunnelcodeBody);
            eigenschaftenView = (TextView) findViewById(R.id.eigenschaftenBody);
            gefahrenView = (TextView) findViewById(R.id.gefahrenBody);
            schutzView = (TextView) findViewById(R.id.schutzBody);
            allgmassnahmenView = (TextView) findViewById(R.id.allgmassnahmenBody);
            stoffaustrittView = (TextView) findViewById(R.id.stoffaustrittBody);
            feuerView = (TextView) findViewById(R.id.feuerBody);
            erstehilfeView = (TextView) findViewById(R.id.erstehilfeBody);
            bergungView = (TextView) findViewById(R.id.bergungBody);
            kleidungView = (TextView) findViewById(R.id.kleidungBody);
            ausruestungView = (TextView) findViewById(R.id.ausruestungBody);
            
            
            nummerView.setText(nummer);
            nameView.setText(name);
            kategorieView.setText(kategorie);
            klasseView.setText(klasse);
            tunnelcodeView.setText(tunnelcode);
            eigenschaftenView.setText(eigenschaften);
            gefahrenView.setText(gefahren);
            schutzView.setText(schutz);
            allgmassnahmenView.setText(allgmassnahmen);
            stoffaustrittView.setText(stoffaustritt);
            feuerView.setText(feuer);
            erstehilfeView.setText(erstehilfe);
            bergungView.setText(bergung);
            kleidungView.setText(kleidung);
            ausruestungView.setText(ausruestung);
            
            
            
            

            
         
            
            btnSuche = (Button) findViewById(R.id.btnSuche);
             
            btnSuche.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    //userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), GefahrengutActivity.class);
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
		i= new Intent(this, MenuEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	
	
	
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");
		 String username = settings.getString("username", "nosuchvalue");
		 Log.i("einsatzrefresh",einsatzID);
		 
		 
		 
   	LoginFunctions func = new LoginFunctions();
   	JSONObject json = func.getEinsatz(username);
   	 try {
				if (json.getString("success") != null) {
				     String res = json.getString("success");
				     if(Integer.parseInt(res) == 1){
				    	 JSONObject jObj = json.getJSONObject("user");
				    	 einsatzID = jObj.getString("einsatzID");
				    	
				    	 
				    	 SharedPreferences.Editor editor = settings.edit();
		            	   editor.remove("einsatzID");
		            	   editor.putString("einsatzID", einsatzID);
		            	   editor.commit();
				     }
				 }
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfos),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
	@SuppressLint("NewApi")
	public void startDropdown(View v) {
		PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.popupmenu, popup.getMenu());
	    final View menu = this.findViewById(R.id.einsatzinfos);
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

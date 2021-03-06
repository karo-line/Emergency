package com.example.emergency.activities.police;

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
 

 
public class DashboardActivity extends Activity {
    Intent i;
	
	UserFunctions userFunctions;
    Button btnLogout;
    TextView loginName;
    TextView loginMail;
    TextView nationalityView;
    TextView birthdayView;
    TextView reasonView;
    TextView aliasView;
    TextView secnameView;
    TextView birthplaceView;
    TextView specialattrView;
    TextView armedView;
    TextView violentView;
    TextView actionsView;
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         String name = getIntent().getExtras().getString("name");
         String sex = getIntent().getExtras().getString("mail");
         String nationality = getIntent().getExtras().getString("nationality");
         String birthday = getIntent().getExtras().getString("birthday");
         String reason = getIntent().getExtras().getString("reason");
         String alias = getIntent().getExtras().getString("alias");
         String secname = getIntent().getExtras().getString("secname");
         String birthplace = getIntent().getExtras().getString("birthplace");
         String specialattr = getIntent().getExtras().getString("specialattr");
         String armed = getIntent().getExtras().getString("armed");
         String violent = getIntent().getExtras().getString("violent");
         String actions = getIntent().getExtras().getString("actions");
         Log.i("dashboard", name);
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
            setContentView(R.layout.dashboard);
            
        	einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
	 		refresh = (TextView) findViewById(R.id.aktualisiert);
	 		if(RefreshInfo.einsatz.isTerminate()) {
				einsatzinfos.setText("Kein Einsatz");
			} else {
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			}
	 		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
	 		s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
            
            loginName = (TextView) findViewById(R.id.login_user);
            loginMail = (TextView) findViewById(R.id.login_mail);
            nationalityView = (TextView) findViewById(R.id.nationality);
            birthdayView = (TextView) findViewById(R.id.birthday);
            reasonView = (TextView) findViewById(R.id.reason);
            secnameView = (TextView) findViewById(R.id.secname);
            birthplaceView = (TextView) findViewById(R.id.birthplace);
            specialattrView = (TextView) findViewById(R.id.specialattr);
            armedView = (TextView) findViewById(R.id.armed);
            violentView = (TextView) findViewById(R.id.violent);
            actionsView = (TextView) findViewById(R.id.actions);
            aliasView = (TextView) findViewById(R.id.alias);
            
            loginName.setText("Name: "+name);
            loginMail.setText("Geschlecht: "+sex);
            nationalityView.setText("Nationalit�t: "+nationality);
            birthdayView.setText("Geburtstag: "+birthday);
            reasonView.setText("Grund des Eintrages: "+reason);
            if(!alias.matches("null")){
            aliasView.setText("Alias: " + alias);
            } else {
            	aliasView.setText("Alias: keine Angabe");
            }
            if(!secname.matches("null")){
            	secnameView.setText("Zweiter Vorname: "+secname);
            } else {
            	secnameView.setText("Zweiter Vorname: keine Angabe");
            }
            if(!birthplace.matches("null")){
            	birthplaceView.setText("Geburtsort: "+birthplace);
            } else {
            	birthplaceView.setText("Geburtsort: keine Angabe");
            }
            if(!specialattr.matches("null")){
            	specialattrView.setText("Besondere Merkmale: "+specialattr);
            } else {
            	specialattrView.setText("Besondere Merkmale: keine Angabe");
            }
            if(!armed.matches("null")){
            	if(armed.matches("0")){
            		armedView.setText("Bewaffnet: nein");
            	} else if(armed.matches("1")) {
            		armedView.setText("Bewaffnet: ja");
            	}
            } else {
            	armedView.setText("Bewaffnet: keine Angabe");
            }
            if(!violent.matches("null")){
            	if(violent.matches("0")){
            		violentView.setText("Gewaltt�tig: nein");
            	} else if(violent.matches("1")) {
            		violentView.setText("Gewaltt�tig: ja");
            	}
            } else {
            	violentView.setText("Gewaltt�tig: keine Angabe");
            }
            if(!actions.matches("null")){
            	actionsView.setText("Ma�nahmen: "+actions);
            } else {
            	actionsView.setText("Ma�nahmen: keine Angabe");
            }
            
         
            
            btnLogout = (Button) findViewById(R.id.btnLogout);
             
            btnLogout.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), PersonActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                    // Closing dashboard screen
                    finish();
                }
            });
             
        /**}else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }*/       
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
	            	   SharedPreferences settings2 = getSharedPreferences("shares",0);
	            	   SharedPreferences.Editor editor2 = settings2.edit();
	            	   editor2.clear();
	            	   editor2.commit();
	            	   finish();
	            	   return true;
	    		   }
				return false;
	    	   }

	    	  });
	    popup.show();

	}
}

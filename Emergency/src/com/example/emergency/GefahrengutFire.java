package com.example.emergency;

import java.util.HashMap;
 

import org.json.JSONException;
import org.json.JSONObject;
 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
 

 
@SuppressLint("NewApi")
public class GefahrengutFire extends Activity {
    
	private Intent i;
	
	Button btnSuche;
    Button btnLinkToRegister;
    EditText inputNummer;
    EditText inputPassword;
    TextView loginErrorMsg;
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NUMMER = "nummer";
    private static String KEY_NAME = "name";
    private static String KEY_BRANDBEKAEMPFUNG = "brandbekaempfung";
    private static String KEY_LECKAGE = "leckage";
    private static String KEY_ERSTEHILFE = "erstehilfe";
    private static String KEY_BRANDEXPLOSION = "brandexplosion";
    private static String KEY_GESUNDHEIT = "gesundheit";
    private static String KEY_ANFAHREN = "anfahren";
    private static String KEY_SCHUTZVORKEHRUNG = "schutzvorkehrung";
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
 
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gefahrengut_fire);
		einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
		refresh = (TextView) findViewById(R.id.aktualisiert);
		
		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		
		scheduleEinsatz s = new scheduleEinsatz();
		s.scheduleUpdateText(einsatzinfos, refresh);
		
        // Importing all assets like buttons, text fields
        inputNummer = (EditText) findViewById(R.id.sucheNummer);
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnSuche = (Button) findViewById(R.id.btnSuche);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.suche_error);
 
        // Login button Click Event
        btnSuche.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String nummer = inputNummer.getText().toString();
                GefahrgutFunction gefahrgutFunction = new GefahrgutFunction();
                JSONObject json = gefahrgutFunction.loginUser(nummer);
 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                             
                            // Clear all previous data in database
                            //userFunction.logoutUser(getApplicationContext());
                            //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_SEX), json.getString(KEY_UID), json_user.getString(KEY_NATIONALITY), json_user.getString(KEY_BIRTHDAY), json_user.getString(KEY_REASON), json_user.getString(KEY_ALIAS));                       
                            Log.i("username", json_user.getString(KEY_NAME));
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), GefahrengutResult.class);
                            dashboard.putExtra("name", json_user.getString(KEY_NAME));
                            dashboard.putExtra("nummer", json_user.getString(KEY_NUMMER));
                            dashboard.putExtra("brandbekaempfung", json_user.getString(KEY_BRANDBEKAEMPFUNG));
                            dashboard.putExtra("leckage", json_user.getString(KEY_LECKAGE));
                            dashboard.putExtra("erstehilfe", json_user.getString(KEY_ERSTEHILFE));
                            dashboard.putExtra("brandexplosion", json_user.getString(KEY_BRANDEXPLOSION));
                            dashboard.putExtra("gesundheit", json_user.getString(KEY_GESUNDHEIT));
                            dashboard.putExtra("anfahren", json_user.getString(KEY_ANFAHREN));
                            dashboard.putExtra("schutzvorkehrung", json_user.getString(KEY_SCHUTZVORKEHRUNG));
                           
                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Person nicht im Register vorhanden!");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosGefahrengutFire),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}
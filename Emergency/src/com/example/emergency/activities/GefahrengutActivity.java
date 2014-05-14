package com.example.emergency.activities;

import java.util.HashMap;
 

import org.json.JSONException;
import org.json.JSONObject;

import unused.DatabaseHandler;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.GefahrgutEmsFunction;
 

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
 

 
public class GefahrengutActivity extends Activity {
    
	private Intent i;
	
	Button btnLogin;
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
    private static String KEY_KLASSE = "klasse";
    private static String KEY_KATEGORIE = "kategorie";
    private static String KEY_TUNNELCODE = "tunnelcode";
    private static String KEY_EIGENSCHAFTEN = "eigenschaften";
    private static String KEY_GEFAHREN = "gefahren";
    private static String KEY_SCHUTZ = "persoenlicherschutz";
    private static String KEY_ALLGMASSNAHMEN = "allgmassnahmen";
    private static String KEY_STOFFAUSTRITT = "massnahmenbeistoffaustritt";
    private static String KEY_FEUER = "massnahmenbeifeuer";
    private static String KEY_ERSTEHILFE = "erstehilfe";
    private static String KEY_BERGUNG = "bergung";
    private static String KEY_KLEIDUNG = "ablegenschutzkleidung";
    private static String KEY_AUSRUESTUNG = "reinigungausruestung";
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gefahrengut);
		
		 einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
 		refresh = (TextView) findViewById(R.id.aktualisiert);
 		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
 		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
 		s = new scheduleEinsatz();
		s.scheduleUpdateText(einsatzinfos, refresh); 
 
        // Importing all assets like buttons, text fields
		inputNummer = (EditText) findViewById(R.id.sucheNummer);
        
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnSuche);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.suche_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	String nummer = inputNummer.getText().toString();
                GefahrgutEmsFunction gefahrgutFunction = new GefahrgutEmsFunction();
                JSONObject json = gefahrgutFunction.sucheGefahrgut(nummer);
 
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
                            Intent dashboard = new Intent(getApplicationContext(), GefahrengutEmsResult.class);
                            dashboard.putExtra("name", json_user.getString(KEY_NAME));
                            dashboard.putExtra("nummer", json_user.getString(KEY_NUMMER));
                            dashboard.putExtra("kategorie", json_user.getString(KEY_KATEGORIE));
                            dashboard.putExtra("klasse", json_user.getString(KEY_KLASSE));
                            dashboard.putExtra("tunnelcode", json_user.getString(KEY_TUNNELCODE));
                            dashboard.putExtra("eigenschaften", json_user.getString(KEY_EIGENSCHAFTEN));
                            dashboard.putExtra("gefahren", json_user.getString(KEY_GEFAHREN));
                            dashboard.putExtra("schutz", json_user.getString(KEY_SCHUTZ));
                            dashboard.putExtra("allgmassnahmen", json_user.getString(KEY_ALLGMASSNAHMEN));
                            dashboard.putExtra("stoffaustritt", json_user.getString(KEY_STOFFAUSTRITT));
                            dashboard.putExtra("feuer", json_user.getString(KEY_FEUER));
                            dashboard.putExtra("erstehilfe", json_user.getString(KEY_ERSTEHILFE));
                            dashboard.putExtra("bergung", json_user.getString(KEY_BERGUNG));
                            dashboard.putExtra("kleidung", json_user.getString(KEY_KLEIDUNG));
                            dashboard.putExtra("ausruestung", json_user.getString(KEY_AUSRUESTUNG));
                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                             
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Gefahrgutnummer nicht vorhanden");
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
		i= new Intent(this, MenuEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoEms.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startReport(View v) {
		i= new Intent(this, Berichte.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);			
	}
	
	public void refreshInfo(View v) {
		SharedPreferences settings = getSharedPreferences("shares",0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosGefahrengut),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}
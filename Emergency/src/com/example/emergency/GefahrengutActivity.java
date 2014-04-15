package com.example.emergency;

import java.util.HashMap;
 

import org.json.JSONException;
import org.json.JSONObject;
 

import android.app.Activity;
import android.content.Intent;
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
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_SEX = "sex";
    private static String KEY_NATIONALITY = "nationality";
    private static String KEY_BIRTHDAY = "birthday";
    private static String KEY_REASON = "reason";
    private static String KEY_ALIAS = "alias";
    private static String KEY_SECNAME = "secName";
    private static String KEY_BIRTHPLACE = "birthplace";
    private static String KEY_SPECIALATTR = "specialattr";
    private static String KEY_ARMED = "armed";
    private static String KEY_VIOLENT = "violent";
    private static String KEY_ACTIONS = "actions";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gefahrengut);
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.sucheNummer);
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnSuche);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.suche_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = "";
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 
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
                            Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            dashboard.putExtra("name", json_user.getString(KEY_NAME));
                            dashboard.putExtra("mail", json_user.getString(KEY_SEX));
                            dashboard.putExtra("nationality", json_user.getString(KEY_NATIONALITY));
                            dashboard.putExtra("birthday", json_user.getString(KEY_BIRTHDAY));
                            dashboard.putExtra("reason", json_user.getString(KEY_REASON));
                            dashboard.putExtra("alias", json_user.getString(KEY_ALIAS));
                            dashboard.putExtra("secname", json_user.getString(KEY_SECNAME));
                            dashboard.putExtra("birthplace", json_user.getString(KEY_BIRTHPLACE));
                            dashboard.putExtra("specialattr", json_user.getString(KEY_SPECIALATTR));
                            dashboard.putExtra("armed", json_user.getString(KEY_ARMED));
                            dashboard.putExtra("violent", json_user.getString(KEY_VIOLENT));
                            dashboard.putExtra("actions", json_user.getString(KEY_ACTIONS));
                            // Close all views before launching Dashboard
                            //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                             
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
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosGefahrengut));
	}
	
	public void back(View v) {
		 finish();
				
	}
}
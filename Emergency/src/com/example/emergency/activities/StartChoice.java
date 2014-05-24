package com.example.emergency.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.ems.StartEms;
import com.example.emergency.activities.fire.StartFire;
import com.example.emergency.activities.police.FullscreenActivity;
import com.example.emergency.functions.LoginFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class StartChoice extends Activity {
	
	Intent i;
	Button btnLogin;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "username";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(R.layout.loginforce);
	
	btnLogin = (Button) findViewById(R.id.btnLogin);
	inputEmail = (EditText) findViewById(R.id.loginEmail);
    inputPassword = (EditText) findViewById(R.id.loginPassword);
	
    inputPassword.setOnEditorActionListener(new OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView v, int actionId,
            KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
        	 
        	login();
        	InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        	imm.hideSoftInputFromWindow(inputPassword.getWindowToken(), 0);	
        	handled = true;
			
        }
        return handled;
        }
    });
    
	btnLogin.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
			String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            LoginFunctions loginFunction = new LoginFunctions();
            JSONObject json = loginFunction.loginUser(email, password);
            loginErrorMsg = (TextView) findViewById(R.id.login_error);
            
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    loginErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                        // user successfully logged in
                        // Store user details in SQLite Database
                        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        //JSONObject json_user = json.getJSONObject("user");
                        String taskforce = json.getString("taskforce");
                        String einsatzID = json.getString("einsatzID");
                        String username = json.getString("username");
                        
                        String rettung= "rettung";	
                        String feuerwehr= "feuerwehr";
                        String oberkommandant= "oberkommandant";
                        String polizei = "polizei";
                        //Log.i("taskforce",json.getString("taskforce"));
                        
                        if (taskforce.equals(rettung)){
                        	Intent startEms = new Intent(getApplicationContext(), StartEms.class);
                        	startEms.putExtra("einsatzID", einsatzID);
                        	startEms.putExtra("username", username);
                        	Log.i("rettung",username);
                        	startActivity(startEms);
                        	finish();
                        } else if (taskforce.equals(feuerwehr)) {
                        	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                        	startFire.putExtra("einsatzID", einsatzID);
                        	startFire.putExtra("taskforce", taskforce);
                        	startFire.putExtra("username", username);
                        	startActivity(startFire);
                        	finish();
                        } else if (taskforce.equals(oberkommandant)) {
                        	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                        	startFire.putExtra("einsatzID", einsatzID);
                        	startFire.putExtra("taskforce", taskforce);
                        	startFire.putExtra("username", username);
                        	startActivity(startFire);
                        	finish();
                        } else if (taskforce.equals(polizei)) {
                        	Intent startPol = new Intent(getApplicationContext(), FullscreenActivity.class);
                        	startPol.putExtra("einsatzID", einsatzID);
                        	startPol.putExtra("username", username);
                        	startActivity(startPol);
                        	finish();
                        }
                        
                        
                         
                        // Clear all previous data in database
                        //userFunction.logoutUser(getApplicationContext());
                        //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                       
                         
                        // Launch Dashboard Screen
                        //Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                         
                        // Close all views before launching Dashboard
                        //dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(dashboard);
                         
                        // Close Login Screen
                        finish();
                    }else{
                        // Error in login
                        loginErrorMsg.setText("Incorrect username/password");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
		}
	});
	
	}
	
	public void login() {
		String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        LoginFunctions loginFunction = new LoginFunctions();
        JSONObject json = loginFunction.loginUser(email, password);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        
        try {
            if (json.getString(KEY_SUCCESS) != null) {
                loginErrorMsg.setText("");
                String res = json.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                    
                    String taskforce = json.getString("taskforce");
                    String einsatzID = json.getString("einsatzID");
                    String username = json.getString("username");
                    String rettung= "rettung";	
                    String feuerwehr= "feuerwehr";
                    String oberkommandant= "oberkommandant";
                    String polizei = "polizei";
                    Log.i("taskforce",json.getString("taskforce"));
                    
                    if (taskforce.equals(rettung)){
                    	Intent startEms = new Intent(getApplicationContext(), StartEms.class);
                    	startEms.putExtra("einsatzID", einsatzID);
                    	startEms.putExtra("username", username);
                    	Log.i("rettung",username);
                    	startActivity(startEms);
                    	finish();
                    } else if (taskforce.equals(feuerwehr)) {
                    	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                    	startFire.putExtra("einsatzID", einsatzID);
                    	startFire.putExtra("taskforce", taskforce);
                    	startFire.putExtra("username", username);
                    	startActivity(startFire);
                    	finish();
                    } else if (taskforce.equals(oberkommandant)) {
                    	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                    	startFire.putExtra("einsatzID", einsatzID);
                    	startFire.putExtra("taskforce", taskforce);
                    	startFire.putExtra("username", username);
                    	startActivity(startFire);
                    	finish();
                    } else if (taskforce.equals(polizei)) {
                    	Intent startPol = new Intent(getApplicationContext(), FullscreenActivity.class);
                    	startPol.putExtra("einsatzID", einsatzID);
                    	startPol.putExtra("username", username);
                    	Log.i("polizei",username);
                    	startActivity(startPol);
                    	finish();
                    }
                    
                    
                    finish();
                }else{
                    // Error in login
                    loginErrorMsg.setText("Incorrect username/password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	
	}
}

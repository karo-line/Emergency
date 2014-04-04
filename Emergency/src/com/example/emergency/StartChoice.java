package com.example.emergency;

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
                        String rettung= "rettung";	
                        String feuerwehr= "feuerwehr";
                        String oberkommandant= "oberkommandant";
                        String polizei = "polizei";
                        Log.i("taskforce",json.getString("taskforce"));
                        
                        if (taskforce.equals(rettung)){
                        	Intent startEms = new Intent(getApplicationContext(), StartEms.class);
                        	startActivity(startEms);
                        	finish();
                        } else if (taskforce.equals(feuerwehr)) {
                        	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                        	startActivity(startFire);
                        	finish();
                        } else if (taskforce.equals(oberkommandant)) {
                        	Intent startFire = new Intent(getApplicationContext(), StartFire.class);
                        	startActivity(startFire);
                        	finish();
                        } else if (taskforce.equals(polizei)) {
                        	Intent startPol = new Intent(getApplicationContext(), FullscreenActivity.class);
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
	
	/**public void startPolice(View v) {
		i= new Intent(this, FullscreenActivity.class);
		ImageView play= (ImageView) findViewById(R.id.police);
	
				        	startActivity(i);
	}
	
	public void startFire(View v) {
		i= new Intent(this, StartFire.class);
		ImageView play= (ImageView) findViewById(R.id.fire);
	
				        	startActivity(i);
	}
	
	public void startEMS(View v) {
		i= new Intent(this, StartEms.class);
	
				        	startActivity(i);
	}
	*/
}

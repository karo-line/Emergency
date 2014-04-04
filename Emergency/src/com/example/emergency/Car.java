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

public class Car extends Activity {

	private Intent i;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_PERSON= "person";
	private static String KEY_ZULASSUNGSDATUM = "zulassungsdatum";
	private static String KEY_ZULASSUNGSORT = "zulassungsort";
	private static String KEY_KENNZEICHEN = "kennzeichen";	
	
	EditText inputKennzeichen;
	TextView loginErrorMsg;
	Button btnSuche;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.car);
			
			inputKennzeichen = (EditText) findViewById(R.id.inputKennzeichen);
			loginErrorMsg = (TextView) findViewById(R.id.login_error);
			btnSuche = (Button) findViewById(R.id.btnSuche);
			
			 btnSuche.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		                String kennzeichen = inputKennzeichen.getText().toString();
		                
		                CarFunction carFunction = new CarFunction();
		                JSONObject json = carFunction.loginUser(kennzeichen);
		 
		                // check for login response
		                try {
		                    if (json.getString(KEY_SUCCESS) != null) {
		                        loginErrorMsg.setText("");
		                        String res = json.getString(KEY_SUCCESS);
		                        if(Integer.parseInt(res) == 1){
		                            // user successfully logged in
		                            // Store user details in SQLite Database
		                            
		                            JSONObject json_user = json.getJSONObject("user");
		                             
		                            // Clear all previous data in database
		                            //userFunction.logoutUser(getApplicationContext());
		                            //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_SEX), json.getString(KEY_UID), json_user.getString(KEY_NATIONALITY), json_user.getString(KEY_BIRTHDAY), json_user.getString(KEY_REASON), json_user.getString(KEY_ALIAS));                       
		                            //Log.i("username", json_user.getString(KEY_NAME));
		                            // Launch Dashboard Screen
		                            Intent carList = new Intent(getApplicationContext(), CarListActivity.class);
		                            carList.putExtra("person", json_user.getString(KEY_PERSON));
		                            carList.putExtra("zulassungsdatum", json_user.getString(KEY_ZULASSUNGSDATUM));
		                            carList.putExtra("zulassungsort", json_user.getString(KEY_ZULASSUNGSORT));
		                            carList.putExtra("kennzeichen", json_user.getString(KEY_KENNZEICHEN));
		                            
		                            // Close all views before launching Dashboard
		                            carList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                            startActivity(carList);
		                            overridePendingTransition(R.layout.fadeout, R.layout.fadein);
		                            // Close Login Screen
		                            finish();
		                        }else{
		                            // Error in login
		                            loginErrorMsg.setText("Kennzeichen nicht vorhanden!");
		                        }
		                    }
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
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
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosCar));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

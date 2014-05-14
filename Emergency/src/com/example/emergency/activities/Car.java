package com.example.emergency.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.CarFunction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.car);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			inputKennzeichen = (EditText) findViewById(R.id.inputKennzeichen);
			loginErrorMsg = (TextView) findViewById(R.id.login_error);
			btnSuche = (Button) findViewById(R.id.btnSuche);
			
			final LinearLayout ll = (LinearLayout) findViewById(R.id.llCar);
			
			
	        inputKennzeichen.setOnEditorActionListener(new OnEditorActionListener()
	        {
	            @Override
	            public boolean onEditorAction(TextView v, int actionId,
	                KeyEvent event) {
	            boolean handled = false;
	            if (actionId == EditorInfo.IME_ACTION_DONE ||
	                    event.getAction() == KeyEvent.ACTION_DOWN &&
	                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
	            	Log.i("entetr","ebnrt"); 
	            	searchClick(inputKennzeichen, ll);
						
	            	handled = true;
					
	            }
	            return handled;
	            }
	        });
			
			
			
			 btnSuche.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	
		            	searchClick(inputKennzeichen, ll);
		            	
		            	
		            	/**
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
		                }*/
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
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosCar),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
	
	public void searchClick(EditText v, LinearLayout llCar) {
		String kennzeichen = v.getText().toString();
		
        CarFunction carFunction = new CarFunction();
        JSONObject json = carFunction.loginUser(kennzeichen);

        
        try {
			if (json.getString(KEY_SUCCESS) != null) {
			     loginErrorMsg.setText("");
			     String res = json.getString(KEY_SUCCESS);
			     if(Integer.parseInt(res) == 1){
        
        // check for login response
        try {
        	JSONArray json_user=json.getJSONArray("user");
			int arrayLength = json_user.length();
			
			for(int i=0; i<arrayLength; i++) {
				final JSONObject jsonNext = json_user.getJSONObject(i);
				String kennz = jsonNext.getString("kennzeichen");
				TextView nameView = new TextView(getApplicationContext());
				nameView.setText(kennz);
				nameView.setTextSize(20);
				nameView.setPadding(30, 30, 5, 5);
				int index = llCar.indexOfChild(findViewById(R.id.inputKennzeichen))+1;
				llCar.addView(nameView,index+i);
				
				nameView.setOnClickListener(new View.OnClickListener() {
					 
		            public void onClick(View view) {
		            	Intent carList = new Intent(getApplicationContext(), CarListActivity.class);
                        try {
                        	
                            carList.putExtra("person", jsonNext.getString(KEY_PERSON));
                            carList.putExtra("zulassungsdatum", jsonNext.getString(KEY_ZULASSUNGSDATUM));
                            carList.putExtra("zulassungsort", jsonNext.getString(KEY_ZULASSUNGSORT));
                            carList.putExtra("kennzeichen", jsonNext.getString(KEY_KENNZEICHEN));
                            
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                      
                        startActivity(carList);
                        overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                        // Close Login Screen
                        //finish();
		            }
				});
			}
        
			
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
		} else {
		   	// Error: Person nciht vorhanden
             loginErrorMsg.setText("Person nicht im Register vorhanden!");
		  }
		 }
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

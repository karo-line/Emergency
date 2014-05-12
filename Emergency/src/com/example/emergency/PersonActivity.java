package com.example.emergency;

import java.util.HashMap;
 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
 

 
public class PersonActivity extends Activity {
    
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
    TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		
		einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
		refresh = (TextView) findViewById(R.id.aktualisiert);
		einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
		s = new scheduleEinsatz();
		s.scheduleUpdateText(einsatzinfos, refresh);
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        
        final LinearLayout ll = (LinearLayout) findViewById(R.id.llPerson);
 
        
        final EditText edittext = (EditText) findViewById(R.id.loginEmail);
        edittext.setOnEditorActionListener(new OnEditorActionListener(){
            @Override
            
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
            	Log.i("enter","inlistener");
            boolean handled = false;
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
            	Toast.makeText(getApplicationContext(), edittext.getText(), Toast.LENGTH_SHORT).show();

                handled = true;
            }
            return handled;
            }
        });
        
        
        
        
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = "";
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 
                // check for login response
                try {
                	JSONArray json_user=json.getJSONArray("user");
    				int arrayLength = json_user.length();
    				
    				for(int i=0; i<arrayLength; i++) {
    					final JSONObject jsonNext = json_user.getJSONObject(i);
    					String name = jsonNext.getString("name");
    					TextView nameView = new TextView(getApplicationContext());
    					nameView.setText(name);
    					nameView.setTextSize(20);
    					nameView.setPadding(30, 30, 5, 5);
    					int index = ll.indexOfChild(findViewById(R.id.loginEmail))+1;
    					ll.addView(nameView,index+i);
    					
    					nameView.setOnClickListener(new View.OnClickListener() {
    						 
    			            public void onClick(View view) {
    			            	Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                                try {
									dashboard.putExtra("name", jsonNext.getString(KEY_NAME));
									dashboard.putExtra("mail", jsonNext.getString(KEY_SEX));
	                                dashboard.putExtra("nationality", jsonNext.getString(KEY_NATIONALITY));
	                                dashboard.putExtra("birthday", jsonNext.getString(KEY_BIRTHDAY));
	                                dashboard.putExtra("reason", jsonNext.getString(KEY_REASON));
	                                dashboard.putExtra("alias", jsonNext.getString(KEY_ALIAS));
	                                dashboard.putExtra("secname", jsonNext.getString(KEY_SECNAME));
	                                dashboard.putExtra("birthplace", jsonNext.getString(KEY_BIRTHPLACE));
	                                dashboard.putExtra("specialattr", jsonNext.getString(KEY_SPECIALATTR));
	                                dashboard.putExtra("armed", jsonNext.getString(KEY_ARMED));
	                                dashboard.putExtra("violent", jsonNext.getString(KEY_VIOLENT));
	                                dashboard.putExtra("actions", jsonNext.getString(KEY_ACTIONS));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                              
                                startActivity(dashboard);
                                overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                                // Close Login Screen
                                //finish();
    			            }
    					});
    				}
                	
                	
                	
                    /**if (json.getString(KEY_SUCCESS) != null) {
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
                            overridePendingTransition(R.layout.fadeout, R.layout.fadein);
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Person nicht im Register vorhanden!");
                        }
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Link to Register Screen
        /**btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });*/
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

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosPersonen),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}
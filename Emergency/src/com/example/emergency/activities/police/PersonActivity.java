package com.example.emergency.activities.police;

import java.util.HashMap;
 

import org.json.JSONArray;
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
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        
        final LinearLayout ll = (LinearLayout) findViewById(R.id.llPerson);
 
        
        final EditText edittext = (EditText) findViewById(R.id.loginEmail);
        edittext.setText("name");
        edittext.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                KeyEvent event)
            {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
            	 String email = inputEmail.getText().toString();
                 String password = "";
                 Log.i("entetr","ebnrt");
                 UserFunctions userFunction = new UserFunctions();
                 JSONObject json = userFunction.loginUser(email, password);
  
                 
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
							String name = jsonNext.getString("name");
							String gebdat = jsonNext.getString("birthday");
							TextView nameView = new TextView(getApplicationContext());
							nameView.setText(name+ " Geburtsdatum: "+gebdat);
							nameView.setTextSize(25);
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
					 	
					 } catch (JSONException e) {
					     e.printStackTrace();
					 }

					handled = true;
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
package com.example.emergency.activities.ems;

import java.util.HashMap;
 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unused.DatabaseHandler;
import unused.VideoEms;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.GefahrgutEmsFunction;
import com.example.emergency.functions.GefahrgutFunction;
import com.example.emergency.functions.LoginFunctions;
 

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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
 

 
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
		inputNummer = (EditText) findViewById(R.id.sucheNummer);
        
        //inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnSuche);
        //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.suche_error);
        
        final LinearLayout ll = (LinearLayout) findViewById(R.id.llGefahrgutEms);
       
        
        inputNummer.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            	 
            	searchClick(inputNummer, ll);
            	InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(inputNummer.getWindowToken(), 0);	
            	handled = true;
				
            }
            return handled;
            }
        });
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	searchClick(inputNummer, ll);
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
	
public void searchClick(EditText v, LinearLayout llGefahrgut) {
		
	String nummer = v.getText().toString();
    GefahrgutEmsFunction gefahrgutFunction = new GefahrgutEmsFunction();
    JSONObject json = gefahrgutFunction.sucheGefahrgut(nummer);

        
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
				String nameGefahrgut = jsonNext.getString(KEY_NAME);
				String nummerGefahrgut = jsonNext.getString(KEY_NUMMER);
				TextView nameView = new TextView(getApplicationContext());
				nameView.setText(nummerGefahrgut+": "+nameGefahrgut);
				nameView.setTextSize(20);
				nameView.setPadding(30, 30, 5, 5);
				int index = llGefahrgut.indexOfChild(findViewById(R.id.sucheNummer))+1;
				llGefahrgut.addView(nameView,index+i);
				
				nameView.setOnClickListener(new View.OnClickListener() {
					 
		            public void onClick(View view) {
		            	Intent dashboard = new Intent(getApplicationContext(), GefahrengutEmsResult.class);
                        try {
                        	 
                             dashboard.putExtra("name", jsonNext.getString(KEY_NAME));
                             dashboard.putExtra("nummer", jsonNext.getString(KEY_NUMMER));
                             dashboard.putExtra("kategorie", jsonNext.getString(KEY_KATEGORIE));
                             dashboard.putExtra("klasse", jsonNext.getString(KEY_KLASSE));
                             dashboard.putExtra("tunnelcode", jsonNext.getString(KEY_TUNNELCODE));
                             dashboard.putExtra("eigenschaften", jsonNext.getString(KEY_EIGENSCHAFTEN));
                             dashboard.putExtra("gefahren", jsonNext.getString(KEY_GEFAHREN));
                             dashboard.putExtra("schutz", jsonNext.getString(KEY_SCHUTZ));
                             dashboard.putExtra("allgmassnahmen", jsonNext.getString(KEY_ALLGMASSNAHMEN));
                             dashboard.putExtra("stoffaustritt", jsonNext.getString(KEY_STOFFAUSTRITT));
                             dashboard.putExtra("feuer", jsonNext.getString(KEY_FEUER));
                             dashboard.putExtra("erstehilfe", jsonNext.getString(KEY_ERSTEHILFE));
                             dashboard.putExtra("bergung", jsonNext.getString(KEY_BERGUNG));
                             dashboard.putExtra("kleidung", jsonNext.getString(KEY_KLEIDUNG));
                             dashboard.putExtra("ausruestung", jsonNext.getString(KEY_AUSRUESTUNG));
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
             loginErrorMsg.setText("Gefahrgut nicht vorhanden!");
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
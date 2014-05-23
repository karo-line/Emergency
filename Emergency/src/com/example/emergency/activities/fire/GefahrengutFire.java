package com.example.emergency.activities.fire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.functions.GefahrgutFunction;
 

 
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
 
        final LinearLayout ll = (LinearLayout) findViewById(R.id.llgefahrgut);
        
        
        inputNummer.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            	Log.i("entetr","ebnrt"); 
            	searchClick(inputNummer, ll);
            	InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(inputNummer.getWindowToken(), 0);	
            	handled = true;
				
            }
            return handled;
            }
        });
        
        
        // Login button Click Event
        btnSuche.setOnClickListener(new View.OnClickListener() {
 
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
	
	public void searchClick(EditText v, LinearLayout llGefahrgut) {
		
        String nummer = v.getText().toString();
        GefahrgutFunction gefahrgutFunction = new GefahrgutFunction();
        JSONObject json = gefahrgutFunction.loginUser(nummer);

        
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
		            	Intent dashboard = new Intent(getApplicationContext(), GefahrengutResult.class);
                        try {
                        	
                        	 
                             dashboard.putExtra("name", jsonNext.getString(KEY_NAME));
                             dashboard.putExtra("nummer", jsonNext.getString(KEY_NUMMER));
                             dashboard.putExtra("brandbekaempfung", jsonNext.getString(KEY_BRANDBEKAEMPFUNG));
                             dashboard.putExtra("leckage", jsonNext.getString(KEY_LECKAGE));
                             dashboard.putExtra("erstehilfe", jsonNext.getString(KEY_ERSTEHILFE));
                             dashboard.putExtra("brandexplosion", jsonNext.getString(KEY_BRANDEXPLOSION));
                             dashboard.putExtra("gesundheit", jsonNext.getString(KEY_GESUNDHEIT));
                             dashboard.putExtra("anfahren", jsonNext.getString(KEY_ANFAHREN));
                             dashboard.putExtra("schutzvorkehrung", jsonNext.getString(KEY_SCHUTZVORKEHRUNG));
                            
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
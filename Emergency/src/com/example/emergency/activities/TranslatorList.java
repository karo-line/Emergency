package com.example.emergency.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.array;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.TranslatorFunction;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class TranslatorList extends Activity {

	private Intent i;
	Button btnWeitere;
	boolean skype;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.translatorlist_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			final Spinner spinner = (Spinner) findViewById(R.id.language_spinner);
			// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			        R.array.languages, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner.setAdapter(adapter);
			
			Button btnOk = (Button) findViewById(R.id.btnOk);
			btnOk.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            	TranslatorFunction translatorFunction = new TranslatorFunction();
			    			JSONObject json = translatorFunction.getTranslator((String) spinner.getSelectedItem());
			    			String username="";
			    			String id="";
			    			try {
								JSONObject jObj = json.getJSONObject("user");
								username = jObj.getString("username");
								id = jObj.getString("id");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			
			            	Uri skypeUri = Uri.parse("skype:" + username + "?call&video=true");
			            	Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

			  			  // Restrict the Intent to being handled by the Skype for Android client only
			            	myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
			            	myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			  			  // Initiate the Intent. It should never fail since we've already established the
			  			  // presence of its handler (although there is an extremely minute window where that
			  			  // handler can go away...)
			  			  	startActivity(myIntent);
			  			  	SharedPreferences settings = getPreferences(0);
						     SharedPreferences.Editor editor = settings.edit();
						     editor.putString("sprachefrei", id);
						     editor.commit();
						     skype = true;
			            }
				}); 
			
	}
	
	protected void onRestart() {
		super.onRestart();
		
		if(skype) {
			SharedPreferences settings = getPreferences(0);
			String spracheId = settings.getString("sprachefrei", "nosuchvalue");
			
			if(!spracheId.equals("nosuchvalue")) {
				TranslatorFunction translatorFunction = new TranslatorFunction();
				translatorFunction.setFrei("1", spracheId);
			} else {
				Log.i("value","nosuchvalue");
			}
			skype=false;
		}
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
		SharedPreferences settings = getPreferences(0);
		 String einsatzID = settings.getString("einsatzID", "nosuchvalue");

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosTranslatorList),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}


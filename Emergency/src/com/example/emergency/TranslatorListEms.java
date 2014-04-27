package com.example.emergency;

import org.json.JSONException;
import org.json.JSONObject;

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

public class TranslatorListEms extends Activity {

	private Intent i;
	Button btnWeitere;
	boolean skype;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.translatorlist_ems_nexus);
			
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
						     editor.putString("sprachefreiId", id);
						     editor.commit();
						     skype = true;
			            }
				}); 
			
	}
	
	protected void onRestart() {
		super.onRestart();
		
		if(skype) {
			SharedPreferences settings = getPreferences(0);
			String spracheId = settings.getString("sprachefreiId", "nosuchvalue");
			
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
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosTranslatorList));
	}
	
	public void back(View v) {
		 finish();
				
	}
}


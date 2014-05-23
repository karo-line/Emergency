package com.example.emergency.activities.ems;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TranslatorFunction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class TranslatorEms extends Activity {

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
			setContentView(R.layout.translator_ems_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinformation);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
			refresh.setText(RefreshInfo.einsatz.getAktualisiert());
			s = new scheduleEinsatz();
			s.scheduleUpdateText(einsatzinfos, refresh);
			
			btnWeitere = (Button) findViewById(R.id.btnWeitere);
			btnWeitere.setOnClickListener(new View.OnClickListener() {
				 
		            public void onClick(View view) {
		            	i= new Intent(getApplicationContext(), TranslatorListEms.class);
		            	startActivity(i);
		            }
			});
			
			
			
			Button btnEnglisch = (Button) findViewById(R.id.btnEnglisch);
			btnEnglisch.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            	TranslatorFunction translatorFunction = new TranslatorFunction();
			    			JSONObject json = translatorFunction.getTranslator("englisch");
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
			            	
			    			translatorFunction.setFrei("0", id);
			    			startActivity(myIntent);
			  			  	SharedPreferences settings = getPreferences(0);
						     SharedPreferences.Editor editor = settings.edit();
						     editor.putString("sprachefreiId", id);
						     editor.commit();
						     skype = true;
			  			
			            }
				});  
			
			Button btnTuerkisch = (Button) findViewById(R.id.btnTuerkisch);
			btnTuerkisch.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            	
			            	TranslatorFunction translatorFunction = new TranslatorFunction();
			    			JSONObject json = translatorFunction.getTranslator("tuerkisch");
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
			
			Button btnKroatisch = (Button) findViewById(R.id.btnKroatisch);
			btnKroatisch.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            	
			            	TranslatorFunction translatorFunction = new TranslatorFunction();
			    			JSONObject json = translatorFunction.getTranslator("kroatisch");
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
			
			Button btnSerbisch = (Button) findViewById(R.id.btnSerbisch);
			btnSerbisch.setOnClickListener(new View.OnClickListener() {
					 
			            public void onClick(View view) {
			            	
			            	TranslatorFunction translatorFunction = new TranslatorFunction();
			    			JSONObject json = translatorFunction.getTranslator("serbisch");
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
	            	   return true;
	    		   }
				return false;
	    	   }

	    	  });
	    popup.show();

	}
}

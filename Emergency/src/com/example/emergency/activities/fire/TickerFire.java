package com.example.emergency.activities.fire;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.BaseActivity;
import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.color;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.entities.Todo;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TickerFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class TickerFire extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	LinearLayout ll;
	
	private HashMap<String, ArrayList<Todo>> map;
	private ArrayList<String> kommArray;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.ticker_nexus);
			
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
			
			ll = (LinearLayout) findViewById(R.id.llTicker);
			
			TickerFunctions tickerFunction = new TickerFunctions();
			JSONObject json = tickerFunction.loadFeed();
			try {
				JSONArray json_user=json.getJSONArray("user");
				int arrayLength = json_user.length();
				
				TextView title = (TextView) findViewById(R.id.title);
				int viewIndex = ll.indexOfChild(title);
				
				for(int i=0; i<arrayLength; i++) {
					JSONObject jsonNext = json_user.getJSONObject(i);
					String action = jsonNext.getString("action");
					String time = jsonNext.getString("time");
					
					TextView actionView = new TextView(getApplicationContext());
					actionView.setText("- "+time+"   "+action);
					actionView.setTextColor(getResources().getColor(R.color.black_overlay));
					actionView.setTextSize(20);
					actionView.setPadding(30, 10, 5, 5);
					ll.addView(actionView, viewIndex+i+1);
				
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Button btnFeed = (Button) findViewById(R.id.plusFeed);
			final int viewIndex = ll.indexOfChild(btnFeed);
			btnFeed.setOnClickListener(new View.OnClickListener() {
				 
		            @SuppressLint("ResourceAsColor")
					public void onClick(View view) {
		            	Log.i("btn","btn");
		            	LinearLayout ll2 = new LinearLayout(getApplicationContext());
		            	
		            	ll.addView(ll2, viewIndex);
		            	final EditText newFeed = new EditText(getApplicationContext());	
		            	newFeed.setText("Neues Feed");
		            	newFeed.setTextColor(getResources().getColor(R.color.black_overlay));
		            	newFeed.setBackgroundColor(R.color.black_overlay);
		            	newFeed.setTextSize(20);
		            	newFeed.setPadding(30, 10, 5, 5);
		            	//newFeed.setWidth(350);
		            	
		            	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		                lp.leftMargin = 30;
		                //lp.rightMargin = 150;
		                lp.topMargin = 30;
		                lp.width = 700;
		                newFeed.setLayoutParams(lp);
		            	
		            	ll2.addView(newFeed);
		            	
		            	Button btnSave = new Button(getApplicationContext());
		            	btnSave.setText("Speichern");
		            	LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		                lp2.topMargin = 25;
		                lp2.rightMargin = 30;
		                //lp2.width = 400;
		                btnSave.setLayoutParams(lp2);
		                
		            	ll2.addView(btnSave);
		            	
		            	btnSave.setOnClickListener(new View.OnClickListener() {
		            		@SuppressLint("NewApi")
							public void onClick(View view) {
		            			String strFeed = newFeed.getText().toString();
		            			
		            			TickerFunctions tickerFunction = new TickerFunctions();
		            			JSONObject json = tickerFunction.storeFeed(strFeed);
		            			//sozusagen activity neu starten
		            			recreate();
		            			
		            		}
		            	});
		            }
			});
			
			

			
	}
	
	
	
	public void startMenu(View v) {
		i= new Intent(this, MenuFire.class);
		startActivity(i);		
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		startActivity(i);		
				
	}
	
	public void startTodo(View v) {
		i= new Intent(this, TickerFire.class);
		startActivity(i);		
				
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

package com.example.emergency.activities.ems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unused.VideoEms;

import com.example.emergency.BaseActivity;
import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.ElgaFunction;
import com.example.emergency.functions.LoginFunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ElgaDataListEms extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	boolean briefArzt;
	boolean briefPflege;
	boolean labor;
	boolean diagnostik;
	boolean eMed;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.elga_list_nexus);
			
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
		
			TextView title = (TextView) findViewById(R.id.elgaTitle);
			LinearLayout ll = (LinearLayout) findViewById(R.id.llElga);
			
			final String server = getIntent().getExtras().getString("server");
			
			
			
			ElgaFunction elgaFunction = new ElgaFunction();
			JSONObject json = elgaFunction.getElgaData(server, "1");
			
			try {
				
				JSONArray json_user=json.getJSONArray("user");
				int arrayLength = json_user.length();
			
				for(int j=0; j<arrayLength; j++) {
					JSONObject jsonNext = json_user.getJSONObject(j);
					String datum = "";
					String body = "";
					String bodyId = "";
					if(server.equals("briefArzt")) {
						title.setText("Entlassungsbrief Arzt");
						datum = jsonNext.getString("datum");
						body = jsonNext.getString("diagnoseentlassung");
						bodyId = jsonNext.getString("briefId");
						
					} else if(server.equals("briefPflege")) {
						title.setText("Entlassungsbrief Pflege");
						datum = jsonNext.getString("datum");
						body = jsonNext.getString("pflegediagnose");
						bodyId = jsonNext.getString("briefId");
						
					} else if(server.equals("labor")) {
						title.setText("Laborbefunde");
						datum = jsonNext.getString("zeitAuftragserfassung");
						body = jsonNext.getString("befundtext");
						bodyId = jsonNext.getString("id");
						
					}else if(server.equals("diagnostik")) {
						title.setText("Befunde bildgebende Diagnostik");
						datum = jsonNext.getString("datum");
						body = jsonNext.getString("anforderung");
						bodyId = jsonNext.getString("id");
						
					}else if(server.equals("eMed")) {
						title.setText("e-Medikation");
						datum = jsonNext.getString("datum");
						body = jsonNext.getString("handelsname");
						bodyId = jsonNext.getString("id");
					}
					
					//anderen server abfragen
		
				final String id = bodyId;
				
				LinearLayout llLine = new LinearLayout(getApplicationContext());
				ll.addView(llLine);
				
				LinearLayout llText = new LinearLayout(getApplicationContext());
				LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
				lpText.width = 500;
				llText.setLayoutParams(lpText);
				llText.setOrientation(LinearLayout.VERTICAL);
				llLine.addView(llText);
				
				LinearLayout llArrow = new LinearLayout(getApplicationContext());
				llLine.addView(llArrow);
				
				
				TextView head = new TextView(getApplicationContext());
				head.setText("Entlassungsbrief: "+ datum);
				head.setTextSize(20);
				head.setPadding(30, 20, 5, 5);
				head.setTextColor(Color.BLACK);
				
				llLine.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//aufruf zu server und starten von neuer activity mit liste der ergbnisse
					//übergabe von parameter das briefArzt angezeigt werden soll
					
					i= new Intent(getApplicationContext(), ElgaDetailEms.class);
					i.putExtra("briefId", id);
					i.putExtra("befund", server);
	            	startActivity(i);
				}
					
				});
				
				TextView bodyView = new TextView(getApplicationContext());
				bodyView.setText(body);
				bodyView.setTextSize(15);
				bodyView.setPadding(30, 5, 5, 5);
				
				
				llText.addView(head);
				llText.addView(bodyView);
				
				ImageView arrow = new ImageView(getApplicationContext());
				arrow.setImageResource(android.R.drawable.ic_menu_more);
				
				llArrow.addView(arrow);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

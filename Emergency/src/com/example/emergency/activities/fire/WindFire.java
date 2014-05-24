package com.example.emergency.activities.fire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.BaseActivity;
import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.activities.StartChoice;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.WindFunction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class WindFire extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.wind_nexus);
			
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
			
			WindFunction windFunction = new WindFunction();
            JSONObject json = windFunction.getWind("http://api.openweathermap.org/data/2.5/weather?",48.202668, 16.344140);
            
            
            try {
				JSONObject json_wind = json.getJSONObject("wind");
				String wind = json_wind.getString("deg");
				String windDir="";
				double windDeg = Double.parseDouble(wind);
				if(windDeg>=22.5&&windDeg<=67.5) {
					windDir="NO";
				} else if (windDeg>67.5&&windDeg<=112.5) {
					windDir="O";
				} else if (windDeg>112.5&&windDeg<=157.5) {
					windDir="SO";
				} else if (windDeg>157.5&&windDeg<=202.5) {
					windDir="S";
				} else if (windDeg>202.5&&windDeg<=247.5) {
					windDir="SW";
				} else if (windDeg>247.5&&windDeg<=292.5) {
					windDir="W";
				} else if (windDeg>292.5&&windDeg<=337.5) {
					windDir="NW";
				} else if (windDeg>337.5 || windDeg<22.5) {
					windDir="N";
				}
				
				TextView windDirection = (TextView) findViewById(R.id.windDirection);
				windDirection.setText("Aktuelle Richtung: "+windDir+ "\n"+"Grad: "+wind);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
            String wind = json.toString();
            
            String forecastUri = "http://api.openweathermap.org/data/2.5/forecast?";
            JSONObject jsonForecast = windFunction.getWind(forecastUri,48.202668, 16.344140);
            Log.i("forecast",jsonForecast.toString());
            try {
				JSONArray json_wind = jsonForecast.getJSONArray("list");
				int arrayLength = json_wind.length();
				
				String windPreText="";
				
				for(int i=1; i<5; i++) {
					JSONObject jsonNext = json_wind.getJSONObject(i);
					JSONObject jsonWind = jsonNext.getJSONObject("wind");
					String degree = jsonWind.getString("deg");	
					String dateTime = jsonNext.getString("dt_txt");
					String windDirPre="";
					
					double windDeg = Double.parseDouble(degree);
					if(windDeg>=22.5&&windDeg<=67.5) {
						windDirPre="NO";
					} else if (windDeg>67.5&&windDeg<=112.5) {
						windDirPre="O";
					} else if (windDeg>112.5&&windDeg<=157.5) {
						windDirPre="SO";
					} else if (windDeg>157.5&&windDeg<=202.5) {
						windDirPre="S";
					} else if (windDeg>202.5&&windDeg<=247.5) {
						windDirPre="SW";
					} else if (windDeg>247.5&&windDeg<=292.5) {
						windDirPre="W";
					} else if (windDeg>292.5&&windDeg<=337.5) {
						windDirPre="NW";
					} else if (windDeg>337.5 || windDeg<22.5) {
						windDirPre="N";
					}
					int degreeInt = (int) windDeg;
					degree = String.valueOf(degreeInt);
					windPreText = windPreText + dateTime+"		~"+degree+"°"+"		-"+windDirPre+ "\n";
					
					Log.i("wind",dateTime+" "+degree);
				}
				TextView windPrediction = (TextView) findViewById(R.id.windPrediction);
				windPrediction.setText(windPreText);
				
				
				
				//TextView windDirection = (TextView) findViewById(R.id.windDirection);
				//windDirection.setText("Aktuelle Richtung: "+windDir+ "\n"+"Grad: "+wind);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
            
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

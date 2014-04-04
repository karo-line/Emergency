package com.example.emergency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WindFire extends Activity {

	private Intent i;
	Button btnWeitere;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.wind_nexus);
			
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
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfoswind));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

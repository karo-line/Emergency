package com.example.emergency;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.emergency.activities.fire.StartFire;
import com.example.emergency.activities.fire.TruppKoordination;
import com.example.emergency.activities.fire.WindFire;
import com.example.emergency.entities.TruppMann;
import com.example.emergency.functions.FahrzeugFunction;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.functions.TruppFunction;
import com.example.emergency.functions.WindFunction;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class scheduleEinsatz {
	private final int FIVE_SECONDS = 60000;
	final Handler handlerInfo;
	final Handler handlerText;
	String wind;
	String sWind;
	
	public scheduleEinsatz() {
		handlerInfo = new Handler();
		handlerText = new Handler();
	}
	
	public void scheduleUpdateInfo(final View v, final String username, final SharedPreferences settings) {
    	
        handlerInfo.postDelayed(new Runnable() {
            public void run() {
            	String einsatzID="0";
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
                sendRefresh(v,einsatzID);  
                refreshCar(v, einsatzID);
                handlerInfo.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
    }
    
    public void sendRefresh(View v, String einsatzID) {
    	RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(v, einsatzID);
    }
    
    @SuppressLint("UseSparseArrays")
	public void refreshCar(View v, String einsatzID) {
    	FahrzeugFunction func = new FahrzeugFunction();
    	JSONObject json = func.getFahrzeuge(einsatzID);
    	
    	try {
			JSONArray json_user=json.getJSONArray("user");
			int arrayLength = json_user.length();
			
			
			HashMap<Integer, MarkerOptions> markerMap = new HashMap<Integer, MarkerOptions>();
			
			for(int i=0; i<arrayLength; i++) {
				JSONObject jsonNext = json_user.getJSONObject(i);
				double lat = Double.valueOf(jsonNext.getString("lat"));
				double lon = Double.valueOf(jsonNext.getString("lon"));
				int rotation = Integer.valueOf(jsonNext.getString("rotation"));
				int id = Integer.valueOf(jsonNext.getString("id"));

		    	MarkerOptions markerOptions = new MarkerOptions()
		        .position(new LatLng(lat,lon))
		        .rotation(rotation)
		        .flat(true);	
		    	
		    	markerMap.put(id, markerOptions);
		    	
			}
			StartFire.setMarkerFireCar(markerMap);
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void scheduleUpdateText(final TextView eInfo, final TextView refresh) {
    	handlerText.postDelayed(new Runnable() {
            public void run() {
            	getEinsatz(eInfo, refresh);         
            	handlerText.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
    }
    
    public void getEinsatz(TextView eInfo, TextView refresh) {
    	if(RefreshInfo.einsatz.isTerminate()) {
    		eInfo.setText("Kein Einsatz");
    	} else {
    		eInfo.setText(RefreshInfo.einsatz.getEinsatz());
    	}
		refresh.setText(RefreshInfo.einsatz.getAktualisiert());
    }
    
    public void stopHandlerText() {
    	handlerText.removeCallbacksAndMessages(null);
    }
    
    public void setWind(String wind) {
    	this.wind=wind;
    }
    
    public String scheduleWind(final LayoutInflater layoutInflater, final View v, final Context c) {
    	sWind ="bla";
    	handlerText.postDelayed(new Runnable() {
            public void run() {
            	sWind =compareWind( layoutInflater,  v, c);         
            	handlerText.postDelayed(this, 300000);
            }
        }, 60000);
    	Log.i("windschedule",sWind);
    	return sWind;
    }
    
    public String compareWind(LayoutInflater layoutInflater, View v, final Context c) {
    	WindFunction windFunction = new WindFunction();
        JSONObject json = windFunction.getWind("http://api.openweathermap.org/data/2.5/weather?",48.202668, 16.344140);
        String windGrad = "";
        String windDir="";
        try {
			JSONObject json_wind = json.getJSONObject("wind");
			windGrad = json_wind.getString("deg");
			
			
			double windDeg = Double.parseDouble(windGrad);
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
			
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(windDir.equals(wind)) {
        	  
    	    View popupView = layoutInflater.inflate(R.layout.popup, null);  
    	             final PopupWindow popupWindow = new PopupWindow(
    	               popupView, 
    	               LayoutParams.WRAP_CONTENT,  
    	                     LayoutParams.WRAP_CONTENT); 
    	             TextView tView = (TextView)popupView.findViewById(R.id.popupText);
    	             tView.setText("Windänderung! Neue Richtung: "+windDir);
    	             //funktionsaufruf get current window
    	             ActivityManager am = (ActivityManager)c.getSystemService(Context.ACTIVITY_SERVICE);
    	             ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
    	            
    	             
    	             Activity currentActivity = ((Emergency)c.getApplicationContext()).getCurrentActivity();
    	             if(currentActivity!=null) {
    	             View neu = currentActivity.findViewById(R.id.einsatzinfos);
    	             
    	             popupWindow.showAsDropDown(neu, 10, -130);
    	             }
    	             
    	             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
    	             btnDismiss.setOnClickListener(new Button.OnClickListener(){

    			     @Override
    			     public void onClick(View v) {
    			      // TODO Auto-generated method stub
    			      popupWindow.dismiss();
    			     }});
    	             
    	             Button btnOpen = (Button)popupView.findViewById(R.id.open);
    	             btnOpen.setOnClickListener(new Button.OnClickListener(){

    			     @Override
    			     public void onClick(View v) {
    			    	 Intent i= new Intent(c, WindFire.class);
    			    	 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			 		c.startActivity(i);
    			     }});
        	return windDir;
        }
        Log.i("windschedule",windDir);
        return "nochange";
    }
    
    
    public void scheduleTimeDiff(final HashMap<String, EditText> dauerList, final String einsatzID, final HashMap<String, ArrayList<TruppMann>> map, final TruppKoordination activity, final HashMap<TruppMann, LinearLayout> layoutMap, final LayoutInflater layoutInflater, final View v) {
    	
    	handlerText.postDelayed(new Runnable() {
            public void run() {
            	try {
					getTimeDiff(dauerList, einsatzID, map, activity, layoutMap, layoutInflater, v);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}         
            	handlerText.postDelayed(this, 60000);
            }
        }, 60000);
    	
    }
    
	public void getTimeDiff(HashMap<String, EditText> dauerList, String einsatzID, HashMap<String, ArrayList<TruppMann>> map, TruppKoordination activity, HashMap<TruppMann, LinearLayout> layoutMap, LayoutInflater layoutInflater, View v) throws JSONException {
	    	
	    TruppFunction truppFunction = new TruppFunction();
	    JSONObject json = truppFunction.getTruppenDiff(einsatzID);
	    
	    JSONArray json_user=json.getJSONArray("user");
		int arrayLength = json_user.length();

		for(int i=0; i<arrayLength; i++) {
			JSONObject jsonNext = json_user.getJSONObject(i);
		    String trupp = jsonNext.getString("trupp");
		    String difftime = jsonNext.getString("difftime");
		    String timeBack = jsonNext.getString("timeBack");
		    String name = jsonNext.getString("name");
		    EditText dauer = dauerList.get(trupp);
		    dauer.setText(difftime);
		   char min =  dauer.getText().toString().charAt(0);
		    if(min == '-') {
		    	
		    	 View popupView = layoutInflater.inflate(R.layout.popup, null);  
	             final PopupWindow popupWindow = new PopupWindow(
	               popupView, 
	               LayoutParams.WRAP_CONTENT,  
	                     LayoutParams.WRAP_CONTENT); 
	             TextView tView = (TextView)popupView.findViewById(R.id.popupText);
	             tView.setText("Zeit ist abgelaufen bei: "+name);
	             popupWindow.showAsDropDown(v, 10, -130);
	             
	             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
	             btnDismiss.setOnClickListener(new Button.OnClickListener(){

			     @Override
			     public void onClick(View v) {
			      // TODO Auto-generated method stub
			      popupWindow.dismiss();
			     }});
	             
	             Button btnOpen = (Button)popupView.findViewById(R.id.open);
	             btnOpen.setOnClickListener(new Button.OnClickListener(){

			     @Override
			     public void onClick(View v) {
			    	 
			     }});
		    	dauer.setText("abgelaufen");
		    }
		    
		    ArrayList<TruppMann> men = map.get(trupp);
		    for(int j=0; j<men.size(); j++) {
		    	if(men.get(j).getName().equals(name)) {
			    	//sollte hier vergleich zwischen ist und war zustand einbauen
		    		if(!men.get(j).getTimeBack().equals(timeBack)) {
			    		men.get(j).setTimeBack(timeBack);
				    	if(!men.get(j).getTimeBack().equals("0000-00-00 00:00:00")) {
				    		//activity.changeTimeBack(layoutMap.get(men.get(j)), timeBack, trupp);
				    		Log.i("aufruf", "changebackbtn");
							
						}
		    		}
		    	}
		    	
		    	
		    }
		}
	    	
	    }
	
public void scheduleKoordination(final String einsatzID, final LayoutInflater layoutInflater, final Context c) {
    	
    	handlerText.postDelayed(new Runnable() {
            public void run() {
            	try {
					getTimeDiff2(einsatzID, layoutInflater, c);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}         
            	handlerText.postDelayed(this, 60000);
            }
        }, 60000);
    	
    }
    
	public void getTimeDiff2(String einsatzID, LayoutInflater layoutInflater, final Context c) throws JSONException {
	    	
	    TruppFunction truppFunction = new TruppFunction();
	    JSONObject json = truppFunction.getTruppenDiff(einsatzID);
	    
	    JSONArray json_user=json.getJSONArray("user");
		int arrayLength = json_user.length();

		for(int i=0; i<arrayLength; i++) {
			JSONObject jsonNext = json_user.getJSONObject(i);
		    String trupp = jsonNext.getString("trupp");
		    String difftime = jsonNext.getString("difftime");
		    String timeBack = jsonNext.getString("timeBack");
		    String name = jsonNext.getString("name");
		    
		   char min =  difftime.charAt(0);
		    if(min == '-') {
		    	
		    	 View popupView = layoutInflater.inflate(R.layout.popup, null);  
	             final PopupWindow popupWindow = new PopupWindow(
	               popupView, 
	               LayoutParams.WRAP_CONTENT,  
	                     LayoutParams.WRAP_CONTENT); 
	             TextView tView = (TextView)popupView.findViewById(R.id.popupText);
	             tView.setText("Zeit ist abgelaufen bei: "+name);
	             
	             Activity currentActivity = ((Emergency)c.getApplicationContext()).getCurrentActivity();
	             
	            if(currentActivity!=null) {
	            if(currentActivity.getComponentName().getClassName().equals("com.example.emergency.activities.fire.TruppKoordination")) {
	            	//do nothing
	            	Log.i("currentTruppAct", currentActivity.getComponentName().getClassName());
	            } else {
	            	Log.i("currentNotTrupp", currentActivity.getComponentName().getClassName());
	            	View neu = currentActivity.findViewById(R.id.einsatzinfos);
		             popupWindow.showAsDropDown(neu, 10, -130);
	            
	             
	             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
	             btnDismiss.setOnClickListener(new Button.OnClickListener(){

			     @Override
			     public void onClick(View v) {
			      // TODO Auto-generated method stub
			      popupWindow.dismiss();
			     }});
	             
	             Button btnOpen = (Button)popupView.findViewById(R.id.open);
	             btnOpen.setOnClickListener(new Button.OnClickListener(){

			     @Override
			     public void onClick(View v) {
			    	 Intent i= new Intent(c, TruppKoordination.class);
			    	 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 		c.startActivity(i);
			     }});
	            }
	            }
		    
		    }
		 
		}
	    	
	    }
	}

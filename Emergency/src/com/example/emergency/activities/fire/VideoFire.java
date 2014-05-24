package com.example.emergency.activities.fire;

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

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoFire extends BaseActivity {

	private Intent i;
	Button btnWeitere;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.video_fire_nexus);
			
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
			
			String id = "1";
			//VideoFunction videoFunction = new VideoFunction();
		    //JSONObject json = videoFunction.loginUser(id);
		    
		   /** try {
		         if (json.getString(KEY_SUCCESS) != null) {
		        	 
		         }
		    } catch (JSONException e) {
		         e.printStackTrace();
		     }*/
		    
		   /** Intent intent = new Intent(Intent.ACTION_VIEW); 
		    intent.setDataAndType(Uri.parse("http://lmattano.dyndns-at-home.com:8443/videos/test.avi"), "video/avi");
		    startActivity(intent);
		    */
			
			
			String vL = RefreshInfo.videoLink;
			Log.i("videoLink", vL);
		    VideoView vv = (VideoView)this.findViewById(R.id.videoView);
		    //String fileName = "android.resource://" + getPackageName() + "/" + R.raw.bunny;
		    String fileName = "http://lmattano.dyndns-at-home.com:8443/videos/"+vL;
		    //String fileName ="http://www.ebookfrenzy.com/android_book/movie.mp4";
		    vv.setVideoPath(fileName);
		    MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(vv);
			vv.setMediaController(mediaController);
		    
		    vv.start();
		    //vv.start();
			
	}
	
	public void startMenu(View v) {
		i= new Intent(this, MenuFire.class);
		startActivity(i);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, MenuFire.class);
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

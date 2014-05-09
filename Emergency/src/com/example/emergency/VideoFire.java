package com.example.emergency;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoFire extends Activity {

	private Intent i;
	Button btnWeitere;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	TextView einsatzinfos;
	TextView refresh;
	scheduleEinsatz s;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.video_fire_nexus);
			
			einsatzinfos = (TextView) findViewById(R.id.einsatzinfos);
			refresh = (TextView) findViewById(R.id.aktualisiert);
			einsatzinfos.setText(RefreshInfo.einsatz.getEinsatz());
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

		 if(!einsatzID.equals("nosuchvalue")) {
				RefreshInfo refreshInfo = new RefreshInfo();
				refreshInfo.refresh(this.findViewById(R.id.einsatzinfosVideo),einsatzID);
		 }
	}
	
	public void back(View v) {
		 finish();
				
	}
}

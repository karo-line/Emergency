package com.example.emergency;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPolice extends Activity {

	private Intent i;
	Button btnWeitere;
	
	protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.video_police_nexus);
			String vL = RefreshInfo.videoLink;
			
		    VideoView vv = (VideoView)this.findViewById(R.id.videoView);
		    
		    String fileName = "http://lmattano.dyndns-at-home.com:8443/videos/"+vL;
		    
		    vv.setVideoPath(fileName);
		    MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(vv);
			vv.setMediaController(mediaController);
		    
		    vv.start();
	}
	
	public void startMenu(View v) {
		i= new Intent(this, Menu.class);
		startActivity(i);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, Menu.class);
		startActivity(i);
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
				
	}
	
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosVideoPolice));
	}
	
	public void back(View v) {
		 finish();
				
	}
}

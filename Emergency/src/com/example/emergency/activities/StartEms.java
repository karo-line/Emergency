package com.example.emergency.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import com.example.emergency.R;
import com.example.emergency.RefreshInfo;
import com.example.emergency.scheduleEinsatz;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.AddressThread;
import com.example.emergency.functions.LoginFunctions;
import com.example.emergency.util.SystemUiHider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class StartEms extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	
	private Intent i;
	GoogleMap map;
	boolean cctvStarted = false;
	ArrayList<Marker> markerList;
	ArrayList<Marker> markerFireCar;
	private LocationClient mLocationClient;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	ImageView deleteMarker;
	LatLng origMarkerPos;
	String einsatzID;
	String username;
	scheduleEinsatz s;
	
	public static class ErrorDialogFragment extends DialogFragment {

	    // Global field to contain the error dialog
	    private Dialog mDialog;

	    // Default constructor. Sets the dialog field to null
	    public ErrorDialogFragment() {
	        super();
	        mDialog = null;
	    }

	    // Set the dialog to display
	    public void setDialog(Dialog dialog) {
	        mDialog = dialog;
	    }

	    // Return a Dialog to the DialogFragment.
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        return mDialog;
	    }
	}
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	      //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.map_ems);
		
		Bundle b = getIntent().getExtras();
		if(b!= null) {
			einsatzID = getIntent().getExtras().getString("einsatzID");
			username = getIntent().getExtras().getString("username");
			SharedPreferences settings = getSharedPreferences("shares",0);
		     SharedPreferences.Editor editor = settings.edit();
		     editor.putString("einsatzID", einsatzID);
		     editor.putString("username", username);
		     editor.commit();
		} else {
			SharedPreferences settings = getSharedPreferences("shares",0);
			einsatzID = settings.getString("einsatzID", "nosuchvalue");
			username = settings.getString("username", "nosuchvalue");
		}
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(findViewById(R.id.einsatzinfosMapEms), einsatzID);
		
		Log.i("username",username);
		s = new scheduleEinsatz();
		s.scheduleUpdateInfo(findViewById(R.id.einsatzinfosMapEms), username);
		
		mLocationClient = new LocationClient(this, this, this);
		//LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,getWindowManager().getDefaultDisplay().getHeight()-100);
		
		FragmentManager fm = getFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.map);
        
        map = ((com.google.android.gms.maps.MapFragment) frag).getMap();
        map.setMyLocationEnabled(true);
        Location myLocation = map.getMyLocation();
        LatLng latLng = new LatLng(48.2071843, 16.3587499);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
        
		
      /**  map.setOnCameraChangeListener(new OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition position) {
            if (cctvStarted) {
            	cctvHelper();
            }
            
        }
    });*/
        
        
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}


	
	public void startMenu(View v) {
		i= new Intent(this, MenuEms.class);
		SharedPreferences settings = getSharedPreferences("shares",0);
		String einsatzID2 = settings.getString("einsatzID", "nosuchvalue");
		Log.i("einsatz",einsatzID2);
		SharedPreferences settings2 = getSharedPreferences("shares",0);
		 String nrBericht = settings2.getString("transportBericht", "nosuchvalue");
		 Log.i("nrBericht", nrBericht);
		startActivity(i);		
				
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoEms.class);
		startActivity(i);		
				
	}
	
	public void startReport(View v) {
		i= new Intent(this, Berichte.class);
		startActivity(i);	
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosMapEms), einsatzID);
	}
	
	
	
	public void back(View v) {
		 finish();
				
	}
	
	@SuppressLint("NewApi")
	public void startDropdown(View v) {
		PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.popupmenu, popup.getMenu());
	    final View menu = this.findViewById(R.id.einsatzinfosMapEms);
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
	
    private void getLatLong(String xml) {
    	String[] nodes = xml.split("<node");
    	int length = nodes.length;
    	for(int i=0; i<length; i++) {
    		String node = nodes[i];
    		int index=node.indexOf("lat");
    		String sub;
    		double lat = 0;
    		double lon;
    		String sub2="";
    		if (index!=-1) {
			sub=node.substring(index+5, index+15);
			lat = Double.parseDouble(sub);
    		}
			index=node.indexOf("lon");
			if (index!=-1) {
			sub=node.substring(index+5, index+15);
			lon = Double.parseDouble(sub);
			
			int index1=node.indexOf("street");
			if (index1!=-1) {
			sub = node.substring(index1);
			index1=sub.indexOf("v=");
			int index2=sub.indexOf("/>");
			sub=sub.substring(index1+3, index2-1);
			Log.i("adr", sub);
			}
			
			int index3=node.indexOf("housenumber");
			if (index3!=-1) {
			sub2 = node.substring(index3);
			index3=sub2.indexOf("v=");
			int index4=sub2.indexOf("/>");
			sub2=sub2.substring(index3+3, index4-1);
			Log.i("adr2", sub2);
			}
			
			//Abfrage starten welche Adresse bei lat lon ist
			String adr = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=true_or_false";
			String x="";
			try {
				x = new AddressThread(adr).execute(adr).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] nodes2 = x.split("formatted");
	    	int length2 = nodes2.length;
	    	String subAdr="";
	    	if(length2>0) {
	    		String node2 = nodes2[1];
	    		int indexAdr1=node2.indexOf(":");
	    		int indexAdr2=node2.indexOf(",");
	    		if (indexAdr1!=-1 && indexAdr2!=-1) {
	    			subAdr=node2.substring(indexAdr1+3, indexAdr2);
	        		}
	    	}
			
			Marker marker = map.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lon))
	        .title("Hydrant")
	        .snippet(subAdr));
	        marker.setVisible(true);
	        markerList.add(marker);
	        
			}
			
    	}
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        if(isGooglePlayServicesAvailable()){
            mLocationClient.connect();
        }

    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    @Override
    protected void onActivityResult(
                    int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                /*
                 * If the result code is Activity.RESULT_OK, try
                 * to connect again
                 */
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mLocationClient.connect();
                        break;
                }

        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog( resultCode,
                                                                                                                  this,
                                                                                                                  CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Location location = mLocationClient.getLastLocation();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        map.animateCamera(cameraUpdate);
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
           Toast.makeText(getApplicationContext(), "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
        }
    }
}


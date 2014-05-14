package com.example.emergency;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.emergency.util.SystemUiHider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
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
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class FullscreenActivity extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks, 
GooglePlayServicesClient.OnConnectionFailedListener{
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	
	//private static final boolean AUTO_HIDE = false;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	//private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	//private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	//private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	//private SystemUiHider mSystemUiHider;
	private Intent i;
	GoogleMap map;
	boolean cctvStarted = false;
	ArrayList<Marker> markerList;
	private LocationClient mLocationClient;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	String einsatzID;
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
	        //    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activitynexus);
		
		Bundle b = getIntent().getExtras();
		if(b!= null) {
			einsatzID = getIntent().getExtras().getString("einsatzID");
			SharedPreferences settings = getPreferences(0);
		     SharedPreferences.Editor editor = settings.edit();
		     editor.putString("einsatzID", einsatzID);
		     editor.commit();
		} else {
			SharedPreferences settings = getPreferences(0);
			einsatzID = settings.getString("einsatzID", "nosuchvalue");
		}
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(findViewById(R.id.einsatzinfosMapPolice), einsatzID);
		
		scheduleEinsatz s = new scheduleEinsatz();
		s.scheduleUpdateInfo(findViewById(R.id.einsatzinfosMapPolice), einsatzID);
		
		
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
        
        

        markerList = new ArrayList<Marker>();
		//final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//final View contentView = findViewById(R.id.fullscreen_content);
		

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		/**mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});*/

		// Set up the user interaction to manually show or hide the system UI.
		/**contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		}); */

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		
		
		//findViewById(R.id.cctv).setOnClickListener(homeTouchListener);
		
		//findViewById(R.id.arrow).setOnClickListener(homeTouchListener);
        map.setOnCameraChangeListener(new OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition position) {
            if (cctvStarted) {
            	cctvHelper();
            }
            
        }
    });
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	/**View.OnClickListener homeTouchListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		
	        	startActivity(i);
				
		}
	};*/

	/**Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};*/

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	/**private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}*/
	
	public void startMenu(View v) {
		i= new Intent(this, Menu.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startVideo(View v) {
		i= new Intent(this, VideoPolice.class);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);		
	}
	
	public void startCctv(View v) {
		ImageView imageView=(ImageView)findViewById(R.id.cctv);
		if (cctvStarted==false) {
			
	        
			           
			imageView.setBackgroundColor(Color.rgb(100, 100, 50));
		try {
			LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
            LatLng l = bounds.northeast;
            double lat = l.latitude;
            double lon = l.longitude;
            LatLng sw = bounds.southwest;
            double latsw = sw.latitude;
            double lonsw = sw.longitude;
            Log.i("zoom", Double.toString(lat));
            String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><osm-script>"+
    				"<query type=\"node\">"+
    				"<has-kv k=\"man_made\" v=\"surveillance\"/>"+
    				"<bbox-query s=\""+latsw+"\" w=\""+lonsw+"\" n=\""+lat+"\" e=\""+lon+"\"/>"+
    				"</query>"+
    				"<print/></osm-script>";
            
			String x = new OverpassThread(xml).execute("http://overpass-api.de/api/interpreter").get();
            //String x = new OverpassThread(lat, lon, latsw, lonsw).execute("http://overpass-api.de/api/interpreter").get();
			this.getLatLong(x);
			cctvStarted = true;
			
			
			 
			        /**map.setOnCameraChangeListener(new OnCameraChangeListener() {
			            @Override
			            public void onCameraChange(CameraPosition position) {
			                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
			                LatLng l = bounds.northeast;
			                double lat = l.latitude;
			                double lon = l.longitude;
			                LatLng sw = bounds.southwest;
			                double latsw = sw.latitude;
			                double lonsw = sw.longitude;
			                Log.i("zoom", Double.toString(lat));
			                
			            }
			        });*/
		        
			
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
			for(int i=0; i<markerList.size(); i++) {
				Marker m = markerList.get(i);
				m.setVisible(false);
				cctvStarted = false;
				Color trans = new Color();
				
				imageView.setBackgroundColor(trans.TRANSPARENT);
				
			}
		}
	}
	private void cctvHelper() {
		try {
			LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
            LatLng l = bounds.northeast;
            double lat = l.latitude;
            double lon = l.longitude;
            LatLng sw = bounds.southwest;
            double latsw = sw.latitude;
            double lonsw = sw.longitude;
            String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><osm-script>"+
    				"<query type=\"node\">"+
    				"<has-kv k=\"man_made\" v=\"surveillance\"/>"+
    				"<bbox-query s=\""+latsw+"\" w=\""+lonsw+"\" n=\""+lat+"\" e=\""+lon+"\"/>"+
    				"</query>"+
    				"<print/></osm-script>";
            
			String x = new OverpassThread(xml).execute("http://overpass-api.de/api/interpreter").get();
            //String x = new OverpassThread(lat, lon, latsw, lonsw).execute("http://overpass-api.de/api/interpreter").get();
			this.getLatLong(x);
			cctvStarted = true;
		        
			
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshInfo(View v) {
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosMapPolice),einsatzID);
	}
	
	public void back(View v) {
		 finish();
				
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
	        .title("Überwachungskamera")
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

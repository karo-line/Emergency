package com.example.emergency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class StartFire extends Activity implements 
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
	boolean naviStarted = false;
	ArrayList<Marker> markerList;
	public static ArrayList<Marker> markerFireCar = new ArrayList<Marker>();
	private LocationClient mLocationClient;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	ImageView deleteMarker;
	LatLng origMarkerPos;
	String einsatzID;
	Polyline line = null;
	private final int FIVE_SECONDS = 120000;
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
		setContentView(R.layout.activity_fire);
		
		/**
		 * einsatzinfos aktualisieren
		 */
		
		Bundle b = getIntent().getExtras();
		if(b!= null) {
			einsatzID = getIntent().getExtras().getString("einsatzID");
			
			SharedPreferences settings = getSharedPreferences("shares",0);
		     SharedPreferences.Editor editor = settings.edit();
		     editor.putString("einsatzID", einsatzID);
		     editor.commit();
		     Log.i("einsatzIDvorhanden", einsatzID);
		} else {
			SharedPreferences settings = getPreferences(0);
			einsatzID = settings.getString("einsatzID", "nosuchvalue");
		}
		RefreshInfo refreshInfo = new RefreshInfo();
		refreshInfo.refresh(findViewById(R.id.einsatzinfosmenu), einsatzID);
		
		s = new scheduleEinsatz();
		s.scheduleUpdateInfo(findViewById(R.id.einsatzinfosmenu), einsatzID);
		
		
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		Context c = getApplicationContext();
		String windSchedule = s.scheduleWind(layoutInflater, findViewById(R.id.einsatzinfosmenu), c);
		
		
		Log.i("nochanged",windSchedule);
		if(!windSchedule.equals("nochange")) {
			Log.i("windhaschanged",windSchedule);
			
		} else{
			Log.i("nochanged",windSchedule);
		}
		
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
        

        ImageView windView = (ImageView) findViewById(R.id.windImg);
       
        
        WindFunction windFunction = new WindFunction();
        JSONObject json = windFunction.getWind("http://api.openweathermap.org/data/2.5/weather?",48.202668, 16.344140);
        String wind="";
        if(json==null) {
        	Log.i("json", "null");
        }
        
        try {
			JSONObject json_wind = json.getJSONObject("wind");
			wind = json_wind.getString("deg");
			
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
			s.setWind(windDir);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        windView.setRotation(Float.parseFloat(wind));
        //windView.setImageDrawable(imgWind);
    
        for(int i=0;i<markerFireCar.size();i++) {
        	Marker m = markerFireCar.get(i);
        	
        	Drawable dr = getResources().getDrawable(R.drawable.big_car);
		    
	        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
	        // Scale it to 50 x 50
	        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 30, true));
	        bitmap = ((BitmapDrawable) d).getBitmap();
	        MarkerOptions markerOptions = new MarkerOptions()
	        .position(m.getPosition())
	        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
	        .rotation(m.getRotation())
	        .flat(true);		
	        Marker marker = map.addMarker(markerOptions);
	        marker.setDraggable(true);
        }
        Log.i("lengtharray", String.valueOf(markerFireCar.size()));
        markerList = new ArrayList<Marker>();
        //markerFireCar = new ArrayList<Marker>();
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
        
        
       // findViewById(R.id.big_car).setOnTouchListener(new MyTouchListener());
       // findViewById(R.id.map).setOnDragListener(new MyDragListener());
        findViewById(R.id.big_car).setOnClickListener(new MyClickListener());
        
       /** Marker marker = map.addMarker(new MarkerOptions()
        .position(new LatLng(37.7750, 122.4183))
        .title("San Francisco")
        .snippet("Population: 776733"))
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.big_car));*/
      /**  Drawable dr = getResources().getDrawable(R.drawable.big_car);
       
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 30, true));
        bitmap = ((BitmapDrawable) d).getBitmap();
        MarkerOptions markerOptions = new MarkerOptions()
        .position(new LatLng(48.2071843, 16.3587499))
        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        .rotation(90)
        .flat(true);		
        Marker marker = map.addMarker(markerOptions);
        marker.setDraggable(true);*/
        map.setOnMarkerClickListener(new MyMarkerClickListener());
        map.setOnMarkerDragListener(new MyMarkerDragListener());
        
        deleteMarker = (ImageView) findViewById(R.id.waste);
        deleteMarker.setVisibility(View.INVISIBLE);
	}
	


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}

private final class MyMarkerDragListener implements OnMarkerDragListener {
    public void onMarkerDragStart(Marker marker) {
        deleteMarker.setVisibility(View.VISIBLE);
        origMarkerPos = marker.getPosition();
        //vibrator.vibrate(100);
    }


	public void onMarkerDrag(Marker marker) {
	    
	    Point markerScreenPosition = map.getProjection().toScreenLocation(marker.getPosition());
	    if (overlap(markerScreenPosition, deleteMarker)) {
	        deleteMarker.setImageResource(R.drawable.ic_action_discard2);
	    } else {
	        deleteMarker.setImageResource(R.drawable.ic_action_discard);
	    }
	}
	
	
	public void onMarkerDragEnd(Marker marker) {
	    
	    deleteMarker.setVisibility(View.GONE);
	    Point markerScreenPosition = map.getProjection().toScreenLocation(marker.getPosition());
	    if (overlap(markerScreenPosition, deleteMarker)) {
	        markerFireCar.remove(marker);
	    	marker.remove();
	    } else {}
	       // marker.setPosition(origMarkerPos);
	}
	
	private boolean overlap(Point point, ImageView imgview) {
	    int[] imgCoords = new int[2];
	    imgview.getLocationOnScreen(imgCoords);
	    Log.i("locations", " ****** Img x:" + imgCoords[0] + " y:" + imgCoords[1] + "    Point x:" + point.x + "  y:" + point.y + " Width:" + imgview.getWidth() + " Height:" + imgview.getHeight());
	    boolean overlapX = point.x < imgCoords[0] + imgview.getWidth() && point.x > imgCoords[0] - imgview.getWidth();
	    boolean overlapY = point.y < imgCoords[1] + imgview.getHeight() && point.y > imgCoords[1] - imgview.getWidth();
	    return overlapX && overlapY;
	}
}
	
	private final class MyMarkerClickListener implements OnMarkerClickListener {
		  public boolean onMarkerClick(Marker m) {
			if(markerFireCar.contains(m)) {
			  float rotation = m.getRotation();
			  if(rotation<360) {
				  m.setRotation(rotation+90);
			  } else {
				  m.setRotation(90);
			  }
		       return true;
			} else {
				return false;
			}
		  }
		}
	
	private final class MyClickListener implements OnClickListener {
		  public void onClick(View view) {
		   /** if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		      ClipData data = ClipData.newPlainText("", "");
		      DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		      view.startDrag(data, shadowBuilder, view, 0);
		      view.setVisibility(View.INVISIBLE);
		      return true;
		    } else {
		    return false;
		    }*/
			  
			  LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
			  LatLng latlngCenter = bounds.getCenter();
			  Drawable dr = getResources().getDrawable(R.drawable.big_car);
		       
		        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
		        // Scale it to 50 x 50
		        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 30, true));
		        bitmap = ((BitmapDrawable) d).getBitmap();
		        MarkerOptions markerOptions = new MarkerOptions()
		        .position(latlngCenter)
		        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
		        .flat(true);		
		        Marker marker = map.addMarker(markerOptions);
		        marker.setDraggable(true);
		        markerFireCar.add(marker);
		        //in Datenbank einfügen
		        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		        db.addUser(marker.getPosition().latitude, marker.getPosition().longitude,markerFireCar.size());
		  }
		}
	private final class MyTouchListener implements OnTouchListener {
		  public boolean onTouch(View view, MotionEvent motionEvent) {
		   /** if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		      ClipData data = ClipData.newPlainText("", "");
		      DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
		      view.startDrag(data, shadowBuilder, view, 0);
		      view.setVisibility(View.INVISIBLE);
		      return true;
		    } else {
		    return false;
		    }*/
			  Drawable dr = getResources().getDrawable(R.drawable.big_car);
		       
		        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
		        // Scale it to 50 x 50
		        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 30, true));
		        bitmap = ((BitmapDrawable) d).getBitmap();
		        MarkerOptions markerOptions = new MarkerOptions()
		        .position(new LatLng(48.2071843, 16.3587499))
		        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
		        .rotation(90)
		        .flat(true);		
		        Marker marker = map.addMarker(markerOptions);
		        marker.setDraggable(true);
		        return true;
		  }
		}
	
	class MyDragListener implements OnDragListener {
		  //Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		  //Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		  
		  @Override
		  public boolean onDrag(View v, DragEvent event) {
		    int action = event.getAction();
		    switch (event.getAction()) {
		    case DragEvent.ACTION_DRAG_STARTED:
		    // do nothing
		      break;
		    case DragEvent.ACTION_DRAG_ENTERED:
		      //v.setBackgroundDrawable(enterShape);
		      break;
		    case DragEvent.ACTION_DRAG_EXITED:        
		      //v.setBackgroundDrawable(normalShape);
		      break;
		    case DragEvent.ACTION_DROP:
		      // Dropped, reassign View to ViewGroup
		      View view = (View) event.getLocalState();
		      view.setX(event.getX());
              view.setY(event.getY());
		      ViewGroup owner = (ViewGroup) view.getParent();
		      owner.removeView(view);
		      FrameLayout container = (FrameLayout) v;
		      container.addView(view);
		      view.setVisibility(View.VISIBLE);
		      break;
		    case DragEvent.ACTION_DRAG_ENDED:
		     // v.setBackgroundDrawable(normalShape);
		      default:
		      break;
		    }
		    return true;
		  }
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
	}
	 * @throws ExecutionException 
	 * @throws InterruptedException */
	
	public void startMenu(View v)  {
		i= new Intent(this, MenuFire.class);
		ImageView menu= (ImageView) findViewById(R.id.dummy_button);
		SharedPreferences settings = getSharedPreferences("shares",0);
		String einsatzID2 = settings.getString("einsatzID", "nosuchvalue");
		Log.i("einsatz",einsatzID2);
		startActivity(i);		
		overridePendingTransition(R.layout.fadeout, R.layout.fadein);
		
	}
	
	public void startNavi(View v) throws InterruptedException, ExecutionException {
		Location myLocation = map.getMyLocation();
        String tag[] = { "lat", "lng" };
        EditText ziel = (EditText) findViewById(R.id.naviGoal);
        String zielAdr = ziel.getText().toString();
		ArrayList all_geo_points = getDirections(myLocation.getLatitude(), myLocation.getLongitude(),zielAdr);
		int size = all_geo_points.size();
		List<LatLng> list = (List<LatLng>) all_geo_points.get(size-1);
		PolylineOptions options = new PolylineOptions();
        options.addAll(list);
        
		if (naviStarted==false) {
	        //options.fillColor(Color.RED);
	        line = map.addPolyline(options);
	        naviStarted = true;
	        Log.i("navifalse","if");
	        
		} else {
			line.remove();
			naviStarted = false;
		}
	}
	
	
	public void startVideo(View v) {
		i= new Intent(this, VideoFire.class);
		ImageView play= (ImageView) findViewById(R.id.play);
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
    				"<has-kv k=\"emergency\" v=\"fire_hydrant\"/>"+
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
    				"<has-kv k=\"emergency\" v=\"fire_hydrant\"/>"+
    				"<bbox-query s=\""+latsw+"\" w=\""+lonsw+"\" n=\""+lat+"\" e=\""+lon+"\"/>"+
    				"</query>"+
    				"<print/></osm-script>";
            
			String x = new OverpassThread(xml).execute("http://overpass-api.de/api/interpreter").get();
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
		refreshInfo.refresh(this.findViewById(R.id.einsatzinfosmenu),einsatzID);
	}
	
	public void startTodo(View v) {
		i= new Intent(this, ToDoFire.class);
		startActivity(i);		
				
	}
	
	public void startTicker(View v) {
		/**i= new Intent(this, TickerFire.class);
		startActivity(i);	*/	
		
		LayoutInflater layoutInflater  = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    View popupView = layoutInflater.inflate(R.layout.popup, null);  
	             final PopupWindow popupWindow = new PopupWindow(
	               popupView, 
	               LayoutParams.WRAP_CONTENT,  
	                     LayoutParams.WRAP_CONTENT); 
	             TextView tView = (TextView)popupView.findViewById(R.id.popupText);
	             tView.setText("Windänderung!");
	             popupWindow.showAsDropDown(findViewById(R.id.map), 10, -130);
	             
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
			    	 i= new Intent(getApplicationContext(), WindFire.class);
			 		startActivity(i);
			     }});
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
			Marker marker = map.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lon))
	        .title("Hydrant")
	        .snippet(sub+" "+sub2));
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
        Bundle b = getIntent().getExtras();
        if(b!= null) {
			einsatzID = getIntent().getExtras().getString("einsatzID");
			
		     SharedPreferences settings = getPreferences(0);
		     SharedPreferences.Editor editor = settings.edit();
		     editor.putString("einsatzID", einsatzID);
		     editor.commit();
		     Log.i("einsatzIDvorhanden", einsatzID);
		} else {
			SharedPreferences settings = getPreferences(0);
			einsatzID = settings.getString("einsatzID", "nosuchvalue");
		}
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
    
    public static ArrayList getDirections(double lat1, double lon1, String ziel) throws InterruptedException, ExecutionException {
        String url = "http://maps.googleapis.com/maps/api/directions/xml?origin=" +lat1 + "," + lon1  + "&destination=" + ziel + "&sensor=false&units=metric";
        String tag[] = { "lat", "lng" };
        ArrayList list_of_geopoints = new ArrayList();
        
        	NaviFunction function= new NaviFunction();
            Document doc = function.getDocument(url);
            		
            if (doc != null) {
                NodeList nl1, nl2;
                nl1 = doc.getElementsByTagName("points");
                
                nl2 = doc.getElementsByTagName(tag[1]);
                if (nl1.getLength() > 0) {
                    list_of_geopoints = new ArrayList();
                    for (int i = 0; i < nl1.getLength(); i++) {
                        Node node1 = nl1.item(i);
                        
                        //Node node2 = nl2.item(i);
                        Log.i("points",node1.getTextContent());
                        List<LatLng> poly = decodePoly(node1.getTextContent());
                        list_of_geopoints.add(poly);
                        /**double lat = Double.parseDouble(node1.getTextContent());
                        double lng = Double.parseDouble(node2.getTextContent());
                        list_of_geopoints.add(new LatLng(lat,lng));*/
                        //list_of_geopoints.add(new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)));
                    }
                } else {
                    // No points found
                }
            }
       
        return list_of_geopoints;
    }
    
    private static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }
    

	


    public static void showAlert(Context act,String msg)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener(){
            


			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}

        }).show();
    }
    
  
}


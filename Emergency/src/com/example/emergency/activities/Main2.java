package com.example.emergency.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import unused.OSMTileProvider;

import com.example.emergency.R;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.example.emergency.functions.OverpassThread;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


@SuppressLint("NewApi")
public class Main2 extends Activity {

	GoogleMap map;
	double lat;
    double lon;
    double latsw;
    double lonsw;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        FragmentManager fm = getFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.map);
        
        map = ((com.google.android.gms.maps.MapFragment) frag).getMap();
        LatLng latLng = new LatLng(48.2071843, 16.3587499);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
     // Create new TileOverlayOptions instance.
        TileProvider osmTileProvider = new OSMTileProvider(256,256);
        TileOverlayOptions opts = new TileOverlayOptions().tileProvider(osmTileProvider).zIndex(5);
        lat = 5;
        lon = 8;
        
        if(map!=null) {
	        map.setOnCameraChangeListener(new OnCameraChangeListener() {
	            @Override
	            public void onCameraChange(CameraPosition position) {
	                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
	                LatLng l = bounds.northeast;
	                lat = l.latitude;
	                lon = l.longitude;
	                LatLng sw = bounds.southwest;
	                latsw = sw.latitude;
	                lonsw = sw.longitude;
	                Log.i("zoom", "zoom");
	                
	            }
	        });
        }
     // Add the tile overlay to the map.
       // TileOverlay overlay = map.addTileOverlay(opts);
        Marker marker = map.addMarker(new MarkerOptions()
        .position(new LatLng(48.2071843, 16.3587499))
        .title("Surveillance")
        .snippet("Tiefgarage Schmerlingplatz"));
        marker.setVisible(true);
        try {
			String x = new OverpassThread("").execute("http://overpass-api.de/api/interpreter").get();
			Log.i("node", x);
			
			Log.i("latn", Double.toString(latsw));
			Log.i("lngn", Double.toString(lonsw));
			this.getLatLong(x);
			/**String[] nodes = x.split("<node");
			Log.i("nodes", nodes[1]);
			Log.i("nodes2", nodes[2]);
			int index=x.indexOf("lat");
			String sub=x.substring(index+5, index+15);
			double lat = Double.parseDouble(sub);
			index=x.indexOf("lon");
			sub=x.substring(index+5, index+15);
			double lon = Double.parseDouble(sub);
			Marker marker2 = map.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lon))
	        .title("Overpass")
	        .snippet("Tiefgarage Schmerlingplatz"));
	        marker.setVisible(true);
			Log.i("sub", sub);*/
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    		if (index!=-1) {
			sub=node.substring(index+5, index+15);
			lat = Double.parseDouble(sub);
    		}
			index=node.indexOf("lon");
			if (index!=-1) {
			sub=node.substring(index+5, index+15);
			lon = Double.parseDouble(sub);
			
			
			
			Marker marker = map.addMarker(new MarkerOptions()
	        .position(new LatLng(lat, lon))
	        .title("Overpass")
	        .snippet("Tiefgarage Schmerlingplatz"));
	        marker.setVisible(true);
			}
    	}
    }
    
   /** public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://overpass-api.de/api/interpreter");

        try {
        	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><osm-script>"+
        				"<query type=\"node\">"+
        				"<has-kv k=\"amenity\" v=\"drinking_water\"/>"+
        				"<bbox-query s=\"41.88659196260802\" w=\"12.488558292388916\" n=\"41.89248629819397\" e=\"12.51119613647461\"/>"+
        				"</query>"+
        				"<print/></osm-script>";
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", xml));
            
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    } */
    
  
 // Set the tile provider to your custom implementation.
 //opts.tileProvider(new OSMTileProvider);
  
 // Optional. Useful if you have multiple, layered tile providers.
// opts.zIndex(5);
  
 
}


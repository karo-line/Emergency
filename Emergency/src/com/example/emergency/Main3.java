package com.example.emergency;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Main3 extends Activity {
 
 private MapView mMapView;
 private MapController myMapController;
 ResourceProxy mResourceProxy;
 
    /** Called when the activity is first created. */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mResourceProxy = new DefaultResourceProxyImpl(inflater.getContext().getApplicationContext());
        MapView mMapView = new MapView(inflater.getContext(), 256, mResourceProxy);
        return mMapView;
    }

}

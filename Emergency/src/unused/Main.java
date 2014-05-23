package unused;

import com.example.emergency.R;
import com.example.emergency.R.id;
import com.example.emergency.R.layout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

@SuppressLint("NewApi")
public class Main extends Activity {

    
    protected GoogleMap mMap;
    protected View mView;
    protected RadioGroup rgMapOptionView;
    TileOverlayOptions mCustomTileOverlayOptions;
	TileOverlay mCustomTileOverlay;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // hack!!!
        if ((mView != null) && (mView.getParent() != null)) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        if (mView == null) {
            mView = inflater.inflate(R.layout.main, container, false);
        }
 
        // das ist dann wieder ok :-)
        setUpMapIfNeeded();
        return mView;
    }
 
    @SuppressLint("NewApi")
	protected void setUpMapIfNeeded() {
       
        FragmentManager fm = getFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.map);
       
        if (frag == null) {
            if ((mView != null) && (mView.getParent() != null)) {
                ((ViewGroup) mView.getParent()).removeView(mView);
                mView = null;
            }
            return;
        } else {
            if (mMap == null) {
                mMap = ((com.google.android.gms.maps.MapFragment) frag).getMap();
            }
        }
       
        if (mMap != null) {
           
            mMap.setMyLocationEnabled(true);
           
            // die listener musst du jetzt bei Bedarf selbst machen
            // mMap.setOnCameraChangeListener(mOnCameraChangeListener);
            // mMap.setOnMapClickListener(mOnMapClickListener);
            // mMap.setOnMarkerClickListener(mOnMarkerClickListener);
            // mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(getActivity()));
            // mMap.setOnInfoWindowClickListener(mOnInfoWindowClickListener);
           
            // ich erstelle meine Marker für die Map in einem AsyncTask,damit das UI nicht blockiert
            // if ((mInitTask != null) && !mInitTask.isCancelled()) {
            //  mInitTask.cancel(true);
            // }
            // mInitTask = new MarkerBuilderTask();
            // mInitTask.execute();
        }
    
        
    /**if (rgMapOptionView != null) {
        int type = PreferenceHelper.getMapOptionType(getActivity());
        
		
		if (mCustomTileOverlay != null) {
            mCustomTileOverlayOptions = null;
            mCustomTileOverlay.remove();
            mCustomTileOverlay = null;
        }
        
		if (mMap != null) {
            switch (type) {

            // Google-Types
            case GoogleMap.MAP_TYPE_NORMAL:
                mMap.setMapType(type);
                rgMapOptionView.check(R.id.map_option_view_def);
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                mMap.setMapType(type);
                rgMapOptionView.check(R.id.map_option_view_sat);
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                mMap.setMapType(type);
                rgMapOptionView.check(R.id.map_option_view_top);
                break;

            // Custom - alsoo von mir
            case CustomMapType.MAP_TYPE_OSM:
                // hier kann auch ein weniger bescheuertes Konstrukt verwendent werden! :-D
                TileProvider osmTileProvider = CustomMapType.CustomTileProviderEnum.OSM.createTileProvider(getActivity());
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                mCustomTileOverlayOptions = new TileOverlayOptions().tileProvider(osmTileProvider).zIndex(0);
                mCustomTileOverlay = mMap.addTileOverlay(mCustomTileOverlayOptions);
                mCustomTileOverlay.clearTileCache();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(46.954012, 8.415527)));
                rgMapOptionView.check(R.id.map_option_view_osm);
                break;

            // Fallback
            default:
                type = GoogleMap.MAP_TYPE_NORMAL;
                mMap.setMapType(type);
                rgMapOptionView.check(R.id.map_option_view_def);
                break;
            }
        }
        rgMapOptionView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mMap == null)
                    return;
                if (mCustomTileOverlay != null) {
                    mCustomTileOverlayOptions = null;
                    mCustomTileOverlay.remove();
                    mCustomTileOverlay = null;
                }
                switch (checkedId) {
                case R.id.map_option_view_def:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    PreferenceHelper.setMapOptionType(getActivity(), GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.map_option_view_sat:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    PreferenceHelper.setMapOptionType(getActivity(), GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case R.id.map_option_view_top:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    PreferenceHelper.setMapOptionType(getActivity(), GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                case R.id.map_option_view_osm:
                    TileProvider osmTileProvider = CustomMapType.CustomTileProviderEnum.OSM.createTileProvider(getActivity());
                    if (osmTileProvider != null) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        mCustomTileOverlayOptions = new TileOverlayOptions().tileProvider(osmTileProvider).zIndex(0);
                        mCustomTileOverlay = mMap.addTileOverlay(mCustomTileOverlayOptions);
                        PreferenceHelper.setMapOptionType(getActivity(), CustomMapType.MAP_TYPE_OSM);
                        break;
                    }
                    // otherwise we use the fall-through and create the default map
                default:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    PreferenceHelper.setMapOptionType(getActivity(), GoogleMap.MAP_TYPE_NORMAL);
                    break;
                }
            }
        });
    }*/
    }
}


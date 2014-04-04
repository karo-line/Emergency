package com.example.emergency;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.UrlTileProvider;

public class OSMTileProvider extends UrlTileProvider {
    
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
   
    //private final Context mContext;
    private final String mBaseUrlFormat;
   
    public OSMTileProvider(Integer width, Integer height) {
        super(width, height);
        //this.mContext = context;
        this.mBaseUrlFormat = "http://tile.openstreetmap.org/%d/%d/%d.png";
        //this.mBaseUrlFormat = mContext.getString(R.string.mapFragment_tileProvider_osm);
    }
   
   // public OSMTileProvider(final Context context) {
    //    this(context, TILE_WIDTH, TILE_HEIGHT);
    //}
   
    @Override
    public URL getTileUrl(int x, int y, int zoom) {
        try {
            return new URL(String.format(mBaseUrlFormat, zoom, x, y));
        } catch (MalformedURLException e) {
            Log.e(OSMTileProvider.class.getSimpleName() + "#getTileUrl", e.getMessage());
            return null;
        }
    }
}

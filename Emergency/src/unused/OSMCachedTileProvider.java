package unused;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

public class OSMCachedTileProvider {
//implements TileProvider {
    
   /** private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;
   
    private final Context mContext;
   
    private final int mTileWidth;
    private final int mTileHeight;
   
    private final OSMTileProvider mOnlineProvider;
   
    private boolean useCache = true;
   
    public OSMCachedTileProvider(final Context context, Integer width, Integer height) {
        this.mContext = context;
        this.mTileWidth = width;
        this.mTileHeight = height;
        this.mOnlineProvider = new OSMTileProvider(mContext, mTileWidth, mTileHeight);
    }
   
    public OSMCachedTileProvider(final Context context) {
        this(context, TILE_WIDTH, TILE_HEIGHT);
    }
   
    public OSMCachedTileProvider useCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }
   
    /* ----------------------------------------------------------------------------------------- */
    /* TileProvider implementation */
    /* ----------------------------------------------------------------------------------------- */
   
   /** @Override
    public Tile getTile(int x, int y, int zoom) {
       
        Tile tile = null;
       
        if (useCache) {
            // read from cache
            tile = getCachedTile(x, y, zoom);
            if (tile != null)
                return tile;
        }
       
        // otherwise read from online provider
        tile = mOnlineProvider.getTile(x, y, zoom);
       
        // check, if the tile is still not initialized
        if (tile == null || NO_TILE.equals(tile)) {
            tile = getAssetNoTile();
        } else {
            if (useCache) {
                cacheTileData(x, y, zoom, tile);
            }
        }
        return tile;
    }
   
    /* ----------------------------------------------------------------------------------------- */
    /* private helper methods for the implementation */
    /* ----------------------------------------------------------------------------------------- */
   
 /**   private void cacheTileData(int x, int y, int zoom, Tile tile) {
       
        File internalCacheDir = SpeedTestDBAccess.getInstance(mContext).getInternalCacheDir();
        if (internalCacheDir == null)
            return;
        File tileFile = new File(internalCacheDir, mContext.getString(R.string.mapFragment_tileProvider_cache_format, CustomMapType.MAP_TYPE_OSM, x, y,
                zoom, tile.width, tile.height));
        File osmTileDir = tileFile.getParentFile();
       
        if (!osmTileDir.isDirectory())
            if (!osmTileDir.mkdirs())
                return;
       
        // if there is a file already, remove it
        if (tileFile.isFile())
            if (!tileFile.delete())
                return;
       
        OutputStream out = null;
        try {
            out = new FileOutputStream(tileFile);
            out.write(tile.data);
            out.flush();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName() + "#storeTileData", "Can't store tile data to file " + tileFile.getName() + ": " + e.getMessage(), e);
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (Exception ignored) {
                }
        }
    }
   
    private Tile getCachedTile(int x, int y, int zoom) {
       
        File internalCacheDir = SpeedTestDBAccess.getInstance(mContext).getInternalCacheDir();
        if (internalCacheDir == null)
            return null;
        File tileFile = new File(internalCacheDir, mContext.getString(R.string.mapFragment_tileProvider_cache_format, CustomMapType.MAP_TYPE_OSM, x, y,
                zoom, TILE_WIDTH, TILE_HEIGHT));
        File osmTileDir = tileFile.getParentFile();
       
        if (!osmTileDir.isDirectory())
            return null;
       
        if (!tileFile.isFile()) {
            return null;
        } else {
            long now = System.currentTimeMillis();
            long then = tileFile.lastModified();
            long diff = now - then;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(diff);
            // if the file is older then a week, tell the #getTile() method, that it need to be realoaded
            if (c.get(Calendar.DAY_OF_MONTH) > 7)
                return null;
        }
       
        InputStream in = null;
        try {
            in = new FileInputStream(tileFile);
            return new Tile(TILE_WIDTH, TILE_HEIGHT, readTileDataFromInputStream(in));
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName() + "#getCachedTile", "Can't read tile data: " + e.getMessage(), e);
            return null;
        }
    }
   
    private Tile getAssetNoTile() {
       
        InputStream in = null;
        try {
            in = mContext.getAssets().open(mContext.getString(R.string.mapFragment_tileProvider_NO_TILE));
            return new Tile(TILE_WIDTH, TILE_HEIGHT, readTileDataFromInputStream(in));
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName() + "#getAssetNoTile", "Can't read tile data: " + e.getMessage(), e);
            return null;
        }
    }
   
    private byte[] readTileDataFromInputStream(InputStream in) {
        if (in == null)
            return null;
        ByteArrayOutputStream buffer = null;
        try {
            buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[BUFFER_SIZE];
            while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName() + "#readTileDataFromInputStream", "Can't read tile data: " + e.getMessage(), e);
            return null;
        } catch (OutOfMemoryError e) {
            Log.e(this.getClass().getSimpleName() + "#readTileDataFromInputStream", "Can't read tile data: " + e.getMessage(), e);
            return null;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            if (buffer != null)
                try {
                    buffer.close();
                } catch (Exception ignored) {
                }
        }
    }*/
}

package unused;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.TileProvider;

public class CustomMapType {
    
    public static final int MAP_TYPE_OSM = 101;
   
    public enum CustomTileProviderEnum {
       
       /** OSM(OSMCachedTileProvider.class, 18);
       
        private final Class<? extends TileProvider> customTileProviderClass;
        private final int maxZoomLevel;
       
        private CustomTileProviderEnum(final Class<? extends TileProvider> customTileProviderClass, final int maxZoomLevel) {
            this.customTileProviderClass = customTileProviderClass;
            this.maxZoomLevel = maxZoomLevel;
        }
       
        public int getMaxZoomLevel() {
            return maxZoomLevel;
        }
       
        public TileProvider createTileProvider(final Context context) {
            try {
                Constructor<? extends TileProvider> constructor = customTileProviderClass.getConstructor(Context.class);
                TileProvider tileProvider = constructor.newInstance(context);
                return tileProvider;
            } catch (NoSuchMethodException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (InstantiationException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (InvocationTargetException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            }
            return null;
        }
       
        public TileProvider createTileProvider(final Context context, Integer width, Integer height) {
            try {
                Constructor<? extends TileProvider> constructor = customTileProviderClass.getConstructor(Context.class, Integer.class, Integer.class);
                TileProvider tileProvider = constructor.newInstance(context, width, height);
                return tileProvider;
            } catch (NoSuchMethodException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (InstantiationException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            } catch (InvocationTargetException e) {
                Log.e(CustomTileProviderEnum.class.getSimpleName() + "#createTileProvider", e.getMessage(), e);
            }
            return null;
        }*/
    }
}

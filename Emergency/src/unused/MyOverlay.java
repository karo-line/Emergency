package unused;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class MyOverlay extends Overlay {

private ArrayList all_geo_points;

public MyOverlay(ArrayList allGeoPoints) {
    super();
    this.all_geo_points = allGeoPoints;
}


public boolean draw(Canvas canvas, MapView mv, boolean shadow, long when) {
    super.draw(canvas, mv, shadow);
    drawPath(mv, canvas);
    return true;
}

public void drawPath(MapView mv, Canvas canvas) {
    int xPrev = -1, yPrev = -1, xNow = -1, yNow = -1;
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setStrokeWidth(4);
paint.setAlpha(100);
    if (all_geo_points != null)
        for (int i = 0; i < all_geo_points.size() - 4; i++) {
            GeoPoint gp = (GeoPoint) all_geo_points.get(i);
            Point point = new Point();
            mv.getProjection().toPixels(gp, point);
            xNow = point.x;
            yNow = point.y;
            if (xPrev != -1) {
                canvas.drawLine(xPrev, yPrev, xNow, yNow, paint);
            }
            xPrev = xNow;
            yPrev = yNow;
        }
    }




}

package kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.target.Target;

/**
 * Specifies a shape of the target (e.g circle, rectangle).
 * Implementations of this interface will be responsible to draw the shape
 * at specified center point (x, y).
 */
public interface Shape {

    /**
     * Draw shape on the canvas with the center at (x, y) using Paint object provided.
     */
    void draw(Canvas canvas, Paint paint, int x, int y);

    /**
     * Get width of the shape.
     */
    int getWidth();

    /**
     * Get height of the shape.
     */
    int getHeight();

    /**
     * Update shape bounds if necessary
     */
    void updateTarget(Target target);

    int getTotalRadius();

    void setPadding(int padding);
}

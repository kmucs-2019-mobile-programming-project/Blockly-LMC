package kmucs.mobileprogramming.team.a.blocklylmc.materialshowcaseview.target;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class PointRadiusTarget implements Target {
    private final Point mPoint;
    private final int mRadius;

    public PointRadiusTarget(Point point, int radius) {
        mPoint = point;
        mRadius = radius;
    }

    @Override
    public Point getPoint() {
        return mPoint;
    }

    @Override
    public Rect getBounds() {
        return new Rect(
                mPoint.x,
                mPoint.y,
                mPoint.x + 2 * mRadius,
                mPoint.y + 2 * mRadius
        );
    }
}

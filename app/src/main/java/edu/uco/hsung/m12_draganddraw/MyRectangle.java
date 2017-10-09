package edu.uco.hsung.m12_draganddraw;

import android.graphics.Paint;
import android.graphics.PointF;

public class MyRectangle {

    private PointF origin;
    private PointF current;
    private Paint paint;

    public MyRectangle(PointF origin) {
        this.origin = origin;
        current = origin;
    }

    public PointF getCurrent() {
        return current;
    }

    public void setCurrent(PointF current) {
        this.current = current;
    }

    public PointF getOrigin() {
        return origin;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}

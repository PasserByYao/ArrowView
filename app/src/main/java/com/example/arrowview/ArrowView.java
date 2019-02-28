package com.example.arrowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

public class ArrowView extends AppCompatTextView {

    private static final String TAG = "ArrowView";

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ArrowShape roundRectShape;
    private ShapeDrawable shapeDrawable;

    public ArrowView(Context context) {
        super(context,null);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: " );
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e(TAG, "dispatchDraw: " );
        if (roundRectShape == null) {
            float[]outradius = new float[]{8,8,8,8,8,8,8,8};
            RectF rectF = new RectF();
            float[]inradius = new float[]{6,6,6,6,6,6,6,6};
            roundRectShape = new ArrowShape(outradius, rectF, inradius,20);
            paint.setColor(Color.LTGRAY);
            paint.setAlpha(7);
            shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.setColorFilter(new ColorFilter());
            setBackgroundDrawable(shapeDrawable);
        }
        roundRectShape.draw(canvas,paint);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        setBackgroundDrawable(shapeDrawable);
        super.setText(text, type);
    }
}

package com.example.arrowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

public class ArrowView extends AppCompatTextView {

    private static final String TAG = "ArrowView";

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ArrowShape roundRectShape;
    private ShapeDrawable shapeDrawable;

    float[]outRadius = new float[]{8,8,8,8,8,8,8,8};
    RectF rectF = new RectF();
    float[]inRadius = new float[]{6,6,6,6,6,6,6,6};

    //X轴偏移量
    int offsetX = 0;
    //屏幕宽度像素
    private int widthPixels;

    //文字颜色
    int textColor = Color.WHITE;

    //背景颜色
    int backgroundColor = Color.LTGRAY;

    //背景透明度
    float backgroundAlpha = 0.5f;

    //箭头宽高
    int arrowRadius = 8;

    public ArrowView(Context context) {
        super(context,null);
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (roundRectShape == null) {
            roundRectShape = new ArrowShape(outRadius, rectF, inRadius, Utils.dp2px(getContext(),arrowRadius),offsetX, Utils.dp2px(getContext(),10));
            paint.setColor(textColor);
            roundRectShape.setBackgroundColor(backgroundColor);
            roundRectShape.draw(canvas,paint);
            paint.setAlpha(7);
            shapeDrawable = new ShapeDrawable(roundRectShape);
            setBackgroundDrawable(shapeDrawable);
            setAlpha(backgroundAlpha);
        }
    }

    /**
     * 重新绘制背景
     * @param anchor
     */
    public void drawBackground(View anchor) {
        //本控件宽度的一半
        int hw = getMeasuredWidth() / 2;

        int[]l = new int[2];

        anchor.getLocationOnScreen(l);

        int viewLeft = l[0]+anchor.getWidth() / 2;

        int viewRight = widthPixels - viewLeft;

        //控件以x轴中间为起始点，offsetX为偏移量
        offsetX = 0;

        roundRectShape = null;

        //当左边距小于控件的一半的时候，箭头左移
        if (viewLeft <= hw) {
            offsetX = hw - viewLeft;
        }

        //右边距小于控件一半的时候，箭头右移
        if (viewRight <= hw) {
            offsetX = viewRight - hw;
        }

        invalidate();
    }

    /**
     * 设置文本颜色和背景颜色
     * @param textColor
     * @param backgroundColor
     */
    public void setColor(@ColorInt int textColor,@ColorInt int backgroundColor) {
        this.textColor = textColor;
        setTextColor(textColor);
        this.backgroundColor = backgroundColor;
    }

    /**
     * 设置背景透明度
     * @param alpha
     */
    public void setBackgroundAlpha(float alpha) {
        this.backgroundAlpha = alpha;
    }

    /**
     * 设置箭头角弧度
     * @param radius
     */
    public void setCornerRadius(int radius) {

        if (radius < 0) {
            radius = 0;
        }

        if (radius > (getMeasuredHeight() - arrowRadius) / 2) {
            radius = (getMeasuredHeight() - arrowRadius) / 2;
        }

        int r = Utils.dp2px(getContext(), radius);
        outRadius = new float[]{r,r,r,r,r,r,r,r};
        int i = Utils.dp2px(getContext(), radius - 1);
        inRadius = new float[]{i,i,i,i,i,i,i,i};
    }
}

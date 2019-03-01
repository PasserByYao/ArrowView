package com.example.arrowview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;

/**
 * @author Administrator
 */
public class ArrowShape extends RectShape {
    private float[] mOuterRadii;
    private RectF mInset;
    private float[] mInnerRadii;

    private RectF mInnerRect;

    private Path mPath;

    private int mArrowRadius;

    private int bgColor = Color.LTGRAY;

    private int offsetX;

    private int paddingBottom;

    /**
     * RoundRectShape constructor.
     * <p>
     * Specifies an outer (round)rect and an optional inner (round)rect.
     *
     * @param outerRadii An array of 8 radius values, for the outer roundrect.
     *                   The first two floats are for the top-left corner
     *                   (remaining pairs correspond clockwise). For no rounded
     *                   corners on the outer rectangle, pass {@code null}.
     * @param inset A RectF that specifies the distance from the inner
     *              rect to each side of the outer rect. For no inner, pass
     *              {@code null}.
     * @param innerRadii An array of 8 radius values, for the inner roundrect.
     *                   The first two floats are for the top-left corner
     *                   (remaining pairs correspond clockwise). For no rounded
     *                   corners on the inner rectangle, pass {@code null}. If
     *                   inset parameter is {@code null}, this parameter is
     *                   ignored.
     */
    public ArrowShape(@Nullable float[] outerRadii, @Nullable RectF inset,
                          @Nullable float[] innerRadii,int arrowRadius,int offsetX,int paddingBottom) {
        if (outerRadii != null && outerRadii.length < 8) {
            throw new ArrayIndexOutOfBoundsException("outer radii must have >= 8 values");
        }
        if (innerRadii != null && innerRadii.length < 8) {
            throw new ArrayIndexOutOfBoundsException("inner radii must have >= 8 values");
        }
        this.offsetX = offsetX;
        this.paddingBottom = paddingBottom;
        mOuterRadii = outerRadii;
        mInset = inset;
        mInnerRadii = innerRadii;
        mArrowRadius = arrowRadius;
        if (inset != null) {
            mInnerRect = new RectF();
        }
        mPath = new Path();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(bgColor);
        canvas.drawPath(mPath, paint);
    }

    @Override
    public void getOutline(Outline outline) {
        if (mInnerRect != null) return; // have a hole, can't produce valid outline

        float radius = 0;
        if (mOuterRadii != null) {
            radius = mOuterRadii[0];
            for (int i = 1; i < 8; i++) {
                if (mOuterRadii[i] != radius) {
                    // can't call simple constructors, use path
                    outline.setConvexPath(mPath);
                    return;
                }
            }
        }

        final RectF rect = rect();
        outline.setRoundRect((int) Math.ceil(rect.left), (int) Math.ceil(rect.top),
                (int) Math.floor(rect.right), (int) Math.floor(rect.bottom)-paddingBottom, radius);
    }

    @Override
    protected void onResize(float w, float h) {
        super.onResize(w, h);

        RectF r = rect();
        mPath.reset();

        if (mOuterRadii != null) {
            r.bottom -= paddingBottom;
            mPath.addRoundRect(r, mOuterRadii, Path.Direction.CW);
        } else {
            r.bottom -= paddingBottom;
            mPath.addRect(r, Path.Direction.CW);
        }
        if (mInnerRect != null) {
            mInnerRect.set(r.left + mInset.left, r.top + mInset.top,
                    r.right - mInset.right, r.bottom - mInset.bottom);
            if (mInnerRect.width() < w && mInnerRect.height() < h) {
                if (mInnerRadii != null) {
                    mPath.addRoundRect(mInnerRect, mInnerRadii, Path.Direction.CCW);
                } else {
                    mPath.addRect(mInnerRect, Path.Direction.CCW);
                }
            }
        }

        mPath.moveTo(r.left + (r.right - r.left)/2 - mArrowRadius/2 - offsetX,r.bottom);
        mPath.lineTo(r.left + (r.right - r.left)/2 - offsetX,r.bottom+mArrowRadius);
        mPath.lineTo(r.left + (r.right - r.left)/2 + mArrowRadius/2 - offsetX,r.bottom);
    }

    @Override
    public ArrowShape clone() throws CloneNotSupportedException {
        final ArrowShape shape = (ArrowShape) super.clone();
        shape.mOuterRadii = mOuterRadii != null ? mOuterRadii.clone() : null;
        shape.mInnerRadii = mInnerRadii != null ? mInnerRadii.clone() : null;
        shape.mInset = new RectF(mInset);
        shape.mInnerRect = new RectF(mInnerRect);
        shape.mPath = new Path(mPath);
        return shape;
    }

    public void setBackgroundColor(@ColorInt int color) {
        this.bgColor = color;
    }
}

package com.example.arrowview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

import static com.example.arrowview.Utils.dp2px;


public class TipWindow extends PopupWindow {

    private final ArrowView arrowView;

    private WeakReference<Activity> act;

    private static TipWindow ins;

    public TipWindow(Activity context) {
        super(context);
        this.act = new WeakReference<>(context);
        arrowView = new ArrowView(context);
        arrowView.setColor(Color.RED,Color.GREEN);

        resize();
        setContentView(arrowView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 获取实例对象
     * @param act
     * @return
     */
    public static TipWindow singleton(Activity act) {
        if (ins == null) {
            synchronized (TipWindow.class) {
                if (ins == null) {
                    ins = new TipWindow(act);
                }
            }
        }
        return ins;
    }

    /**
     * 设置主题色
     * @param textColor
     * @param backgroundColor
     * @return
     */
    public TipWindow theme(@ColorInt int textColor, @ColorInt int backgroundColor) {
        arrowView.setColor(textColor,backgroundColor);
        return this;
    }

    /**
     * 设置背景透明度
     * @param alpha
     * @return
     */
    public TipWindow alpha(float alpha) {
        arrowView.setBackgroundAlpha(alpha);
        return this;
    }

    /**
     * 设置圆角弧度
     * @param radiusDpValue
     * @return
     */
    public TipWindow cornerRadius(int radiusDpValue) {
        arrowView.setCornerRadius(radiusDpValue);
        return this;
    }
    /**
     * 在指定的view上面弹窗
     * @param tip
     * @param anchor
     */
    public void showAboveAnchor(String tip,View anchor) {
        arrowView.setText(tip);
        Activity a = act.get();
        arrowView.setPadding(dp2px(a,10),dp2px(a,5)
                ,dp2px(a,10),dp2px(a,15));

        resize();

        int[] point = calculateAnchor(anchor);

        super.showAtLocation(act.get().getWindow().getDecorView(), Gravity.NO_GRAVITY, point[0], point[1]);
    }

    /**
     * 根据锚点计算弹窗显示位置
     * @param anchor
     * @return
     */
    private int[] calculateAnchor(View anchor) {
        int[] ints = new int[2];
        int anchorX = (int) anchor.getX();
        int anchorWidth = anchor.getWidth();
        int sx = anchorX + anchorWidth / 2;
        anchor.getLocationOnScreen(ints);

        int marginX = 0;

        if (ints[0] != anchorX) {
            marginX = ints[0] - anchorX;
        }
        ints[1] = ints[1] - arrowView.getMeasuredHeight();
        ints[0] = sx - arrowView.getMeasuredWidth()/2 +marginX;
        arrowView.drawBackground(anchor);
        return ints;
    }

    /**
     * 重新测量控件大小
     */
    public void resize() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) arrowView.getLayoutParams();
        if (params == null) {
            params = new FrameLayout.LayoutParams(-1, -1);
        }
        arrowView.setLayoutParams(params);
        arrowView.measure(0,0);
        int measuredWidth = arrowView.getMeasuredWidth();
        int measuredHeight = arrowView.getMeasuredHeight();

        //重新设置pop window宽高
        setWidth(measuredWidth);
        setHeight(measuredHeight);
    }

    /**
     * 销魂引用，防止内存泄漏
     */
    public void destory() {
        if (act != null) {
            act.clear();
        }
    }
}

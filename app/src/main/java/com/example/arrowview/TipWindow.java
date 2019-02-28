package com.example.arrowview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import static com.example.arrowview.Utils.dp2px;


public class TipWindow extends PopupWindow {

    private static final String TAG = "TipWindow";
    private final ArrowView arrowView;

    public TipWindow(Context context) {
        super(context);

        arrowView = new ArrowView(context);
        arrowView.setColor(Color.RED,Color.GREEN);

        resize();
        setContentView(arrowView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 在指定的view上面弹窗
     * @param s
     * @param parent
     * @param anchor
     */
    public void showAtLocation(String s,View parent,View anchor) {
        arrowView.setText(s);

        Context context = parent.getContext();

        arrowView.setPadding(dp2px(context,10),dp2px(context,5)
                ,dp2px(context,10),dp2px(context,15));

        resize();

        int[] point = calculateAnchor(anchor);

        super.showAtLocation(parent, Gravity.NO_GRAVITY, point[0], point[1]);
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
        ints[1] = ints[1] - arrowView.getMeasuredHeight();
        ints[0] = sx - arrowView.getMeasuredWidth()/2 ;
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

        Log.e(TAG, "TipWindow: w : "+measuredWidth+ "   h : "+measuredHeight);

        //重新设置pop window宽高
        setWidth(measuredWidth);
        setHeight(measuredHeight);
    }
}

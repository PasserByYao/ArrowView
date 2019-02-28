package com.example.arrowview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TipWindow extends PopupWindow {

    private static final String TAG = "TipWindow";
    private final ArrowView arrowView;

    public TipWindow(Context context) {
        super(context);

        arrowView = new ArrowView(context);
        arrowView.setText("爽歪歪");
        arrowView.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        arrowView.setPadding(dp2px(10),dp2px(5),dp2px(10),dp2px(15));
        arrowView.setLayoutParams(layoutParams);
        arrowView.measure(0,0);
        int measuredWidth = arrowView.getMeasuredWidth();
        int measuredHeight = arrowView.getMeasuredHeight();

        Log.e(TAG, "TipWindow: w : "+measuredWidth+ "   h : "+measuredHeight);

        setWidth(measuredWidth);
        setHeight(measuredHeight);
        setContentView(arrowView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static int dp2px(int dp) {
        return dp * 3;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        arrowView.setText("爽歪歪", TextView.BufferType.NORMAL);
        super.showAtLocation(parent, gravity, x, y);
    }
}

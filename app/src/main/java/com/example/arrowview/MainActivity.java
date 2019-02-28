package com.example.arrowview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private TipWindow tipWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tipWindow = new TipWindow(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        Log.e("Main Act : ", "rawx : "+rawX+"   rawy : "+rawY );
        tipWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY,rawX,rawY);
        return true;
    }
}

package com.example.parq_indoor;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ParkingView extends View {
    public ParkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParkingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // TODO
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

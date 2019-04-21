package com.example.parq_indoor;

import android.content.Context;

public class Utils {
    public static float dpToPx(float dp, Context context) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}

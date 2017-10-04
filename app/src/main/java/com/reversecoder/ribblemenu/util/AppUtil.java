package com.reversecoder.ribblemenu.util;

import android.content.Context;

/**
 * Created by Rashed on 03-Oct-17.
 */

public class AppUtil {

    public static int toPx(Context context, int value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value * density);
    }
}

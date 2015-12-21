package com.androidso.utils;

import android.content.Context;
import android.util.TypedValue;


/**
 * Created by qaza2008 on 15/6/3.
 */
public class AndroidUtils {
    /**
     * 转换dip到px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue - 0.5f) / scale);

    }

}

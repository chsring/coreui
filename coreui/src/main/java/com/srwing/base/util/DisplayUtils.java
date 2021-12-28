package com.srwing.base.util;

import android.content.Context;

/**
 * Description:
 * Created by small small su
 * Date: 2021/11/24
 * Email: surao@foryou56.com
 */
public class DisplayUtils {

    public static int dip2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}

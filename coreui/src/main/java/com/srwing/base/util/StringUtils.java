package com.srwing.base.util;

import android.text.TextUtils;

/**
 * Description:处理字符串的工具类
 * Created by small small su
 * Date: 2021/12/13
 * Email: surao@foryou56.com
 */
public class StringUtils {

    public static String getNotNullString(String text) {
        return !TextUtils.isEmpty(text) ? text : "";
    }
}

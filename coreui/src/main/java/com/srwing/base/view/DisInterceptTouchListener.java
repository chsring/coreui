package com.srwing.base.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:添加阻尼回弹效果
 * Created by small small su
 * Date: 2021/12/9
 * Email: surao@foryou56.com
 */
public class DisInterceptTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!(v instanceof ViewGroup)) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                v.onTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return false;
    }
}

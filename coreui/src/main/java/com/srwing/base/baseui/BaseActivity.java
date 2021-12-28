package com.srwing.base.baseui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.srwing.base.R;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;


/**
 * 沉浸状态栏基类Ativity，只由BaseActivity继承
 * Created by 13932 on 2018/12/26.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(getAnimInId(), R.anim.push_no_translate);
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundResource(R.color.activityBackground);
        if (isStatusBarSupport()) {
            setupTheme();
        }
    }

    private void setupTheme() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (dark()) {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(visibility);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            setStatusBarMode(this, true);
        }
    }

    protected boolean dark() {
        return false;
    }

    /**
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS 的时候才有效，也就是只有在状态栏全透明的时候才有效。
     */
    public static boolean setStatusBarMode(Activity aActivity, boolean aDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View lDecorView = aActivity.getWindow().getDecorView();
            if (lDecorView != null) {
                int lVisibility = lDecorView.getSystemUiVisibility();
                if (aDark) {
                    lVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    lVisibility &= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                lDecorView.setSystemUiVisibility(lVisibility);
                return true;
            }
        }
        return false;
    }

    protected boolean isStatusBarSupport() {
        return true;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_no_translate, getAnimOutId());
    }

    protected int getAnimInId() {
        return R.anim.push_in_from_right;
    }


    protected int getAnimOutId() {
        return R.anim.push_out_to_right;
    }

    public final void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }
}

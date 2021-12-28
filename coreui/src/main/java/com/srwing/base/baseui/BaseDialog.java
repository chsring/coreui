package com.srwing.base.baseui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.wj.base.util.DensityUtils;


/**
 * Dialog支持沉浸式，使用时需要设置R.style.DialogStyleStatus,且重写isStatusBarSupport返回true
 */

public abstract class BaseDialog extends Dialog{

    protected View mView;

    public BaseDialog(@NonNull Context context) {
        super(context);
        initDialogAttrs();
        initView();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialogAttrs();
        initView();
    }

    private void initDialogAttrs(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        window.setDimAmount(0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setLayout(getDialogWindowWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
        configAttrs(params, window, true);

    }

    protected void configAttrs(WindowManager.LayoutParams params, Window window, boolean isTransparent){
        if(isTransparent) window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initView(){
        mView  = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        bindView();
        bindListener();
//        mView.setBackgroundDrawable(getBackDrawable());
        setContentView(mView);
        initLaterView();
    }




    protected int getDialogWindowWidth() {
        return DensityUtils.getScreenWidth(getContext()) * 4 / 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isStatusBarSupport()){
            setupTheme();
        }
    }

    protected Drawable getBackDrawable() {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColor(Color.parseColor("#ffffff"));
        drawable.setCornerRadius(DensityUtils.dip2px(getContext(),15));
        return drawable;
    }

    protected <T extends View> T findView(int id){
        View view = mView.findViewById(id);
        return (T) view;
    }


    protected abstract int getLayoutId();

    protected abstract void bindView();

    protected abstract void bindListener();

    protected void initLaterView(){}

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
            setStatusBarMode(true);
        }
    }

    protected boolean dark() {
        return false;
    }

    /**
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS 的时候才有效，也就是只有在状态栏全透明的时候才有效。
     */
    public boolean setStatusBarMode(boolean aDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View lDecorView = getWindow().getDecorView();
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

    protected boolean isStatusBarSupport(){
        return false;
    }
}

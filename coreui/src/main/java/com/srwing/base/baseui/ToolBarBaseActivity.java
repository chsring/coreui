package com.srwing.base.baseui;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.srwing.base.R;
import com.srwing.base.view.CustomToolBar;

/**
 * Description:
 * Created by small small su
 * Date: 2021/11/27
 * Email: surao@foryou56.com
 */
public class ToolBarBaseActivity extends BaseActivity {
    protected CustomToolBar toolBar;
    private int space = 40;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new CustomToolBar(this);
        toolBar.setBarBackground(ContextCompat.getColor(this, R.color.color_6287c7));
        toolBar.setBarTitleTextColor(ContextCompat.getColor(this, R.color.color_FFFFFF));
        toolBar.setBarTitleTextSize(7);
        toolBar.setBarTextBlod(true);
        setSupportActionBar(toolBar);
        setHomeUpEnable();
    }

    @Override
    public void onAttachedToWindow() {
        ViewGroup group = findViewById(android.R.id.content);
        int barHeight = BarUtils.getActionBarHeight() + space;
        int width = ScreenUtils.getScreenWidth();
        ViewGroup childAt = (ViewGroup) group.getChildAt(0);

        int paddingTop;
        if (childAt == null) {
            paddingTop = 0;
        } else {
            paddingTop = childAt.getPaddingTop() + barHeight;
            childAt.setPadding(0, paddingTop, 0, 0);
        }
        group.addView(toolBar, width, barHeight);
    }

    private void setHomeUpEnable() {
        toolBar.setLeftButtonIcon(R.drawable.icon_left_arrow);
        toolBar.setRightButtonIconRight(R.drawable.icon_more);
        toolBar.setLeftButtonOnClickListener(v -> onBackPressed());
    }
}

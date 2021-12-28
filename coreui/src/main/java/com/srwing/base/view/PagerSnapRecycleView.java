package com.srwing.base.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:
 * Created by small small su
 * Date: 2021/12/10
 * Email: surao@foryou56.com
 */
public class PagerSnapRecycleView extends RecyclerView {
    public PagerSnapRecycleView(@NonNull Context context) {
        super(context);
        setSnap();
    }

    public PagerSnapRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setSnap();
    }

    public PagerSnapRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSnap();
    }

    private void setSnap() {
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(this);
        setOnTouchListener(new DisInterceptTouchListener());
    }
}

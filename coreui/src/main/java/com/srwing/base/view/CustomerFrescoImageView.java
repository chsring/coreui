package com.srwing.base.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class CustomerFrescoImageView extends SimpleDraweeView {
    public CustomerFrescoImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public CustomerFrescoImageView(Context context) {
        super(context);
    }

    public CustomerFrescoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (getDrawable() != null) {
            getDrawable().setVisible(true, false);
        }
    }

}

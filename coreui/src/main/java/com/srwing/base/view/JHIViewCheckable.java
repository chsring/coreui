package com.srwing.base.view;

import android.widget.Checkable;

import androidx.annotation.Nullable;

public interface JHIViewCheckable extends Checkable {
    /**
     * 获取viewId
     */
    int getViewId();

    void setOnCheckedChangeListener(@Nullable JHViewOnCheckedChangeListener listener);

    /**
     * 获取显示的文字
     * @return
     */
    String getViewText();

}

package com.srwing.base.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.srwing.base.R;

import java.util.ArrayList;
import java.util.List;

public class JHRadioGroup extends LinearLayout {

    // holds the checked id; the selection is empty by default
    private int mCheckedId = -1;
    // tracks children radio buttons checked state
    private JHViewOnCheckedChangeListener mChildOnCheckedChangeListener;
    // when true, mOnCheckedChangeListener discards events
    private boolean mProtectFromCheckedChange = false;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;


    public JHRadioGroup(Context context) {
        this(context, null);
    }

    public JHRadioGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JHRadioGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.JHRadioGroup, defStyleAttr, 0);
        boolean isVertical = a.getBoolean(R.styleable.JHRadioGroup_vertical, true);
        a.recycle();
        setOrientation(isVertical ? VERTICAL : HORIZONTAL);
        init();
    }


    private void init() {
        mCheckedId = View.NO_ID;
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != View.NO_ID) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }


    /**
     * ??????????????????????????????????????????
     */
    private List<BottomItemView> findCheckedView(View child) {
        List<BottomItemView> allCompoundButtonList = null;
        if (child instanceof BottomItemView) {
            allCompoundButtonList = new ArrayList();
            allCompoundButtonList.add((BottomItemView) child);
        } else if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                List<BottomItemView> allChildCompoundButtonList = findCheckedView(group.getChildAt(i));
                if (allChildCompoundButtonList != null) {
                    if (allCompoundButtonList == null) {
                        allCompoundButtonList = new ArrayList();
                    }
                    allCompoundButtonList.addAll(allChildCompoundButtonList);
                }
            }
        }
        return allCompoundButtonList;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof JHIViewCheckable) {
            final JHIViewCheckable button = (JHIViewCheckable) child;
            if (button.isChecked()) {
                mProtectFromCheckedChange = true;
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getViewId());
            }
        }

        super.addView(child, index, params);
    }

    /**
     * <p>Sets the selection to the radio button whose identifier is passed in
     * parameter. Using -1 as the selection identifier clears the selection;
     * such an operation is equivalent to invoking {@link #clearCheck()}.</p>
     *
     * @param id the unique id of the radio button to select in this group
     * @see #getCheckedRadioButtonId()
     * @see #clearCheck()
     */
    public void check(int id) {
        // don't even bother
        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(id);
    }

    private void setCheckedId(int id) {
        mCheckedId = id;
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof JHIViewCheckable) {
            ((JHIViewCheckable) checkedView).setChecked(checked);
        }
    }

    /**
     * <p>Returns the identifier of the selected radio button in this group.
     * Upon empty selection, the returned value is -1.</p>
     *
     * @return the unique id of the selected radio button in this group
     * @attr ref android.R.styleable#NestRadioGroup_checkedButton
     * @see #check(int)
     * @see #clearCheck()
     */
    public int getCheckedRadioButtonId() {
        return mCheckedId;
    }

    public String getCheckButtonText() {
        View checkedView = findViewById(mCheckedId);
        if (checkedView != null && checkedView instanceof JHIViewCheckable) {
            return ((JHIViewCheckable) checkedView).getViewText();
        }
        return "";
    }

    /**
     * <p>Clears the selection. When the selection is cleared, no radio button
     * in this group is selected and {@link #getCheckedRadioButtonId()} returns
     * null.</p>
     *
     * @see #check(int)
     * @see #getCheckedRadioButtonId()
     */
    public void clearCheck() {
        check(-1);
    }

    /**
     * <p>Register a callback to be invoked when the checked radio button
     * changes in this group.</p>
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * <p>This set of layout parameters defaults the width and the height of
     * the children to {@link #WRAP_CONTENT} when they are not specified in the
     * XML file. Otherwise, this class ussed the value read from the XML file.</p>
     * <p/>
     * <p>See
     * <p>
     * for a list of all child view attributes that this class supports.</p>
     */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int w, int h) {
            super(w, h);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        /**
         * <p>Fixes the child's width to
         * {@link ViewGroup.LayoutParams#WRAP_CONTENT} and the child's
         * height to  {@link ViewGroup.LayoutParams#WRAP_CONTENT}
         * when not specified in the XML file.</p>
         *
         * @param a          the styled attributes set
         * @param widthAttr  the width attribute to fetch
         * @param heightAttr the height attribute to fetch
         */
        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {

            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = WRAP_CONTENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }

    /**
     * <p>Interface definition for a callback to be invoked when the checked
     * radio button changed in this group.</p>
     */
    public interface OnCheckedChangeListener {
        /**
         * <p>Called when the checked radio button has changed. When the
         * selection is cleared, checkedId is -1.</p>
         *
         * @param group     the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio button
         */
        void onCheckedChanged(JHRadioGroup group, int checkedId);
    }

    private class CheckedStateTracker implements JHViewOnCheckedChangeListener {

        public void onCheckedChanged(JHIViewCheckable buttonView, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getViewId();
            setCheckedId(id);
        }
    }

    /**
     * <p>A pass-through listener acts upon the events and dispatches them
     * to another listener. This allows the table layout to set its own internal
     * hierarchy change listener without preventing the user to setup his.</p>
     */
    private class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {
        private OnHierarchyChangeListener mOnHierarchyChangeListener;

        /**
         * {@inheritDoc}
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void onChildViewAdded(View parent, View child) {
            if (parent == JHRadioGroup.this && child instanceof JHIViewCheckable) {
                int id = child.getId();
                // generates an id if it's missing
                if (id == View.NO_ID && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    id = View.generateViewId();
                    child.setId(id);
                }
                ((JHIViewCheckable) child).setOnCheckedChangeListener(mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onChildViewRemoved(View parent, View child) {
            if (parent == JHRadioGroup.this && child instanceof JHIViewCheckable) {
                ((JHIViewCheckable) child).setOnCheckedChangeListener(null);
            }
            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

}

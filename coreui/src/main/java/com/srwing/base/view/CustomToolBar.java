package com.srwing.base.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.srwing.base.R;
import com.srwing.base.util.DisplayUtils;


/**
 * Description:
 * Created by srwing
 * Date: 22/10/2018
 * Email: surao@foryou56.com
 */
public class CustomToolBar extends Toolbar {

    private LayoutInflater mInflater;

    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private Button mLeftButton;
    private Button mRightButton_Left;
    private ImageView mRightButton_Right;
    private TextView mTextRightRight;
    private TextView mSearchViewCopy;
    private LinearLayout mSearchViewLayout;
    private ConstraintLayout mllRightBtn;
    private ImageView mSearchViewClearIcon;
    private RelativeLayout rlLeftBtn, mRlContent;
    private ImageView mIvBottomLine;

    private Context mContext;

    public CustomToolBar(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    @SuppressLint("RestrictedApi")
    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initView();
//        setContentInsetsRelative(20, 20);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CustomToolBar, defStyleAttr, 0);
            boolean isShowSearchView = a.getBoolean(R.styleable.CustomToolBar_isShowSearchView, false);

            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }

            final Drawable leftIcon = a.getDrawable(R.styleable.CustomToolBar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }
            CharSequence leftButtonText = a.getText(R.styleable.CustomToolBar_leftButtonText);
            if (leftButtonText != null) {
                setLeftButtonText(leftButtonText);
            }
            final Drawable rightIconleft = a.getDrawable(R.styleable.CustomToolBar_rightButtonIcon_left);
            if (rightIconleft != null) {
                setRightButtonIconLeft(rightIconleft);
            }
            CharSequence rightButtonTextleft = a.getText(R.styleable.CustomToolBar_rightButtonText_left);
            if (rightButtonTextleft != null) {
                setRightButtonTextLeft(rightButtonTextleft);
            }
            final Drawable rightIconRight = a.getDrawable(R.styleable.CustomToolBar_rightButtonIcon_right);
            if (rightIconRight != null) {
                setRightButtonIconRight(rightIconRight);
            }
            CharSequence rightButtonTextRight = a.getText(R.styleable.CustomToolBar_rightButtonText_right);
            if (rightButtonTextRight != null) {
                setRightButtonTextRight(rightButtonTextRight);
            }
            a.recycle();
        }

    }

    private void initView() {

        if (mView == null) {

            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.core_ui_custom_toolbar, null);
            mTextTitle = mView.findViewById(R.id.toolbar_title);
            mSearchView = mView.findViewById(R.id.toolbar_searchview);
            mRightButton_Left = mView.findViewById(R.id.toolbar_rightButton_left);
            mRightButton_Right = mView.findViewById(R.id.toolbar_rightButton_right);
            mLeftButton = mView.findViewById(R.id.toolbar_leftButton);
            rlLeftBtn = mView.findViewById(R.id.rl_Left_Btn);
            mTextRightRight = mView.findViewById(R.id.toolbar_rightText_right);
            mSearchViewCopy = mView.findViewById(R.id.toolbar_searchview_copy);
            mSearchViewLayout = mView.findViewById(R.id.searchview_layout);
            mSearchViewClearIcon = mView.findViewById(R.id.icon_clear_edit);
            mllRightBtn = mView.findViewById(R.id.layout_right);
            mIvBottomLine = mView.findViewById(R.id.iv_bottom_line);
            mRlContent = mView.findViewById(R.id.rl_content);
            mSearchView.addTextChangedListener(new FYTextWatcherWithClear(mSearchView, mSearchViewClearIcon));

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);

            addView(mView, lp);
        }
    }

    public void setBarTitleText(String text) {
        mTextTitle.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setBarTitleTextColor(int color) {
        mTextTitle.setTextColor(color);
    }

    public void setBarBackground(int color) {
        mView.setBackgroundColor(color);
    }

    public void setBarTitleTextSize(int dp) {
        mTextTitle.setTextSize(DisplayUtils.dip2px(mContext, dp));
    }

    public void setBarTextBlod(boolean blod) {
        TextPaint tp = mTextTitle.getPaint();
        tp.setFakeBoldText(blod);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonIconLeft(Drawable icon) {

        if (mRightButton_Left != null) {
            mRightButton_Left.setBackground(icon);
            mRightButton_Left.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonIconLeft(int icon) {
        setRightButtonIconLeft(getResources().getDrawable(icon));
    }

    public void setRightButtonLeftOnClickListener(OnClickListener li) {
        mRightButton_Left.setOnClickListener(li);
    }

    public void setRightButtonTextLeft(CharSequence text) {
        mRightButton_Left.setText(text);
        mRightButton_Left.setVisibility(VISIBLE);
    }

    public void setRightButtonTextLeft(int id) {
        setRightButtonTextLeft(getResources().getString(id));
    }

    public Button getRightButton_Left() {
        return this.mRightButton_Left;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonIconRight(Drawable icon) {

        if (mRightButton_Right != null) {
            mRightButton_Right.setImageDrawable(icon);
            mRightButton_Right.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonIconRight(int icon) {
        setRightButtonIconRight(getResources().getDrawable(icon));
    }

    public void setRightButtonRightOnClickListener(OnClickListener li) {
        mllRightBtn.setOnClickListener(li);
    }

    public void setRightButtonTextRight(CharSequence text) {
        mTextRightRight.setText(text);
        mTextRightRight.setVisibility(VISIBLE);
    }

    public void setRightButtonTextRight(int id) {
        setRightButtonTextRight(getResources().getString(id));
    }

    public ImageView getRightButton_Right() {
        return this.mRightButton_Right;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftButtonIcon(Drawable icon) {
        if (mLeftButton != null) {
            mLeftButton.setBackground(icon);
            mLeftButton.setVisibility(VISIBLE);
            rlLeftBtn.setVisibility(VISIBLE);
        }
    }

    public void setLeftButtonIcon(int icon) {
        setLeftButtonIcon(getResources().getDrawable(icon));
    }

    public void setLeftButtonOnClickListener(OnClickListener li) {
        rlLeftBtn.setOnClickListener(li);
    }

    public void setLeftButtonText(CharSequence text) {
        mLeftButton.setText(text);
        mLeftButton.setVisibility(VISIBLE);
        rlLeftBtn.setVisibility(VISIBLE);
    }

    public void setLeftButtonText(int id) {
        setLeftButtonText(getResources().getString(id));
    }


    public Button getLeftButton() {
        return this.mLeftButton;
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {

        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }


    public void setSearchHint(String hint) {
        if (mSearchView != null)
            mSearchView.setHint(hint);
    }

    public void showSearchView() {
        if (mSearchViewLayout != null) {
            mSearchViewLayout.setVisibility(VISIBLE);
        }
    }

    public String getSearchContent() {
        if (mSearchView != null) {
            return mSearchView.getText().toString();
        }
        return null;
    }

    public void showSearchView(Activity activity) throws Exception {
        if (null == activity) {
            throw new Exception("activity can not be null");
        }
        if (mSearchViewLayout != null) {
            mSearchViewLayout.setVisibility(VISIBLE);
            mSearchView.setFocusable(true);
            mSearchView.setFocusableInTouchMode(true);
            mSearchView.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) activity.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) return;
            imm.showSoftInput(mSearchView, InputMethodManager.SHOW_FORCED);
        }
    }

    public void hideSearchView() {
        if (mSearchViewLayout != null)
            mSearchViewLayout.setVisibility(GONE);
    }

    public void showTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);

    }

    public EditText getSearchView() {
        return mSearchView;
    }

    public void setRightRightText(String text, OnClickListener listener) {
        mTextRightRight.setText(text);
        mTextRightRight.setVisibility(VISIBLE);
        mTextRightRight.setOnClickListener(listener);
    }

    public void setRightRightTextColorAndSize(int color, int size) {
        mTextRightRight.setVisibility(VISIBLE);
        mTextRightRight.setTextColor(color);
        mTextRightRight.setTextSize(size);
    }

    public void setSearchViewCopyTextAndLisner(String text, OnClickListener listener) {
        mSearchViewCopy.setVisibility(VISIBLE);
        if (listener != null) {
            mSearchViewCopy.setOnClickListener(listener);
        }

        if (!TextUtils.isEmpty(text)) {
            mSearchViewCopy.setText(text);
        }
    }

    //适配好运蓝色标题
    public void setIsBlueStyle() {
        if (mView != null) {
            mView.setBackgroundColor(getResources().getColor(R.color.core_ui_color_0064ff));
            setBackgroundColor(getResources().getColor(R.color.core_ui_color_0064ff));
            setLeftButtonIcon(R.drawable.core_ui_icon_white_arrow);
            mTextTitle.setTextColor(getResources().getColor(R.color.color_FFFFFF));
            mTextRightRight.setTextColor(getResources().getColor(R.color.color_FFFFFF));
        }

    }

    //底部线默认height=0.5 color= e6e6e6
    public void setBottomLineVisableGone(int viewStatus) {
        mIvBottomLine.setVisibility(viewStatus);
    }

    public void setBottomLineVisable(int colorId) {
        mIvBottomLine.setVisibility(VISIBLE);
        mIvBottomLine.setBackgroundColor(colorId);
    }

    //设置左右按钮边距
    public void setContentPadding(int leftPadding, int rightPadding) {
        mRlContent.setPadding(leftPadding, 0, rightPadding, 0);
    }

}
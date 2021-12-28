package com.srwing.enterwechat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.srwing.base.baseui.BaseDialog;
import com.wj.base.util.TextUtils;

/**
 * Created by liuyh on 18/12/14.
 */

public class JHDialog extends BaseDialog {

    private TextView mContentTv;
    private TextView mCancelTv;
    private TextView mSureTv;
    private TextView mTitleTv;
    private ImageView mTipIv;
    private ClearEditText editText;
    private ClearEditText editTextRe;
    private View view, viewRe;

    private OnJHClickListener mListener;
    private boolean mIsSureCancel;

    public JHDialog(@NonNull Context context, String title, String content, String cancelTxt, String sureTxt, OnJHClickListener listener) {
        super(context);
        initView(title, content, cancelTxt, sureTxt, listener, true);
    }

    public JHDialog(@NonNull Context context, String title, String content, String cancelTxt, String sureTxt, OnJHClickListener listener, boolean isSureCancel) {
        super(context);
        initView(title, content, cancelTxt, sureTxt, listener, isSureCancel);
    }

    public JHDialog(Context context, @StringRes int titleId, @StringRes int contentId, @StringRes int cancelTxtId, @StringRes int sureTxtId) {
        super(context);
        initView(titleId, contentId, cancelTxtId, sureTxtId);
    }

    public JHDialog(Context context, @StringRes int contentId, @StringRes int cancelTxtId, @StringRes int sureTxtId) {
        super(context);
        initView(0, contentId, cancelTxtId, sureTxtId);
    }

    public JHDialog(Context context, @StringRes int contentId, @StringRes int sureTxtId) {
        super(context);
        initView(0, contentId, 0, sureTxtId);
    }

    private void initView(String title, String content, String cancelTxt, String sureTxt, OnJHClickListener listener, boolean isSureCancel) {
        if (TextUtils.isEmpty(title)) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
        }
        mTitleTv.setText(title);
        mContentTv.setText(content);
        if (TextUtils.isEmpty(cancelTxt)) {
            mCancelTv.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
        } else {
            mCancelTv.setVisibility(View.VISIBLE);
            mCancelTv.setText(cancelTxt);
        }
        mSureTv.setText(sureTxt);
        mIsSureCancel = isSureCancel;
        mListener = listener;
    }

    private void initView(@StringRes int titleId, @StringRes int contentId, @StringRes int cancelTxtId, @StringRes int sureTxtId) {
        if (titleId == 0) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(titleId);
        }
        mContentTv.setText(contentId);
        if (cancelTxtId == 0) {
            mCancelTv.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
        } else {
            mCancelTv.setVisibility(View.VISIBLE);
            mCancelTv.setText(cancelTxtId);
        }
        if (sureTxtId == 0) {
            mSureTv.setVisibility(View.GONE);
            setCanceledOnTouchOutside(true);
        } else {
            mSureTv.setText(sureTxtId);
        }
    }

    public JHDialog setOnClickListener(OnJHClickListener listener) {
        mListener = listener;
        return this;
    }

    public JHDialog isSureCancel(boolean isSureCancel) {
        mIsSureCancel = isSureCancel;
        return this;
    }

    public JHDialog setOnCancelable(boolean flag) {
        setCancelable(flag);
        return this;
    }

    public JHDialog(@NonNull Context context, String title, String content, String cancelTxt, String sureTxt, int tipRes, OnJHClickListener listener) {
        super(context);
        if (TextUtils.isEmpty(title)) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
        }
        mTitleTv.setText(title);
        mContentTv.setText(content);
        if (TextUtils.isEmpty(cancelTxt)) {
            mCancelTv.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
        } else {
            mCancelTv.setVisibility(View.VISIBLE);
            mCancelTv.setText(cancelTxt);
        }
        mTipIv.setBackgroundResource(tipRes);
        mSureTv.setText(sureTxt);
        mIsSureCancel = true;
        mListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_jh_base;
    }

    @Override
    protected void bindView() {
        mTitleTv = findView(R.id.dialog_jh_title_tv);
        mContentTv = findView(R.id.dialog_jh_content_tv);
        mCancelTv = findView(R.id.dialog_jh_cancel_tv);
        mSureTv = findView(R.id.dialog_jh_sure_tv);
        mTipIv = findView(R.id.dialog_jh_tip_iv);
        editText = findView(R.id.dialog_editext);
        editTextRe = findView(R.id.dialog_editext_re);
        view = findView(R.id.view_1);
        viewRe = findView(R.id.view_2);
    }

    @Override
    protected void configAttrs(WindowManager.LayoutParams params, Window window, boolean isTransparent) {
        super.configAttrs(params, window, true);
        window.setDimAmount(0.5f);
        params.gravity = Gravity.CENTER;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = getContext().getResources().getDimensionPixelOffset(R.dimen.dp520);
    }

    public void setContent(String content) {
        mContentTv.setText(content);
    }

    public JHDialog setEditText(String text, int inputType, int maxLength) {
        editTextRe.setVisibility(View.GONE);
        viewRe.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            editText.setHint(text);
        }
        editText.setInputType(inputType);
        editText.setMaxLines(maxLength);
        editText.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    public JHDialog setEditTextRe(String text, String reText, int inputType, int maxLength) {
        if (!TextUtils.isEmpty(text)) {
            editText.setHint(text);
        }
        editText.setInputType(inputType);
        editText.setMaxLines(maxLength);
        editText.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(reText)) {
            editTextRe.setHint(reText);
        }
        editTextRe.setInputType(inputType);
        editTextRe.setMaxLines(maxLength);
        editTextRe.setVisibility(View.VISIBLE);
        viewRe.setVisibility(View.VISIBLE);
        return this;
    }

    public JHDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
            mTitleTv.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public String getEditext() {
        return TextUtils.isEmpty(editText.getText().toString()) ? null : editText.getText().toString();
    }

    public String getEditextRe() {
        return TextUtils.isEmpty(editTextRe.getText().toString()) ? null : editTextRe.getText().toString();
    }

    @Override
    protected void bindListener() {

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.cancelClick(JHDialog.this);
                } else {
                    cancel();
                }
            }
        });

        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.sureClick(JHDialog.this);
                }
                if (mIsSureCancel) {
                    cancel();
                }
            }
        });

    }

    public static abstract class OnJHClickListener {
        public abstract void sureClick(JHDialog dialog);

        public void cancelClick(JHDialog dialog) {
            dialog.cancel();
        }
    }
}

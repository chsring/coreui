package com.srwing.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.srwing.base.R;

/**
 * 底部导航 item
 * 支持返回顶部，右上角 免费、红点标签
 */
public class BottomItemView extends RelativeLayout implements JHIViewCheckable {
    private AppCompatImageView mNormalView;
    private AppCompatImageView mSelectView;
    private AppCompatImageView mTopView;
    private AppCompatTextView mTextView;
    private View redPointTipView;
    private View freeTipView;
    private boolean mChecked;
    private boolean mIsReTop = false;
    private HomeBottomItemBean itemBean;

    private Animatable mNormalDrawable;
    private Animatable mSelectDrawable;
    private Animatable mTopDrawable;

    private JHViewOnCheckedChangeListener mOnCheckedChangeListener;

    public BottomItemView(Context context) {
        this(context, null);
        initView(context);
    }

    public BottomItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context);
    }

    public BottomItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化view
     */
    private void initView(Context context) {
        inflate(context, R.layout.view_home_bottom_item, this);
        mNormalView = findViewById(R.id.normal);
        mSelectView = findViewById(R.id.select);
        mTopView = findViewById(R.id.top);
        mTextView = findViewById(R.id.text);
        freeTipView = findViewById(R.id.free_tip);
        redPointTipView = findViewById(R.id.red_point_tip);

//        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources()).setFadeDuration(0).build();
//        mNormalView.setHierarchy(hierarchy);
//        hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources()).setFadeDuration(0).build();
//        mSelectView.setHierarchy(hierarchy);
//        hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources()).setFadeDuration(0).build();
//        mTopView.setHierarchy(hierarchy);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (mChecked) {
                mSelectView.setVisibility(View.VISIBLE);
                mNormalView.setVisibility(View.GONE);
//                if (mSelectDrawable != null) {
//                    mSelectDrawable.start();
//                }
            } else {
                mNormalView.setVisibility(View.VISIBLE);
                mSelectView.setVisibility(View.GONE);
//                if (mSelectDrawable != null) {
//                    mSelectDrawable.stop();
//                }
            }
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }

        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean performClick() {
        if (!isChecked()) {
            toggle();
        }
        final boolean handled = super.performClick();
        return handled;
    }

    /**
     * 设置item数据
     */
//    public void bindItemBean(HomeBottomItemBean itemBean) {
//        if (itemBean == null) {
//            return;
//        }
//        this.itemBean = itemBean;
//
//        mTextView.setText(itemBean.getText());
//        mTextView.setTextColor(Color.parseColor(itemBean.getTextColor()));
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setUri(itemBean.getNormalIcon())
//                .setControllerListener(new BaseControllerListener<ImageInfo>() {
//                    @Override
//                    public void onFinalImageSet(
//                            String id,
//                            @Nullable ImageInfo imageInfo,
//                            @Nullable Animatable animatable) {
//                        mNormalDrawable = animatable;
//                    }
//                })
//                .build();
//        mNormalView.setController(controller);
//
//        controller = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setUri(itemBean.getSelectIcon())
//                .setControllerListener(new BaseControllerListener<ImageInfo>() {
//                    @Override
//                    public void onFinalImageSet(
//                            String id,
//                            @Nullable ImageInfo imageInfo,
//                            @Nullable Animatable animatable) {
//                        mSelectDrawable = animatable;
//                    }
//                })
//                .build();
//        mSelectView.setController(controller);
//        controller = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setUri(itemBean.getReTopIcon())
//                .setControllerListener(new BaseControllerListener<ImageInfo>() {
//                    @Override
//                    public void onFinalImageSet(
//                            String id,
//                            @Nullable ImageInfo imageInfo,
//                            @Nullable Animatable animatable) {
//                        mTopDrawable = animatable;
//                    }
//                })
//                .build();
//        mTopView.setController(controller);
//    }

    /**
     * 设置免费标签显示
     */
    public void freeTipVisibility(int visibility) {
        freeTipView.setVisibility(visibility);
    }

    public boolean freeTipIsShow() {
        return freeTipView.isShown();
    }

    /**
     * 设置红点标签显示
     */
    public void redPointTipVisibility(int visibility) {
        redPointTipView.setVisibility(visibility);
    }

    public boolean redPointTipIsShow() {
        return redPointTipView.isShown();
    }

    /**
     * 是否切换成返回顶部
     */
    public void reTop(boolean isTop) {
        if (mIsReTop != isTop) {
            mIsReTop = isTop;
            if (isTop) {
                mTextView.setText("返回顶部");
                mTopView.setVisibility(View.VISIBLE);
                mNormalView.setVisibility(View.GONE);
                mSelectView.setVisibility(View.GONE);
            } else {
                mTopView.setVisibility(View.GONE);
                mTextView.setText(itemBean.getText());
                if (mChecked) {
                    mSelectView.setVisibility(View.VISIBLE);
                    mNormalView.setVisibility(View.GONE);
//                    if (mSelectDrawable != null) {
//                        mSelectDrawable.start();
//                    }
                } else {
                    mNormalView.setVisibility(View.VISIBLE);
                    mSelectView.setVisibility(View.GONE);
//                    if (mSelectDrawable != null) {
//                        mSelectDrawable.stop();
//                    }
                }

//                FrescoLoadUtils.Companion.loadHttpUrl(mChecked ? itemBean.getSelectIcon() : itemBean.getNormalIcon(), mIconView);
            }
        }

    }

    public boolean isReTop() {
        return this.mIsReTop;
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    @Override
    public void setOnCheckedChangeListener(@Nullable JHViewOnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public String getViewText() {
        return mTextView == null ? "" : mTextView.getText().toString();
    }

    @Override
    public int getViewId() {
        return getId();
    }

//    public interface OnCheckedChangeListener {
//        /**
//         * @param buttonView The compound button view whose state has changed.
//         * @param isChecked  The new checked state of buttonView.
//         */
//        void onCheckedChanged(BottomItemView buttonView, boolean isChecked);
//    }

    private boolean hasShowAnimation = false;
    private final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();

    private final float scaleSize = 0.88f;
    private final float scaleNormalSize = 1f;
    private final long animDuration = 100;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!hasShowAnimation) {
                    scaleSmall();
                    hasShowAnimation = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (hasShowAnimation) {
//                    recoverView();
                    hasShowAnimation = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }


    private void scaleSmall() {
        animate().scaleX(scaleSize).scaleY(scaleSize).setDuration(animDuration).setInterpolator(interpolator);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                recoverView();
            }
        }, animDuration);
    }

    private void recoverView() {
        animate().scaleX(scaleNormalSize).scaleY(scaleNormalSize).setDuration(animDuration).setInterpolator(interpolator);

    }


}

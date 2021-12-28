package com.srwing.enterwechat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.srwing.base.baseui.ToolBarBaseActivity;

/**
 * Description:
 * Created by small small su
 * Date: 2021/12/25
 * Email: surao@foryou56.com
 */
public class ImageActivity extends ToolBarBaseActivity {
    WeChatEntity.WeChatBase data;

    private AppCompatTextView tvName;
    private AppCompatTextView tvNumber;
    private AppCompatTextView tvSex;
    private AppCompatTextView tvXueyuan;
    private AppCompatTextView tvOutTime;
    private AppCompatTextView tvInTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setTitle("");
        data = (WeChatEntity.WeChatBase) getIntent().getSerializableExtra("data");
//        Glide.with(ImageActivity.this).load(url).into(imageView);
//        loadDefaultImage(ImageActivity.this, imageView, url);


        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        tvSex = findViewById(R.id.tv_sex);
        tvXueyuan = findViewById(R.id.tv_xueyuan);
        tvOutTime = findViewById(R.id.tv_out_time);
        tvInTime = findViewById(R.id.tv_in_time);

        tvName.setText("姓名： " + data.name);
        tvNumber.setText("学号： " + data.studentId);
        tvSex.setText("性别： " + data.sex);
        tvXueyuan.setText("所在学院： " + data.faculty);
        tvOutTime.setText("出校时间： " + data.outTime + " " + data.outShiJian);
        tvInTime.setText("返校时间： " + data.backTime + " " + data.backShiJian);

    }

    public static void loadDefaultImage(Context context, AppCompatImageView imageView, String url) {
//        Glide.with(App.getApp()).load(url).into(imageView);
//        Glide.with(context).asBitmap().load(url).dontAnimate().into(new CustomTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                imageView.setImageBitmap(resource);
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//        });

        Glide.with(context).load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
    }

}

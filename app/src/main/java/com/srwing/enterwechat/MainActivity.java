package com.srwing.enterwechat;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.srwing.base.baseui.ToolBarBaseActivity;
import com.srwing.base.util.DateUtils;
import com.srwing.base.util.StringUtils;
import com.srwing.newtwork.ByNet;
import com.srwing.newtwork.callback.IFailure;
import com.srwing.newtwork.callback.ISuccess;
import com.srwing.newtwork.configs.Configurator;
import com.srwing.newtwork.http.IMethod;
import com.srwing.newtwork.interceptors.ByInterceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ToolBarBaseActivity {

    private String URL_BASE = "http://8.140.113.169:8080/";

    JHDialog jhDialog;
    private AppCompatTextView tvApplyTime;
    private AppCompatTextView tvXuehao;
    private AppCompatTextView tvXueyuan;
    private AppCompatTextView tvNianji;
    private AppCompatTextView tvZhuanye;
    private AppCompatTextView tvXueli;
    private AppCompatTextView tvXingbie;
    private AppCompatTextView tvMinzu;
    private AppCompatTextView tvDianhua;
    private AppCompatTextView tvName;
    private AppCompatTextView tvFudaoyuan;
    private AppCompatTextView tvOutDate;
    private AppCompatTextView tvOutTime;
    private AppCompatTextView tvInDate;
    private AppCompatTextView tvInTime;
    private AppCompatTextView tvTianshu;
    private AppCompatTextView ivTongxingzheng;
    private AppCompatTextView tvOtherReason, tvIdCard;
    private AppCompatTextView tvShenPiRen;
    private AppCompatTextView tvShenHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("在校学生出校门申请");

        tvApplyTime = findViewById(R.id.tv_apply_time);
        tvXuehao = findViewById(R.id.tv_xuehao);
        tvXueyuan = findViewById(R.id.tv_xueyuan);
        tvNianji = findViewById(R.id.tv_nianji);
        tvZhuanye = findViewById(R.id.tv_zhuanye);
        tvXueli = findViewById(R.id.tv_xueli);
        tvXingbie = findViewById(R.id.tv_xingbie);
        tvMinzu = findViewById(R.id.tv_minzu);
        tvDianhua = findViewById(R.id.tv_dianhua);
        tvName = findViewById(R.id.tv_name);
        tvIdCard = findViewById(R.id.tv_id_card);
        tvFudaoyuan = findViewById(R.id.tv_fudaoyuan);
        tvOutDate = findViewById(R.id.tv_out_date);
        tvOutTime = findViewById(R.id.tv_out_time);
        tvInDate = findViewById(R.id.tv_in_date);
        tvInTime = findViewById(R.id.tv_in_time);
        tvTianshu = findViewById(R.id.tv_tianshu);
        ivTongxingzheng = findViewById(R.id.iv_tongxingzheng);
        tvShenPiRen = findViewById(R.id.tv_shenpiren);
        tvOtherReason = findViewById(R.id.tv_other_reason);
        tvShenHe = findViewById(R.id.tv_shenhe);



        ivTongxingzheng.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        ivTongxingzheng.getPaint().setAntiAlias(true);//抗锯齿
        initNetConfig();
        CommonTabLayout commonTabLayout = findViewById(R.id.tab);
        ArrayList<CustomTabEntity> mTabEntities;
        mTabEntities = new ArrayList<>();
        mTabEntities.add(new TabEntity("申请表单", 0, 0));
        mTabEntities.add(new TabEntity("办事流程", 0, 0));
        mTabEntities.add(new TabEntity("处理记录", 0, 0));
        commonTabLayout.setTabData(mTabEntities);

        jhDialog = new JHDialog(MainActivity.this, "", "您已经被我们监控，请输入您的代号", "取消", "确定", new JHDialog.OnJHClickListener() {
            @Override
            public void sureClick(JHDialog dialog) {
                String text = dialog.getEditext();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                requestInfo(text);
            }
        });
        jhDialog.setEditText("请输入代号", InputType.TYPE_CLASS_PHONE, 11).show();

    }

    private void requestInfo(String id) {
        ByNet.builder()
                .service(WeChatService.class)
                .params("id", id)
                .method((IMethod<WeChatService>) WeChatService::getUser)
                .success(new ISuccess<WeChatEntity>() {
                    @Override
                    public void onSuccess(WeChatEntity response) {
                        if (response.code == 200 && response != null && response.data != null) {
                            setUser(response.data);
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(int code, String desc) {
                        Log.i("network", code + ": " + desc);
                    }
                })
                .build()
                .excute();
    }

    private void initNetConfig() {
        Configurator configurator = ByNet.init(this)  //初始化
                .withApiHost(URL_BASE);  //设置网络请求 同理的 Domain
//        if (BuildConfig.DEBUG) {
        configurator.withInterceptor(new ByInterceptor()); //设置请求日志解析拦截器
//        }
        configurator.withLoggerAdapter();  //设置LogAdapter
        configurator.withDebugMode(BuildConfig.DEBUG);  //设置是否打印请求 日志
        configurator.withNoProxy(true);
        configurator.configure();  //配置生效
    }

    private void setUser(WeChatEntity.WeChatBase data) {

        tvApplyTime.setText(StringUtils.getNotNullString(data.applicationTime));


        tvXuehao.setText(StringUtils.getNotNullString(data.studentId));
        tvXueyuan.setText(StringUtils.getNotNullString(data.faculty));
        tvNianji.setText(StringUtils.getNotNullString(data.grade));
        tvZhuanye.setText(StringUtils.getNotNullString(data.specialty));
//        tvXueli .setText(StringUtils.getNotNullString(data.applicationTime));
        tvXingbie.setText(StringUtils.getNotNullString(data.sex));
        tvMinzu.setText(StringUtils.getNotNullString(data.nationality));
        tvDianhua.setText(StringUtils.getNotNullString(data.phone));
        tvName.setText(StringUtils.getNotNullString(data.name));
        tvIdCard.setText(StringUtils.getNotNullString(data.idCard));
//        tvFudaoyuan .setText(StringUtils.getNotNullString(data.applicationTime));
        tvOutDate .setText(StringUtils.getNotNullString(data.outTime));
        tvOutTime .setText(StringUtils.getNotNullString(data.outShiJian));

        tvInDate .setText(StringUtils.getNotNullString(data.backTime));
        tvInTime .setText(StringUtils.getNotNullString(data.backShiJian));

//        tvInDate.setText(StringUtils.getNotNullString(data.backTime));

//        tvInTime .setText(StringUtils.getNotNullString(data.backTime));

        tvTianshu .setText(getDays(data.outTime,data.backTime));
        tvFudaoyuan.setText(StringUtils.getNotNullString(data.counselorName));
        tvShenPiRen.setText(StringUtils.getNotNullString(data.counselorName));

        ivTongxingzheng.setText(StringUtils.getNotNullString(data.url));
        tvOtherReason.setText(StringUtils.getNotNullString(data.otherReason));

        tvShenHe.setText(StringUtils.getNotNullString(data.applicationTime));

//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        data.outTime = sdf.format(d);

        ivTongxingzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

    }

    private String getDays(String startDate,String endDate){
        if(TextUtils.isEmpty(startDate)||TextUtils.isEmpty(endDate)){
            return "1天";
        }
       return DateUtils.caculateTotalTime(startDate,endDate,"yyyy-mm-dd");


    }
}
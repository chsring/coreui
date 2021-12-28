package com.srwing.base.baseui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

/**
 * Description:
 * Created by srwing
 * Date: 24/1/2019
 * Email: surao@foryou56.com
 * 添加数据加载监控
 * update by pujuntao on 2019/2/20
 */
public class BaseViewModel extends AndroidViewModel implements IViewModel {

    protected Application application;

    protected MutableLiveData<DataLoadStatus> dataLoadStatus = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<DataLoadStatus> getDataLoadStatus() {
        return dataLoadStatus;
    }

    public void postDataLoadStatus(DataLoadStatus status) {
        if (status == null) return;
        dataLoadStatus.postValue(status);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}

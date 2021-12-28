package com.srwing.base.launch;

import android.content.Intent;

import io.reactivex.rxjava3.core.Observable;


public interface IFragmentProxy {

    Observable<ActivityResultInfo> startForResult(final Intent intent);

    void startForResult(Intent intent, StartActivityUtil.Callback callback);

    void startActivityNoResult(Intent intent);

}

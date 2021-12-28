package com.srwing.base.launch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;


/**
 * @author srwing
 * @fileName StartOnResultFragment
 * @time 2018/9/19 上午11:40
 * @desc
 */

public class StartOnResultFragmentSurport extends Fragment implements IFragmentProxy {
    private final Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private final Map<Integer, StartActivityUtil.Callback> mCallbacks = new HashMap<>();
    private final int REQUEST_CODE = 23;// requestCode 不能超过16bits

    final int MAX_SIZE = 1024; //最大请求码 requestCode 不能超过16bits

    public StartOnResultFragmentSurport() {
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mSubjects.put(REQUEST_CODE, subject);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void startForResult(Intent intent, StartActivityUtil.Callback callback) {
        int address = callback.hashCode() % MAX_SIZE;
        while (mCallbacks.get(address) != null) {
            //  如果 包含了此地址 说明已经存在，则temSize左移1位,再次测试mCallbacks中是否包含此key
            address += 1;
            // 如果移位之后 地址仍然 超过65535 则再次取余数
            if (address > 65530) {
                address %= MAX_SIZE;
            }
        }
        mCallbacks.put(address, callback);
        startActivityForResult(intent, address);
    }

    public void startActivityNoResult(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(resultCode, data));
            subject.onComplete();
        }

        //callback方式的处理
        StartActivityUtil.Callback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(new ActivityResultInfo(resultCode, data));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("life-cycle", "fragment-life-onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i("life-cycle", "fragment-life-onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("life-cycle", "fragment-life-onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("life-cycle", "fragment-life-onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("life-cycle", "fragment-life-onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("life-cycle", "fragment-life-onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("life-cycle", "fragment-life-onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("life-cycle", "fragment-life-onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("life-cycle", "fragment-life-onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("life-cycle", "fragment-life-onDetach");
        super.onDetach();
    }
}

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

public class StartOnResultFragment extends Fragment implements IFragmentProxy{
    private final Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private final Map<Integer, StartActivityUtil.Callback> mCallbacks = new HashMap<>();

    public StartOnResultFragment() {
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mSubjects.put(subject.hashCode(), subject);
//                getActivity().startActivityForResult(intent, 101);
                startActivityForResult(intent, subject.hashCode());
            }
        });
    }

    public void startForResult(Intent intent, StartActivityUtil.Callback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        startActivityForResult(intent, callback.hashCode());
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

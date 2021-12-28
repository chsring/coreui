package com.srwing.base.baseui;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;


/**
 * Description:
 * Created by srwing
 * Date: 24/1/2019
 * Email: surao@foryou56.com
 */
public class TitleViewModel extends BaseViewModel {

    public ObservableField<String> title = new ObservableField<>();

    public MutableLiveData<View.OnClickListener> clickListenerMutableLiveData = new MutableLiveData<>();

    public TitleViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    public void setOnBackClickListener(View.OnClickListener clickListener){
        clickListenerMutableLiveData.setValue(clickListener);
    }


}

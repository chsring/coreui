package com.srwing.base.baseui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.srwing.base.BR;
import com.srwing.base.R;
import com.srwing.base.databinding.CoreUiActivityContainerBinding;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:MvvM模式BaseActivity 处理ViewModel绑定 自带Toolbar功能
 * Created by srwing
 * Date: 23/1/2019
 * Email: surao@foryou56.com
 */
public abstract class MvvMBaseActivity<VB extends ViewDataBinding, VM extends BaseViewModel>
        extends BaseActivity implements IMvvmActivity {

    protected VB dataBinding;
    protected VM viewModel;
    protected CoreUiActivityContainerBinding titleContainerBinding;
    protected TitleViewModel titleViewModel;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public int getBRId() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // 设置为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置没有Title的ContentView
     *
     * @param layoutResID
     */
    public void setContentViewNoTitle(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null, false);
        setContentView(view, false);
    }

    public void setBackButton() {
        titleContainerBinding.toolbar.setLeftButtonIcon(R.drawable.core_ui_icon_arrow_left);
        titleContainerBinding.toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String title) {
        titleContainerBinding.toolbar.setTitle(title);
    }

    /**
     * 设置没有Title的ContentView
     */
    public void setContentViewNoTitle(View view) {
        setContentView(view, false);
    }

    private void setContentView(View view, boolean hasTitle) {
        if (isMvvMMode()) {
            if (hasTitle) {
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this)
                        .inflate(R.layout.core_ui_activity_container, null, false);

                titleContainerBinding = DataBindingUtil.bind(linearLayout);

                ViewModelProvider provider = new ViewModelProvider(this);
                titleViewModel = provider.get(TitleViewModel.class);
                titleContainerBinding.setVariable(BR.titleViewModel, titleViewModel);
                dataBinding = DataBindingUtil.bind(view);
                viewModel = obtainViewModel(this);
                int brId = getBRId();
                dataBinding.setVariable(brId, viewModel);
                titleContainerBinding.container.addView(dataBinding.getRoot());
                //添加ViewModel的生命周期管理
                getLifecycle().addObserver(viewModel);
                super.setContentView(titleContainerBinding.getRoot());

            } else {
                dataBinding = DataBindingUtil.bind(view);
                viewModel = obtainViewModel(this);
                int brId = getBRId();
                dataBinding.setVariable(brId, viewModel);
                super.setContentView(dataBinding.getRoot());
            }
        } else {
            if (hasTitle) {
                super.setContentView(setInnerContentView(view));
            } else {
                super.setContentView(view);
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View childView = LayoutInflater.from(this).inflate(layoutResID, null, false);
        setContentView(childView, true);
    }

    /**
     * 判断是否使用了 MVVM MODE
     *
     * @return
     */
    private boolean isMvvMMode() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            return actualTypeArguments.length == 2;
        }
        return true;
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, true);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setContentView(view, true);
    }


    /**
     * 将应用层的布局放置到容器中
     *
     * @param view
     * @return
     */
    private View setInnerContentView(View view) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.core_ui_activity_container, null, false);
        linearLayout.addView(view);
        return linearLayout;
    }

    /**
     * 根据Activity泛型参数 初始化ViewModel
     *
     * @param activity
     * @param <VM>     ViewModel 泛型参数
     * @return
     */
    public <VM> VM obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel.class;
        }
        ViewModelProvider activityProvider = new ViewModelProvider(activity);
        VM vm = (VM) activityProvider.get(modelClass);
        return vm;
    }

    @Override
    protected void onDestroy() {
        if (null != viewModel) {
            getLifecycle().removeObserver(viewModel);
        }
        if (null != titleViewModel) {
            getLifecycle().removeObserver(titleViewModel);
        }
        super.onDestroy();
    }


}

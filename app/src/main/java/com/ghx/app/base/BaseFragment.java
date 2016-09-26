package com.ghx.app.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghx.app.R;
import com.ghx.app.lulu.utils.ToastUtil;
import com.ghx.app.lulu.weiget.Topbar;

/**
 * Created by guo_hx on 2016/9/22.16:13
 */

public abstract class BaseFragment extends Fragment implements IBaseView, View.OnClickListener {

    private BasePresenter mPresenter;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                if(getActivity()!=null) {
                    handleMsg(msg);
                }
        }
    };
    private Topbar mTopbar;

    public BaseFragment() {

        try {
            mPresenter = (BasePresenter) getPresenter().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayoutId(), container, false);

        mPresenter.initData(savedInstanceState);
        mTopbar = (Topbar) rootView.findViewById(R.id.topbar);
        initAllWidget(rootView);
        viewShow();
        return rootView;
    }

    private void viewShow() {
        mPresenter.viewShow();
    }

    protected void handleMsg(Message msg) {
        mPresenter.handleMsg(msg);
    }

    @Override
    public void onClick(View v) {
        clickView(v);
    }

    protected abstract int getLayoutId();

    protected abstract Class getPresenter();

    protected abstract void initAllWidget(View rootView);

    protected abstract void clickView(View v);

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void toast(String str_msg) {
        ToastUtil.showToast("toast");
    }

    @Override
    public void to(Class<?> cls, Bundle bundles) {
        ToastUtil.showToast("to");
    }

    public Topbar getTopbar() {

        return mTopbar;
    }
}

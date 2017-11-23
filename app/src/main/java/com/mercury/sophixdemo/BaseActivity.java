package com.mercury.sophixdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Mercury on 2017/11/23.
 */

public abstract class BaseActivity extends AppCompatActivity implements DataManager {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        initData(savedInstanceState);
    }

    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

}

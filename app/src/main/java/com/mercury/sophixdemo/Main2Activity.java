package com.mercury.sophixdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity {

    @Bind(R.id.tv_content)
    TextView  tvContent;
    @Bind(R.id.btn_dependency)
    Button    btnDependency;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    private static final String IMAGE_URL = "http://img.ivsky.com/img/tupian/li/201708/23/yingyangfengfudeheimeishuiguotupian-005.jpg";

    @OnClick({R.id.tv_content, R.id.btn_dependency})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_content:
                showToast("新增了资源目录和文件");
                break;
            case R.id.btn_dependency:
                Glide.with(Main2Activity.this).load(IMAGE_URL).into(iv);

                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvContent.setText(PropertyUtil.getInstance().readCofigFile());

        tvVersion.setText("版本号:" +BuildConfig.VERSION_NAME);
    }


}

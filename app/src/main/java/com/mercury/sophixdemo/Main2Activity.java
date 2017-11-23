package com.mercury.sophixdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @Bind(R.id.tv_content)
    TextView  tvContent;
    @Bind(R.id.btn_dependency)
    Button    btnDependency;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    private static final String IMAGE_URL = "http://img.ivsky.com/img/tupian/li/201708/23/yingyangfengfudeheimeishuiguotupian-005.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        tvContent.setText(PropertyUtil.getInstance().readCofigFile());

    }

    @OnClick({R.id.tv_content, R.id.btn_dependency})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_content:
                ToastUtil.showToast(Main2Activity.this, "新增了资源目录和文件");
                break;
            case R.id.btn_dependency:
                Glide.with(Main2Activity.this).load(IMAGE_URL).into(iv);

                break;
        }
    }
}

package com.mercury.sophixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int param = 1;
    private static int paramsStatic = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("热修复成功了,这是第" + getMulti(param, paramsStatic) + "次");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(MainActivity.this,"成功跳转到埋桩的页面");
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

    }

    private int getCount(int a, int b) {
        return a + b;
    }

    private static int getMulti(int a, int b) {
        return a * b;
    }

}

package com.mercury.sophixdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    }

    private int getCount(int a, int b) {
        return a + b;
    }

    private static int getMulti(int a, int b) {
        return a * b;
    }
}

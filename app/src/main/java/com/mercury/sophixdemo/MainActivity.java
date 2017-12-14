package com.mercury.sophixdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int param               = 1;
    private static int paramsStatic = 2;

    private static final int DURATION = 2000;

    ImageView ivCoin;
    ImageView ivPocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
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
        ivCoin = (ImageView) findViewById(R.id.iv_coin);
        ivPocket = (ImageView) findViewById(R.id.iv_pocket);
        ivPocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCoin.setVisibility(View.VISIBLE);
                startCoin();
                setWallet();
            }
        });
    }

    private void setWallet() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if (animatedFraction >= 0.8) {
                    valueAnimator.cancel();
                    startWallet();
                }
            }
        });
        valueAnimator.start();
    }

    private void startWallet() {
        //x轴缩放
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(ivPocket, "scaleX", 1, 1.1f,
                0.9f, 1);
        objectAnimatorX.setDuration(800);

        //y轴缩放
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(ivPocket, "scaleX", 1, 0.75f,
                1.25f, 1);
        objectAnimatorY.setDuration(800);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());

        //同时执行x,y轴缩放动画
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        animatorSet.start();
    }

    private void startCoin() {
        //掉落
        Animation animationTranslate = AnimationUtils.loadAnimation(this, R.anim.top_in);

        //旋转
        CoinRotateAnimation coinRotateAnimation = new CoinRotateAnimation();
        coinRotateAnimation.setRepeatCount(10);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(DURATION);
        animationSet.addAnimation(coinRotateAnimation);
        animationSet.addAnimation(animationTranslate);

        ivCoin.startAnimation(animationSet);


    }

    private int getCount(int a, int b) {
        return a + b;
    }

    private static int getMulti(int a, int b) {
        return a * b;
    }

}

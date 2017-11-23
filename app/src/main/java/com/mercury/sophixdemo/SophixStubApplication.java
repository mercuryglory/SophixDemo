package com.mercury.sophixdemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}

    private static int count = 0;

    private Context mContext;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
        //         如果需要使用MultiDex，需要在此处调用。
        //         MultiDex.install(this);
        initSophix();

    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(BuildConfig.AppId, BuildConfig.AppSecret, BuildConfig.RSA)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                            saveFixStatus(false);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            if (count <= 0) {
                                Log.i(TAG, "应用已经进入后台");
                                SophixManager.getInstance().killProcessSafely();
                            } else {
                                Log.i(TAG, "应用还处于前台");
                                saveFixStatus(true);
                                //这时候要做标记,下次应用进入后台时应用自杀
                            }
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            Log.i(TAG, "code load fail");
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i(TAG, "Mode:" + mode + "\tCode:" + code + "\tInfo:" + info);
                        }
                    }
                }).initialize();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, this.getClass().getSimpleName());
        SophixManager.getInstance().queryAndLoadNewPatch();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.v(TAG, "onActivityCreated_count:" + count);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                count++;
                Log.v(TAG, "onActivityStarted_count:" + count);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.v(TAG, "onActivityResumed_count:" + count);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.v(TAG, "onActivityPaused_count:" + count);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                Log.v(TAG, "onActivityStopped_count:" + count);
                if (count <= 0 && getFixStatus()) {
                    SophixManager.getInstance().killProcessSafely();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.v(TAG, "onActivitySaveInstanceState_count:" + count);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.v(TAG, "onActivityDestroyed_count:" + count);
            }
        });
    }


    private void saveFixStatus(boolean status) {

        if (getApplicationContext() == null) {
            Log.i("status", "getApplicationContext:" + status);
        }
        if (mContext == null) {
            Log.i("status", "mContext:" + status);
        }
        SharedPreferences sophix = mContext.getSharedPreferences("sophix", Context
                .MODE_PRIVATE);
        SharedPreferences.Editor edit = sophix.edit();
        edit.putBoolean("needRestart", status);
        edit.apply();

    }

    private boolean getFixStatus() {
        SharedPreferences sophix = mContext.getSharedPreferences("sophix", Context
                .MODE_PRIVATE);
        return sophix.getBoolean("needRestart", false);
    }
}
package com.mercury.sophixdemo;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wang.zhonghao on 2017/11/22.
 * description:  新增的类
 */

public class PropertyUtil {

    private static PropertyUtil instance;
    private Properties mProperties;

    public static PropertyUtil getInstance() {
        if (instance == null) {
            synchronized (PropertyUtil.class) {
                if (instance == null) {
                    instance = new PropertyUtil();
                }
            }
        }
        return instance;
    }

    private PropertyUtil() {
        mProperties= new Properties();
        AssetManager assets = MyApplication.context.getAssets();
        try {
            InputStream inputStream = assets.open("config.properties");
//        InputStream inputStream = MyApplication.context.getResources().openRawResource(R.raw
//                .config);
            mProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readCofigFile() {
        return mProperties.getProperty("content", "");

    }
}

package com.mercury.sophixdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Mercury on 2017/11/23.
 */

public interface DataManager {

    int getLayoutId();

    void initData(@Nullable Bundle savedInstanceState);
}

package com.owo.app.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangli on 15-5-23.
 */
public  interface ActivityLifeCycleListener {
    void onCreate(Activity a, Bundle savedInstanceState);

    void onDestroy(Activity a);

    void onPause(Activity a);

    void onResume(Activity a);

    void onSaveInstanceState(Activity a, Bundle outState);

    void onRestoreInstanceState(Activity a, Bundle savedInstanceState);
}

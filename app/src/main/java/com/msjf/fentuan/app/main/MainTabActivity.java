package com.msjf.fentuan.app.main;

import android.content.Intent;
import android.os.Bundle;

import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.owo.app.base.ConfigurableActivityGroup;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;

/**
 * Created by wangli on 15-5-23.
 */
public class MainTabActivity extends ConfigurableActivityGroup {

    private HomePage mHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePage = new HomePage(this, this);
        mHomePage.onCreate(this, savedInstanceState);
        setContentView(mHomePage);
    }

    @Override
    protected void onDestroy() {
        mHomePage.onDestroy(this);
        Singleton.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContextManager.init(this);
        mHomePage.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHomePage.onPause(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mHomePage.onSaveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mHomePage.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


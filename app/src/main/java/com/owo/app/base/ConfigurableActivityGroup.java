package com.owo.app.base;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;

import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;

public class ConfigurableActivityGroup extends ActivityGroup {
    @Override
    protected void onDestroy() {
        mInterceptedContentView = null;
        LoadingCtrl.dismiss(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextManager.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContextManager.init(this);
    }

    private View mInterceptedContentView;

    @Override
    public void setContentView(View view) {
        mInterceptedContentView = view;
        super.setContentView(view);
        onContentViewSetted();
    }

    private void onContentViewSetted() {
        Singleton.of(Language.class).addObserver(new LanguageObserver() {
            @Override
            public void onLanguageChanged() {
                Language.notifyChanged(mInterceptedContentView);
            }
        });
        Singleton.of(Theme.class).addObserver(new ThemeObserver() {

            @Override
            public void onThemeChanged() {
                Theme.notifyChanged(mInterceptedContentView);
            }
        });
    }
}

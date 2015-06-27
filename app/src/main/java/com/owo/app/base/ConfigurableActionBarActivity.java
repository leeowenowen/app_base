package com.owo.app.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.log.Logger;
import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

/**
 * Created by Esimrop on 15/5/23.
 */
public class ConfigurableActionBarActivity extends AppCompatActivity {
    protected TextView tvTitle;
    private int actionBarResId;
    protected View backButton;

    @Override
    protected void onDestroy() {
        mInterceptedContentView = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextManager.init(this);
        initActionBar();
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

    protected void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
            actionBarResId = getActionBarResId();

            actionBar.setCustomView(actionBarResId);
            View actionBarView = actionBar.getCustomView();
            tvTitle = (TextView) actionBarView.findViewById(R.id.tv_actionbar_title);

            backButton = actionBarView.findViewById(R.id.iv_actionBar_back);
            try {
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } catch (Exception e) {
                Logger.e("WARRNING", "This actionbar custom view doesn't have a view which id is iv_actionbar_back");
            }
        }
    }

    protected int getActionBarResId() {
        return R.layout.defult_actionbar;
    }

    protected void setActionBarTitle(String title) {
        try {
            tvTitle.setText(title);
        } catch (Exception e) {
            Logger.e("Error", "This Custom ActionBar doesn't have a TextView which Id is tv_actionbar_title");
        }
    }

    protected void setActionBarBackButtonEnable(boolean enable) {
        try {
            if (enable) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Logger.e("Error", "This Custom ActionBar doesn't have a View which Id is iv_actionbar_back");
        }
    }

    private void onContentViewSetted() {
        Singleton.of(Language.class).addObserver(new LanguageObserver() {
            @Override
            public void onLanguageChanged() {
                onThemeChanged();
                Language.notifyChanged(mInterceptedContentView);
            }
        });
        Singleton.of(Theme.class).addObserver(new ThemeObserver() {

            @Override
            public void onThemeChanged() {
                onLanguageChanged();
                Theme.notifyChanged(mInterceptedContentView);
            }
        });
    }

    protected void onLanguageChanged() {
    }

    protected void onThemeChanged() {
    }
}

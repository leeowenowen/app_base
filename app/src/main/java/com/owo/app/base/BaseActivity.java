package com.owo.app.base;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

/**
 * Created by wangli on 15-5-23.
 */
public class BaseActivity extends ConfigurableActionBarActivity {
    protected View mRightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRightView = (View) getSupportActionBar().getCustomView().findViewById(R.id.v_actionbar_right);
    }

    protected int getActionBarResId() {
        return R.layout.base_actionbar;
    }

    protected void onLanguageChanged() {
    }

    protected void onThemeChanged() {
        TU.setTextSize(32, tvTitle);
        TU.setTextColor(ColorId.main_text_inverse_color);
    }
}

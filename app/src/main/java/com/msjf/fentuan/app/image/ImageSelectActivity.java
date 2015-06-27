package com.msjf.fentuan.app.image;

import android.content.Intent;
import android.os.Bundle;

import com.owo.app.base.ConfigurableActivity;

/**
 * Created by wangli on 15-6-2.
 */
public class ImageSelectActivity extends ConfigurableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isSingleSelect = getIntent().getBooleanExtra("is_single_select", false);
        boolean isWHEqual = getIntent().getBooleanExtra("is_wh_equal", false);
        boolean needCrop = getIntent().getBooleanExtra("need_crop", false);

        setContentView(new ImageSelectPage(this, isWHEqual, isSingleSelect, needCrop));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != 100)
        {
            return;
        }
        setResult(requestCode, data);
        finish();
    }
}

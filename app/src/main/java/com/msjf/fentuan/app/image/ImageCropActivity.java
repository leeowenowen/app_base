package com.msjf.fentuan.app.image;

import android.os.Bundle;

import com.owo.app.base.ConfigurableActivity;

/**
 * Created by wangli on 15-6-2.
 */
public class ImageCropActivity extends ConfigurableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imagePath = getIntent().getStringExtra("image_path");
        boolean isWHEqual = getIntent().getBooleanExtra("is_wh_equal", false);
        int width = getIntent().getIntExtra("width",0);
        int height = getIntent().getIntExtra("height",0);
        setContentView(new ImageCropPage(this, imagePath, isWHEqual, width, height));
    }
}

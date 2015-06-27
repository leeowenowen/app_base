package com.msjf.fentuan.fendouquan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.base.util.DimensionUtil;
import com.owo.base.util.MediaUtil;

import java.util.ArrayList;

public class DouYiDuanActivity extends ConfigurableActivity {
    private DouYiDuanPage mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String type = getIntent().getStringExtra("type");
        mPage = new DouYiDuanPage(this);
        if (type != null) {
            mPage.setPageType(PageType.valueOf(type));
        }
        setContentView(mPage);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100: {// 调用Gallery返回的
                if (data != null) {
                    ArrayList<String> paths = data.getStringArrayListExtra("image_path");
                    mPage.notifyPhotoData(paths);
                }
                break;
            }
        }
    }
}

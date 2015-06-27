package com.msjf.fentuan.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.base.ConfigurableActivity;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.DimensionUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RegisterActivity extends ConfigurableActivity {
    private RegisterView mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mRegisterView = new RegisterView(this);
        setContentView(mRegisterView);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100: {// 调用Gallery返回的
                if (data != null) {
                    ArrayList<String> image_path = data.getStringArrayListExtra("image_path");
                    final int size = DimensionUtil.w(180);
                    ImageLoader.getInstance().loadImage("file://" + image_path.get(0), new ImageSize(size, size), new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            Bitmap bmp = BitmapHelper.createScaledBitmap(loadedImage, size, size);
                            mRegisterView.notifyPhotoData(bmp);
                        }
                    });
                }
                break;
            }
        }
    }
}

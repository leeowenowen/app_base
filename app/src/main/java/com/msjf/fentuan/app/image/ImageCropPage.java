package com.msjf.fentuan.app.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;

import com.edmodo.cropper.CropImageView;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.ui.util.LoadingCtrl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.ContextManager;
import com.owo.base.async.TaskRunner;
import com.owo.base.util.BitmapHelper;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by wangli on 15-6-3.
 */
public class ImageCropPage extends BasePage {
    private Button mBtnCrop;
    private CropImageView mCropImageView;

    public ImageCropPage(Context context, String imagePath, boolean isWHEqual, int width, int height) {
        super(context);

        mBtnCrop = new Button(context);
        mCropImageView = new CropImageView(context);
        if (isWHEqual) {
            mCropImageView.setAspectRatio(1, 1);
        }
        setTitleRightExtension(mBtnCrop);
        mBtnCrop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap bmp = mCropImageView.getCroppedImage();
                LoadingCtrl.start("正在生成头像...");
                TaskRunner.run(new Runnable() {
                    @Override
                    public void run() {
                        final String path = ContextManager.context().getFilesDir().getPath() + UUID.randomUUID();
                        BitmapHelper.saveBitmap(bmp, path);
                        post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> paths = new ArrayList<>();
                                paths.add(path);
                                Intent data = new Intent();
                                data.putStringArrayListExtra("image_path", paths);
                                ContextManager.activity().setResult(100, data);
                                ContextManager.activity().finish();
                            }
                        });
                    }
                });

            }
        });
        setContentView(mCropImageView);
        ImageLoader.getInstance().loadImage(imagePath, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mCropImageView.setImageBitmap(loadedImage);
            }
        });
        mBtnCrop.setText("确定");
    }

    @Override
    public void onLanguageChanged() {
        super.onLanguageChanged();
        setTitle("裁剪图片");
    }
}

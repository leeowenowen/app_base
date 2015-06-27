package com.msjf.fentuan.register;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.msjf.fentuan.R;
import com.owo.app.base.ConfigurableActivity;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class PhotoActivity extends ConfigurableActivity {
    public static final String KEY_CROP = "need_crop";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";
    private boolean mCrop;
    private int mHeight;
    private int mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mCrop = getIntent().getBooleanExtra(KEY_CROP, true);
        if (mCrop) {
            mWidth = getIntent().getIntExtra(KEY_WIDTH, 180);
            mHeight = getIntent().getIntExtra(KEY_HEIGHT, 180);
        }
        setContentView(new PhotoWidget(this));
    }

    private class PhotoWidget extends FrameLayout implements ThemeObserver {
        private FrameLayout mContainer;
        private PhotoUploadListView mListView;

        public PhotoWidget(Context context) {
            super(context);
            mListView = new PhotoUploadListView(context);
            mContainer = new FrameLayout(context);
            mContainer.addView(mListView, LP.FMM);

            FrameLayout.LayoutParams lParams = new FrameLayout.LayoutParams(DimensionUtil.w(620),
                    DimensionUtil.h(300), Gravity.CENTER);
            addView(mContainer, lParams);
            onThemeChanged();

            mListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            // take photo
                            String status = Environment.getExternalStorageState();
                            if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
                                doTakePhoto();// 用户点击了从照相机获取
                            } else {
                                Toast.makeText(PhotoActivity.this, "没有SD卡!", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 1:
                            // select file from file system
                            doPickPhotoFromGallery();
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onThemeChanged() {
            mContainer.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.popup_bg));
        }
    }

    /* 用来标识请求照相功能的activity */
    public static final int CAMERA_WITH_DATA = 3023;

    /* 用来标识请求gallery的activity */
    public static final int PHOTO_PICKED_WITH_DATA = 3021;

    public static final int PHOTO_CROPED_WITH_DATA = 3022;

    /* 拍照的照片存储位置 */
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory()
            + "/DCIM/Camera");

    private static File mCurrentPhotoFile;// 照相机拍照得到的图片

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(PhotoActivity.this, "启动系统拍照程序失败!", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    // 请求Gallery程序
    @SuppressLint("InlinedApi")
    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT < 19)
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }
            else {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

            }
            if (mCrop) {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);

                intent.putExtra("crop", "true");
//                intent.putExtra("outputX", DimensionUtil.w(mWidth));
//                intent.putExtra("outputY", DimensionUtil.w(mHeight));
            }
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(PhotoActivity.this, "打开相册失败!", Toast.LENGTH_LONG).show();
        }
    }

    private void doCropPhoto(File f) {
        Uri uri = Uri.fromFile(f);
        doCropPhoto(uri);
    }

    public static String tempPhotoPath() {
        return Environment.getExternalStorageDirectory() + File.separator
                + "msjf_fen_tuan_photo_tmp";
    }

    private void doCropPhoto(Uri uri) {
        try {
            // 启动gallery去剪辑这个照片
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
//            intent.putExtra("outputX", DimensionUtil.w(mWidth));
//            intent.putExtra("outputY", DimensionUtil.w(mHeight));
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPhotoPath())));
            // intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true); // no face detection
            //
            startActivityForResult(intent, PHOTO_CROPED_WITH_DATA);
        } catch (Exception e) {
            Toast.makeText(PhotoActivity.this, "打开系统剪切图片程序失败!", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: {// 调用Gallery返回的
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(PhotoActivity.this, uri);
                    File f = new File(path);
                    if (mCrop) {
                        doCropPhoto(f);
                    } else {
                        //Bitmap bmp = MediaUtil.createImageThumbnail(path, DimensionUtil.w(300), 0, null, Bitmap.Config.ARGB_8888);
                        data.putExtra("data", path);
                        setResult(resultCode, data);
                        finish();
                    }
                } else {
                    setResult(resultCode, data);
                    finish();
                }
                break;
            }
            case PHOTO_CROPED_WITH_DATA:
                if (data != null) {
                    //Bitmap bmp = MediaUtil.createImageThumbnail(tempPhotoPath(), DimensionUtil.w(300), 0, null, null);
                    //Bitmap bmp = BitmapFactory.decodeFile(tempPhotoPath());
                    data.putExtra("data", tempPhotoPath());
                    setResult(resultCode, data);
                }
                finish();
                break;
            case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                if (mCrop) {
                    doCropPhoto(mCurrentPhotoFile);
                } else {
                    //Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoFile.getPath());
                    data.putExtra("data", mCurrentPhotoFile.getPath());
                    setResult(resultCode, data);
                    finish();
                }
                break;
            }
        }

    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}

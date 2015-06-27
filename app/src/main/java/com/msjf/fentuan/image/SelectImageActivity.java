package com.msjf.fentuan.image;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.msjf.fentuan.R;
import com.owo.app.base.ConfigurableActionBarActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Esimrop on 15/5/24.
 */
public class SelectImageActivity extends ConfigurableActionBarActivity implements AdapterView.OnItemClickListener {
    private Picasso picasso;
    private GridView gridView;
    private ArrayList<ImageInfo> imageInfoList;
    private ImageInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectimage);

        imageInfoList = new ArrayList<>();
        picasso = Picasso.with(this);

        initView();
        loadImageInfo();
        setUpData();
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
    }

    private void loadImageInfo() {
        Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            imageInfoList.add(new ImageInfo("file://" + path, false));
            cursor.moveToNext();
        }

    }

    private void setUpData() {
        adapter = new ImageInfoAdapter();
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (ImageInfo info : imageInfoList) {
            info.setselect(false);
        }

        imageInfoList.get(position).setselect(true);
        adapter.notifyDataSetChanged();
    }

    class ImageInfo {
        private String path;

        private boolean select;

        public ImageInfo(String path, boolean isSelect) {
            this.path = path;
            this.select = isSelect;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSelect() {
            return select;
        }

        public void setselect(boolean isSelect) {
            this.select = isSelect;
        }
    }

    class ImageInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageHolder imageHolder = null;
            if (null != convertView) {
                imageHolder = (ImageHolder) convertView.getTag();
            } else {
                convertView = View.inflate(SelectImageActivity.this, R.layout.item_select_image, null);
                convertView.setLayoutParams(new AbsListView.LayoutParams((int) (parent.getWidth() / 3) - 1, (int) (parent.getWidth() / 3)));// 动态设置item的高度
                imageHolder = new ImageHolder();
                imageHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);

                convertView.setTag(imageHolder);
            }

            String path = imageInfoList.get(position).path;
            Log.e("path", path);
            picasso.load(path).resize(100, 100).centerCrop().into(imageHolder.imageView);
            return convertView;
        }
    }

    class ImageHolder {
        public ImageView imageView;
    }
}

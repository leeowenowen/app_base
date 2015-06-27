package com.msjf.fentuan.crowdfunding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.owo.app.base.ConfigurableActionBarActivity;

/**
 * Created by Esimrop on 15/5/23.
 */
public class ListCrowdfundingActivity extends ConfigurableActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crowdfunding_list);

        initView();
        setUpData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CrowdfundingListAdapter());
        listView.setOnItemClickListener(this);

        ImageView createCrowdfundingButton = (ImageView) findViewById(R.id.iv_crowdfunding_show_dialog);
        createCrowdfundingButton.setOnClickListener(this);
    }

    private void setUpData() {
        setActionBarTitle("亲密众筹");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CrowdfundingInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_crowdfunding_show_dialog:
                showCreateCrowdfundingDialog();
                break;
        }
    }

    private void showCreateCrowdfundingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_crowdfunding_create);
        builder.create().show();
    }

    class CrowdfundingListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CrowdfundingItemHolder holder = null;

            if (null != convertView) {
                holder = (CrowdfundingItemHolder) convertView.getTag();
            } else {
                convertView = View.inflate(ListCrowdfundingActivity.this, R.layout.item_crowdfunding, null);
                holder = new CrowdfundingItemHolder();
                convertView.setTag(holder);

                holder.icon = (ImageView) convertView.findViewById(R.id.iv_crowdfunding_item_icon);
                holder.starName = (TextView) convertView.findViewById(R.id.tv_crowdfunding_item_star_name);
                holder.productName = (TextView) convertView.findViewById(R.id.tv_crowdfunding_item_product_name);
                holder.productReMarks = (TextView) convertView.findViewById(R.id.tv_crowdfunding_item_product_remarks);
                holder.productMinPrice = (TextView) convertView.findViewById(R.id.tv_crowdfunding_item_product_min_price);
                holder.productFinishPercent = (TextView) convertView.findViewById(R.id.tv_crowdfunding_item_product_finish_percent);
            }

            //TODO setUpData

            return convertView;
        }
    }

    class CrowdfundingItemHolder {
        ImageView icon;
        TextView starName;
        TextView productName;
        TextView productReMarks;
        TextView productMinPrice;
        TextView productFinishPercent;
    }
}

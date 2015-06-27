package com.msjf.fentuan.crowdfunding;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msjf.fentuan.R;
import com.owo.app.base.ConfigurableActionBarActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Esimrop on 15/5/23.
 */
public class CrowdfundingInfoActivity extends ConfigurableActionBarActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crowdfunding_info);
        initView();
        setUpdata();
    }

    @Override
    protected int getActionBarResId() {
        return R.layout.actionbar_crowdfunding_info;
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CrowdfundingViewPagerAdapter());

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.viewpagerIndicator);
        indicator.setViewPager(viewPager);

    }

    private void setUpdata() {
        setActionBarTitle("项目详情");
    }

    class CrowdfundingViewPagerAdapter extends PagerAdapter {
        private List<ImageView> viewLists;

        public CrowdfundingViewPagerAdapter() {
            viewLists = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ImageView iv = new ImageView(CrowdfundingInfoActivity.this);
                ViewPager.LayoutParams params = new ViewPager.LayoutParams();
                params.width = ViewPager.LayoutParams.MATCH_PARENT;
                params.height = ViewPager.LayoutParams.MATCH_PARENT;
                iv.setLayoutParams(params);
                iv.setImageResource(R.drawable.welcome_text_logo);
                viewLists.add(iv);
            }
        }

        @Override

        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(viewLists.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(viewLists.get(position), 0);
            return viewLists.get(position);
        }
    }
}

//package com.msjf.fentuan.paidup;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.RadioGroup;
//
//import com.msjf.fentuan.R;
//import com.msjf.fentuan.paidup.Fragment.MyFragment;
//
//
//public class PaidBaoTuanHomeActivity extends FragmentActivity {
//    private RadioGroup rg_change;
//    private FrameLayout fm_change;
//    private Fragment myFragment;
//    private Fragment nearFragment;
//    private  FragmentTransaction ft = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_paid_bao_tuan_home);
//        initView();
//        initListenner();
//        rg_change.check(R.id.near_baotuan);
//
//
//    }
//
//
//    private void initListenner() {
//        rg_change.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId) {
//                    case R.id.near_baotuan:
//                        nearFragment = new MyFragment(getApplicationContext());
//                        changeFragment(nearFragment,"NearFragment");
//                        break;
//                    case R.id.my_baotuan:
//                        myFragment = new MyFragment(getApplicationContext());
//                        changeFragment(myFragment,"MyFragment");
//                        break;
//                }
//                if (ft != null)
//                    ft.commit();
//            }
//        });
//
//    }
//
//    public void changeFragment(Fragment  fragment,String tag) {
//
//        ft = getSupportFragmentManager().beginTransaction().replace(R.id.fm_change, fragment, tag);
//
//    }
//
//    /**
//     * ????????
//     */
//    private void initView() {
//        rg_change = (RadioGroup) findViewById(R.id.rg_change);
//        fm_change = (FrameLayout) findViewById(R.id.fm_change);
//
//    }
//
//
//    /**
//     * ??????????????????
//     *
//     * @param v
//     */
//    public void back(View v) {
//        finish();
//    }
//
//
//}

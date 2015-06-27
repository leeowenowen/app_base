//package com.msjf.fentuan.paidup;
//
//import android.app.Activity;
//
//import android.app.Dialog;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//
//import com.msjf.fentuan.R;
//import com.msjf.fentuan.paidup.bean.User;
//
//import org.w3c.dom.Text;
//
///**
// * Created by 春晓 on 2015/6/9
// * 抱团详情Activity
// */
//public class BaoTuanDetails extends Activity implements View.OnClickListener {
//    private TextView im_baotuan;
//    private ImageView back;
//    private Button btn_cancle;
//    private Button btn_confirm;
//    private View viewDialog;
//    private Dialog dialog;
//    private  ImageView user_head;
//    private  TextView team_num;
//    private  ImageView release_message;
//    private  ImageView zan;
//    private  TextView team_total_num;
//    private  TextView tv_content;
//    private  TextView creat_time;
//    private  TextView show_more;
//    private  TextView tv_place;
//    private Intent intent;
//    private User user;
//    private  GridView gv_head;
//    private TextView launch_baotuan;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_baotuan_details);
//        intent = getIntent();
//        user = (User) intent.getSerializableExtra("User");
//        initView();
//        initListenner();
//        initData();
//    }
//
//    private void initData() {
//        user_head.setImageResource(user.getUserHead());
//        team_num.setText("已抱团" + 0 + "人");
//        team_total_num.setText("人数:"+user.getTeamNum());
//        tv_content.setText("内容：");
//        creat_time.setText("时间："+user.getCreatDataDetails());
//        tv_place.setText("地点："+user.getAddress());
//    }
//
//    private void initListenner() {
//        launch_baotuan.setOnClickListener(this);
//        back.setOnClickListener(this);
//        im_baotuan.setOnClickListener(this);
//
//    }
//
//    private void initView() {
//       launch_baotuan = (TextView) findViewById(R.id.launch_baotuan);
//        gv_head = (GridView)findViewById(R.id.gv_head);
//        im_baotuan = (TextView) findViewById(R.id.im_baotuan);
//        back = (ImageView) findViewById(R.id.back);
//        user_head = (ImageView) findViewById(R.id.user_head);
//        team_num = (TextView) findViewById(R.id.team_num);
//        team_total_num = (TextView) findViewById(R.id.team_total_num);
//        tv_content = (TextView) findViewById(R.id.tv_content);
//        creat_time = (TextView) findViewById(R.id.creat_time);
//        tv_place = (TextView) findViewById(R.id.tv_place);
//        show_more = (TextView) findViewById(R.id.show_more);
//        zan = (ImageView) findViewById(R.id.zan);
//        release_message = (ImageView) findViewById(R.id.release_message);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back:
//                finish();
//                break;
//            case R.id.im_baotuan:
//                dialog = new Dialog(this);
//                dialog.show();
//                initDialogView();
//                initDialogListenner();
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                dialog.setContentView(viewDialog, params);
//                break;
//            case R.id.btn_cancle:
//                dialog.dismiss();
//                break;
//            case R.id.zan:
//                //TODO 点赞操作
//                break;
//            case R.id.release_message:
//                //TODO 发送新评论
//                break;
//            case R.id.show_more:
//                //TODO 显示更多操作
//                break;
//            case R.id.launch_baotuan:
//                startActivity(new Intent(this,PaidUpActivity.class));
//                break;
//            case R.id.btn_confirm:
//                startActivity(new Intent(this,PayPageActivity.class));
//                break;
//
//
//        }
//    }
//
//    /**
//     * 支付相关逻辑
//     */
//    private void Methoud_Pay() {
//
//    }
//
//    /**
//     * 初始化diaolog的View
//     */
//    private void initDialogView() {
//        viewDialog = View.inflate(this, R.layout.dialog_pay_page, null);
//        btn_cancle = (Button) viewDialog.findViewById(R.id.btn_cancle);
//        btn_confirm = (Button) viewDialog.findViewById(R.id.btn_confirm);
//    }
//
//    /**
//     * 初始化diaolog的点击事件
//     */
//    private void initDialogListenner() {
//        btn_cancle.setOnClickListener(this);
//        btn_confirm.setOnClickListener(this);
//    }
//}

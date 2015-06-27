//package com.msjf.fentuan.paidup;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.msjf.fentuan.R;
//import com.msjf.fentuan.paidup.bean.User;
//
//
//public class PaidUpActivity extends Activity implements View.OnClickListener {
//    private EditText team_name;
//    private EditText head_mumber;
//    private EditText team_mumbers;
//    private EditText creat_time;
//    private EditText team_place;
//    private EditText team_pace;
//    private TextView submit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_paid_up);
//        initView();
//        initListenner();
//    }
//
//    private void initListenner() {
//        submit.setOnClickListener(this);
//
//    }
//
//    private void initView() {
//
//        team_name = (EditText) findViewById(R.id.team_name);
//        team_mumbers = (EditText) findViewById(R.id.team_mumbers);
//        creat_time = (EditText) findViewById(R.id.creat_time);
//        team_place = (EditText) findViewById(R.id.team_place);
//        team_pace = (EditText) findViewById(R.id.team_pace);
//        head_mumber = (EditText) findViewById(R.id.head_mumber);
//        submit = (TextView) findViewById(R.id.submit);
//
//
//    }
//
//
//    public void back(View v) {
//        finish();
//    }
//
//    @Override
//    public void onClick(View v) {
//        String text = "提交成功";
//        int count = 0 ;
//        switch (v.getId()) {
//            case R.id.submit:
//                boolean flag = TextUtils.isEmpty(team_name.getText())&&TextUtils.isEmpty(team_mumbers.getText())&&TextUtils.isEmpty(creat_time.getText())&&TextUtils.isEmpty(team_place.getText())&&TextUtils.isEmpty(team_pace.getText())&&TextUtils.isEmpty(head_mumber.getText());
//
//               if(!flag){
//                   /*User user = new User();
//                   user.setCreatDataDetails();*/
//                  startActivity(new Intent(this, PaidBaoTuanHomeActivity.class));
//                   finish();
//               }else{
//
//                   if (TextUtils.isEmpty(team_name.getText()))
//                       count++;
//                       text = "抱团名称不能为空";
//                   if (TextUtils.isEmpty(team_mumbers.getText()))
//                       count++;
//                       text = "抱团成员不能为空";
//                   if (TextUtils.isEmpty(creat_time.getText()))
//                       count++;
//                       text = "创建时间不能为空";
//                   if (TextUtils.isEmpty(team_place.getText()))
//                       count++;
//                       text = "抱团地点不能为空";
//                   if (TextUtils.isEmpty(team_pace.getText()))
//                       count++;
//                       text = "抱团费用不能为空";
//                   if (TextUtils.isEmpty(head_mumber.getText()))
//                       count++;
//                       text = "团长人数不能为空";
//                   if(count ==6)
//                       text = "请输入内容";
//
//               }
//
//                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//
//}

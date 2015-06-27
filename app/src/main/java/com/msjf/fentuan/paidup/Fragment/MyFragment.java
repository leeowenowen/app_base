//package com.msjf.fentuan.paidup.Fragment;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.Image;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//
//import com.msjf.fentuan.R;
//import com.msjf.fentuan.paidup.BaoTuanDetails;
//import com.msjf.fentuan.paidup.PaidBaoTuanHomeActivity;
//import com.msjf.fentuan.paidup.bean.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 春晓 on 2015/6/9.
// */
//public class MyFragment extends Fragment implements AdapterView.OnItemClickListener {
//    private Context context;
//    private ListView lsitView;
//    private View view;
//    private List<User> userList;
//    private List<User> positionList;
//
//    public MyFragment() {
//        super();
//    }
//
//    public MyFragment(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * 返回列表对象以显示
//     *
//     * @param inflater
//     * @param container
//     * @param savedInstanceState
//     * @return
//     */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        initView();
//        initData();
//        lsitView.setAdapter(adapter);
//        initListenner();
//        return view;
//    }
//
//    private void initListenner() {
//
//        lsitView.setOnItemClickListener(this);
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        String tag = getTag();
//        if ("NearFragment".equals(tag)) {
//            //TODO 获取附近抱团信息
//            userList = new ArrayList<User>();
//            User user = new User();
//            user.setAddress("ddddd");
//            user.setCreatData("2012-09-09");
//            user.setTeamNum(300 + "人");
//            user.setUserName("小明");
//            user.setUserType("学生团");
//            user.setCreatDataDetails("2012-09-09");
//            user.setUserHead(R.drawable.crowdfunding_publish);
//            userList.add(user);
//            userList.add(user);
//            userList.add(user);
//        } else {
//            //TODO 获取我的抱团信息
//            userList = new ArrayList<User>();
//            User user = new User();
//            user.setAddress("dddd");
//            user.setCreatData("20222-09-09");
//            user.setTeamNum(300 + "人");
//            user.setUserName("小明");
//            user.setUserType("学生团");
//            user.setCreatDataDetails("2012-09-09");
//            user.setUserHead(R.drawable.crowdfunding_publish);
//            userList.add(user);
//            userList.add(user);
//            userList.add(user);
//        }
//
//
//    }
//
//    /**
//     * 初始化控件
//     *
//     * @return
//     */
//    private View initView() {
//        view = View.inflate(context, R.layout.list_near_baotuan, null);
//        lsitView = (ListView) view.findViewById(R.id.lv_baotuan);
//        return view;
//    }
//
//    BaseAdapter adapter = new BaseAdapter() {
//        @Override
//        public int getCount() {
//
//            return userList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//
//            return userList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = View.inflate(getActivity(), R.layout.item_near_baotuan_list, null);
//                holder.item_patner_head = (ImageView) convertView.findViewById(R.id.item_patner_head);
//                holder.item_patner_name = (TextView) convertView.findViewById(R.id.item_patner_name);
//                holder.item_patner_type = (TextView) convertView.findViewById(R.id.item_patner_type);
//                holder.item_creat_time = (TextView) convertView.findViewById(R.id.item_creat_time);
//                holder.item_creat_time_details = (TextView) convertView.findViewById(R.id.item_creat_time_details);
//                holder.item_team_num = (TextView) convertView.findViewById(R.id.item_team_num);
//                holder.item_team_place = (TextView) convertView.findViewById(R.id.item_team_place);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            User user = userList.get(position);
//
//            positionList = new ArrayList<User>();
//            positionList.add(user);
//            holder.item_patner_head.setImageResource(R.drawable.crowdfunding_publish);
//            holder.item_patner_name.setText(user.getUserName());
//            holder.item_patner_type.setText(user.getUserType());
//            holder.item_creat_time.setText(user.getCreatData());
//            holder.item_creat_time_details.setText(user.getCreatDataDetails());
//            holder.item_team_num.setText(user.getTeamNum());
//            holder.item_team_place.setText(user.getAddress());
//            return convertView;
//        }
//    };
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(context, BaoTuanDetails.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("User",userList.get(position));
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//
//
//    class ViewHolder {
//        ImageView item_patner_head;
//        TextView item_patner_name;
//        TextView item_creat_time;
//        TextView item_patner_type;
//        TextView item_creat_time_details;
//        TextView item_team_num;
//        TextView item_team_place;
//    }
//}

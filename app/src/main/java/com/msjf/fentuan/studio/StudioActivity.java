package com.msjf.fentuan.studio;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.image.SelectImageActivity;
//import com.msjf.fentuan.paidup.PaidBaoTuanHomeActivity;
import com.owo.app.base.ConfigurableActionBarActivity;

public class StudioActivity extends ConfigurableActionBarActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView addOrSendButton;
    private ViewGroup addLayout;
    private EditText edStudioChatMessage;
    private ImageView enter_baotuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studio);

        initView();
        setUpData();
    }

    private void initView() {

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new StudioChatAdapter());
        enter_baotuan = (ImageView) findViewById(R.id.enter_baotuan);
        enter_baotuan.setOnClickListener(this);
        addOrSendButton = (ImageView) findViewById(R.id.iv_studio_add_or_send);
        addOrSendButton.setOnClickListener(this);

        addLayout = (ViewGroup) findViewById(R.id.rl_add);

        ViewGroup rlAddImage = (ViewGroup) findViewById(R.id.rl_add_image);
        rlAddImage.setOnClickListener(this);

        edStudioChatMessage = (EditText) findViewById(R.id.edit_studio_message);
        edStudioChatMessage.addTextChangedListener(new StudioMessageTextWatcher());
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            View actionBarView = actionBar.getCustomView();
            TextView rightButton = (TextView) actionBarView.findViewById(R.id.tv_studio_right_button);
            rightButton.setOnClickListener(this);
        }
    }

    @Override
    protected int getActionBarResId() {
        return R.layout.actionbar_studio;
    }

    private void setUpData() {
        //TODO 获取数据并展示到界面

        setActionBarTitle("星话直播间");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionBar_back:
                finish();
                break;

            case R.id.iv_studio_add_or_send:
                addOrSend();
                break;

            case R.id.rl_add_image:
                addImage();
                break;

            case R.id.tv_studio_right_button:
                onActionBarRightButtonClick();
                break;
            case R.id.enter_baotuan:
            //    startActivity(new Intent(this, PaidBaoTuanHomeActivity.class));
                break;
        }
    }


    private void onActionBarRightButtonClick() {
        //TODO 检查权限，发表今日话题
        boolean canPublish = true;
        showPublicToDayTopicDialog();
    }

    private void showPublicToDayTopicDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(R.layout.dialog_studio_add_topic);
        dialog.create().show();
    }

    private void addImage() {
        Intent i = new Intent(this, SelectImageActivity.class);
        startActivity(i);
    }

    private void addOrSend() {
        String message = edStudioChatMessage.getText().toString();
        if (TextUtils.isEmpty(message)) {
            if (addLayout.getVisibility() == View.VISIBLE) {
                //TODO 隐藏addLayout,切换按钮图片
                addLayout.setVisibility(View.GONE);
                addOrSendButton.setImageResource(R.drawable.studio_add_image);
            } else {
                //TODO 显示addLayout,切换按钮图片
                addLayout.setVisibility(View.VISIBLE);
                addOrSendButton.setImageResource(R.drawable.studio_keyboard);
            }
        } else {
            sendMessage(message);
        }
    }

    private void sendMessage(String message) {

    }

    private class StudioChatAdapter extends BaseAdapter {

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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return View.inflate(StudioActivity.this, R.layout.item_studio_chat_message, null);
        }
    }

    private class StudioMessageTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                addOrSendButton.setImageResource(R.drawable.studio_add_image);
            } else {
                addOrSendButton.setImageResource(R.drawable.studio_send);
            }
        }
    }
    //	private void makeStudioData() {
//		StudioData studioData = Singleton.of(StudioData.class);
//		Theme theme = Singleton.of(Theme.class);
//		studioData.mHasHost = true;
//		Bitmap bmp = theme.bitmap(BitmapId.me_default_photo);
//		studioData.mHostPhoto = bmp;
//		studioData.mStarPhoto = bmp;
//		studioData.mTopic = "今天我是直播间的主持人，咱们聊聊邵峰的颜值，大家鼓掌";
//
//		UserData userData = Singleton.of(UserData.class);
//		for (int i = 0; i < 10; ++i) {
//			ChatItem item = new ChatItem();
//			String id = "" + i;
//			item.mUserId = id;
//			item.mContent = "我说的话　" + i;
//			studioData.add(item);
//			User user = new User();
//			user.id = id;
//			user.avatar = bmp;
//			userData.put(id, user);
//		}
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//		makeStudioData();
//
//		StudioPage page = new StudioPage(this);
//		page.setData(Singleton.of(StudioData.class));
//		setContentView(page);
//	}
//	protected void onResume() {
//	    Log.i("OneOneChatActivity", "onResume");
//		super.onResume();
//
//		DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
//        sdkHelper.pushActivity(this);
//		// register the event listener when enter the foreground
//        EMChatManager.getInstance().registerEventListener(mListener,new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage                                                                                  ,EMNotifierEvent.Event.EventReadAck});
//	}
//
//	@Override
//	protected void onPause(){
//	    Log.i("OneOneChatActivity", "onPause");
//
//	    // unregister this event listener when this activity enters the background
//	    EMChatManager.getInstance().unregisterEventListener(mListener);
//
//	    DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
//
//	    // 把此activity 从foreground activity 列表里移除
//        sdkHelper.popActivity(this);
//
//	    super.onStop();
//	}
//
//	private EMEventListener mListener = new EMEventListener() {
//		@Override
//		public void onEvent(EMNotifierEvent arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	};
}

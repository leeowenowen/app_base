package com.msjf.fentuan.studio;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.msjf.fentuan.R;
import com.msjf.fentuan.app.main.MainChatView;
import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.studio.StudioData.ChatItem;
import com.msjf.fentuan.ui.ChatViewBase.Delegate;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.pattern.Singleton;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.ListViewBase;
import com.owo.ui.utils.LP;

public class StudioPage extends BasePage {

	private ImageView mHostPhoto;
	private ImageView mMicroPhone;
	private ImageView mStartPhoto;
	private TextView mTopic;
	private TextView mZuoHost;
	private ImageView mRightTitle;

	private FrameLayout mTopicContainer;
	private LinearLayout mHostLayout;
	private LinearLayout mNoHostLayout;
	private ListViewBase mChatListView;
	private MainChatView mChatView;

	private ChatListAdapter mChatListAdapter = new ChatListAdapter();

	public StudioPage(Context context) {
		super(context, false);
	}

	protected void initComponents(Context context) {
		super.initComponents(context);
		mHostPhoto = new ImageView(context);
		mMicroPhone = new ImageView(context);
		mStartPhoto = new ImageView(context);
		mTopic = new TextView(context);
		mZuoHost = new TextView(context);
		mChatView = new MainChatView(context);
		mRightTitle = new ImageView(context);

		LinearLayout hostCenterLayout = new LinearLayout(context);
		hostCenterLayout.setOrientation(VERTICAL);
		hostCenterLayout.addView(mTopic, LP.LWW());
		hostCenterLayout.addView(mMicroPhone, LP.LWW());
		LU.setMargin(mMicroPhone, 0, 10, 0, 0);

		mHostLayout = new LinearLayout(context);
		mHostLayout.setGravity(Gravity.CENTER_VERTICAL);
		mHostLayout.addView(mHostPhoto);
		mHostLayout.addView(hostCenterLayout, LP.L0W1);
		mHostLayout.addView(mStartPhoto);

		mNoHostLayout = new LinearLayout(context);
		mNoHostLayout.addView(mZuoHost);

		mTopicContainer = new FrameLayout(context);
		mTopicContainer.addView(mHostLayout);
		mTopicContainer.addView(mNoHostLayout);

		mChatListView = new ListViewBase(context);
		mChatListView.setDivider(null);
		mChatListView.setAdapter(mChatListAdapter);

		LinearLayout contentView = new LinearLayout(context);
		contentView.setOrientation(VERTICAL);
		contentView.addView(mTopicContainer);
		contentView.addView(mChatListView, LP.LM01);
		contentView.addView(mChatView);
		setContentView(contentView);
		setTitleRightExtension(mRightTitle);
		mTopic.setFocusable(true);
		mTopic.setFocusableInTouchMode(true);
	}

	protected void setupListeners() {
		super.setupListeners();

		Singleton.of(StudioData.class).addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object data) {
				mChatListAdapter.notifyDataSetChanged();
			}
		});
		mChatView.setDelegate(new Delegate() {
			@Override
			public void onSendText(String text) {
				StudioData data = Singleton.of(StudioData.class);
				ChatItem item = new ChatItem();
				item.mContent = text;
				item.mUserId = "0";
				data.add(item);
			}

			@Override
			public void onSelectFace() {
				Toast.makeText(getContext(), "select face", Toast.LENGTH_LONG).show();
			}
		});

		mRightTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
				TopicSendView topicSendView = new TopicSendView(getContext());
				topicSendView.setDelegate(new Delegate() {

					@Override
					public void onSendText(String text) {
					}

					@Override
					public void onSelectFace() {

					}
				});
				FrameLayout layout = new FrameLayout(getContext());
				layout.addView(topicSendView);
				LU.setMargin(topicSendView, 20, 20, 20, 20);
				dialog.setContentView(layout);
				dialog.show();
			}
		});
	}

	public void setData(StudioData data) {
		mHostPhoto.setImageBitmap(data.mHostPhoto);
		mStartPhoto.setImageBitmap(data.mStarPhoto);
		mTopic.setText(data.mTopic);
		mChatListAdapter.updateData(data.chatList());
	}

	@Override
	public void onLanguageChanged() {
		super.onLanguageChanged();
		setTitle("星话直播间－冯绍峰");
		mTopic.setText("今天我是直播间的主持人，咱们聊聊邵峰的颜值，大家鼓掌!");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onThemeChanged() {
		super.onThemeChanged();

		Theme theme = Singleton.of(Theme.class);
		mMicroPhone.setImageBitmap(theme.bitmap(BitmapId.studio_mic_phone));
		mRightTitle.setImageBitmap(theme.bitmap(BitmapId.studio_host_topic));
		mTopicContainer.setBackgroundColor(theme.color(ColorId.studio_top_layout_bg));
		setBackgroundDrawable(new BitmapDrawable(theme.bitmap(BitmapId.studio_bg)));
		TU.setTextSize(27, mTopic);
		TU.setTextColor(ColorId.main_text_color, mTopic);
	}

	private class ChatListAdapter extends BaseAdapter {

		private List<ChatItem> mChatList;

		public void updateData(List<ChatItem> chatList) {
			mChatList = chatList;
			mChatListView.setAdapter(this);
		}

		@Override
		public int getCount() {
			return mChatList == null ? 0 : mChatList.size();
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
			ChatItemView view = null;
			if (convertView == null) {
				view = new ChatItemView(getContext());
			} else {
				view = (ChatItemView) convertView;
			}
			view.setData(mChatList.get(position));
			Random random = new Random(System.currentTimeMillis());
			int me = random.nextInt() % 2;
			view.setMe(me == 1);
			return view;
		}

		@SuppressWarnings("deprecation")
		private class ChatItemView extends LinearLayout {
			private ImageView mPhoto;
			private TextView mContent;

			public void setMe(boolean left) {
				int id = (left ? R.drawable.studio_bubble_left : R.drawable.studio_bubble_right);
				mContent.setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(id));
				removeAllViews();
				if (left) {
					addView(mPhoto);
					addView(mContent, LP.L0W1);
					LU.setMargin(mContent, 10, 0, 0, 0);
				} else {
					addView(mContent, LP.L0W1);
					addView(mPhoto);
				}
				LU.setPadding(this, 20, 0, 20, 0);
			}

			public ChatItemView(Context context) {
				super(context);

				mPhoto = new ImageView(context);
				mContent = new TextView(context);
				setGravity(Gravity.CENTER_VERTICAL);
				addView(mPhoto);
				addView(mContent);
			}

			public void setData(ChatItem item) {
				UserData userData = Singleton.of(UserData.class);
				User user = userData.get(item.mUserId);
				Bitmap photo = null;
				if (user == null) {
					photo = null; // should set default bitmap
				} else {
					photo = user.getPhoto().getBmp();
				}

				mPhoto.setImageBitmap(photo);
				mContent.setText(item.mContent);
				TU.setTextColor(ColorId.main_text_color, mContent);
				TU.setTextSize(27, mContent);
			}
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
}

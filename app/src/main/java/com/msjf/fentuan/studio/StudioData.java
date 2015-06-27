package com.msjf.fentuan.studio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.graphics.Bitmap;

public class StudioData extends Observable{
	public boolean mHasHost;
	public String mTopic;
	public Bitmap mHostPhoto;
	public Bitmap mStarPhoto;
	private List<ChatItem> mChatList = new ArrayList<ChatItem>();

	private StudioData() {
	}

	public static class ChatItem {
		public String mUserId;
		public String mContent;
		public String mFaceId;
	}

	public void add(ChatItem chatItem) {
		mChatList.add(chatItem);
		setChanged();
		notifyObservers();
	}

	public List<ChatItem> chatList() {
		return mChatList;
	}
}

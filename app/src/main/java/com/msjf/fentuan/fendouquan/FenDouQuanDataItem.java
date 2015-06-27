package com.msjf.fentuan.fendouquan;

import java.util.List;

import android.graphics.Bitmap;

public class FenDouQuanDataItem {
	public String mUserId;
	public String mUserName;
	public String mUserShenFen;
	public Bitmap mUserPhoto;
	public String mTitle;
	public List<Bitmap> mPhotos;
	public long mTime;
	public int mZanNum;
	public int mEyeNum;
	public List<CommentItem> mComments;

	public static class CommentItem {
		//TODO: query user info from UserData with userId
		public String mUserId;
		public String mUserName;
		public String mComment;
	}
}

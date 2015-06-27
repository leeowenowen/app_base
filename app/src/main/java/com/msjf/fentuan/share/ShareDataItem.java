package com.msjf.fentuan.share;

import com.owo.app.theme.BitmapId;

public class ShareDataItem {
	public String mText;
	public BitmapId mBmpId;
	public ShareType mType;
	public ShareDataItem(String text,//
			BitmapId bmpId,//
			ShareType type)
	{
		mText = text;
		mBmpId = bmpId;
		mType = type;
	}
}

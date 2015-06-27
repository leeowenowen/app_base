package com.msjf.fentuan.user;

public enum RegisterSource {
	Default,//
	QQ,//
	WeiXin,//
	WeiBo;//
	
	public static RegisterSource fromIndex(int index)
	{
		switch(index)
		{
		case 0:
			return Default;
		case 1:
			return QQ;
		case 2:
			return WeiXin;
		case 3:
			return WeiBo;
			default:break;
		}
		return null;
	}
}

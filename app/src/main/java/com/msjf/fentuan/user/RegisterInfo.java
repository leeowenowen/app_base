package com.msjf.fentuan.user;

public class RegisterInfo {
	private String mValidateCode;
	private String mValidateCodeSequenceNum;

	public void setValidateCode(String validateCode) {
		mValidateCode = validateCode;
	}

	public String getValidateCode() {
		return mValidateCode;
	}

	public void setValidateCodeSequenceNum(String num) {
		mValidateCodeSequenceNum = num;
	}

	public String getValidateCodeSequenceNum() {
		return mValidateCodeSequenceNum;
	}
	
	
}

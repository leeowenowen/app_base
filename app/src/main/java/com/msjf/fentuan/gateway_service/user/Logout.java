package com.msjf.fentuan.gateway_service.user;

public class Logout {
	private static final String TAG = "GatewayClient";

//  public static void start(final String phone,//
//			final String password,//
//			Client client) {
//		GatewayService.request(new RequestBuilder() {
//
//			@Override
//			public String service() {
//				return "/v1/user/do_logout";
//			}
//
//			@Override
//			public String secureMode() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public JSONObject buildRequest() throws JSONException {
//				JSONObject obj = new JSONObject();
//				obj.put("phone", phone);
//				return obj;
//			}
//		}, new ResponseParser() {
//
//			@Override
//			public boolean parse(JSONObject json) {
//				Singleton.of(Self.class).getUser().setId(json.optString("tid"));
//				Singleton.of(Self.class).getUser().setPhone(phone);
//				Logger.v(TAG, json.toString());
//				return false;
//			}
//		}, client);
//	}
}

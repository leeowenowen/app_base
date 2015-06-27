package com.msjf.fentuan.gateway_service;

/**
 * 返回代码
 *
 * @ClassName: ResultCode
 * @Description: TODO
 * @author: 唐建飞
 * @date:2015年5月18日 下午11:50:36
 */
public class ResultCodeConstants {
    public static class ResultCodeInfo {
        private int mCode;
        private String mMsg;

        public ResultCodeInfo(int code, String msg) {
            mCode = code;
            mMsg = msg;
        }

        public static ResultCodeInfo getResultCodeInfo(int code, String msg) {
            return new ResultCodeInfo(code, msg);
        }
    }
    /*
	 * 定义：4位整数
	 * 前两位代表模块编号，后两位代表异常编号
	 */

    /*****************************************系统异常10xx begin*****************************************/
    /**
     * 1001:请求参数为空
     */
    public static final ResultCodeInfo C1001 = ResultCodeInfo.getResultCodeInfo(1001, "请求参数为空");

    /**
     * 1002:签名验证失败
     */
    public static final ResultCodeInfo C1002 = ResultCodeInfo.getResultCodeInfo(1002, "签名验证失败");

    /**
     * 1003:没有对应的接口方法
     */
    public static final ResultCodeInfo C1003 = ResultCodeInfo.getResultCodeInfo(1003, "没有对应的接口方法");

    /**
     * 1004:接口方法未启用
     */
    public static final ResultCodeInfo C1004 = ResultCodeInfo.getResultCodeInfo(1004, "接口方法未启用");

    /**
     * 1005:接口方法返回信息为空
     */
    public static final ResultCodeInfo C1005 = ResultCodeInfo.getResultCodeInfo(1005, "接口方法返回信息为空");

    /**
     * 1006:系统异常
     */
    public static final ResultCodeInfo C1006 = ResultCodeInfo.getResultCodeInfo(1006, "系统异常");

    /**
     * 1007:客户端id不存在
     */
    public static final ResultCodeInfo C1007 = ResultCodeInfo.getResultCodeInfo(1007, "客户端id不存在");

    /**
     * 1008:客户端id不可用
     */
    public static final ResultCodeInfo C1008 = ResultCodeInfo.getResultCodeInfo(1008, "客户端id不可用");

    /**
     * 1009:请求已过期
     */
    public static final ResultCodeInfo C1009 = ResultCodeInfo.getResultCodeInfo(1009, "请求过期");
    /*****************************************系统异常10xx end*****************************************/


    /*****************************************业务公用00xx begin*****************************************/
    /**
     * 0010:分页信息为空
     */
    public static final ResultCodeInfo C0010 = ResultCodeInfo.getResultCodeInfo(0010, "分页信息为空");
    /*****************************************业务公用00xx end*****************************************/


    /*****************************************用户相关11xx begin*****************************************/
    /**
     * 1101:手机号码为空
     */
    public static final ResultCodeInfo C1101 = ResultCodeInfo.getResultCodeInfo(1101, "手机号码为空");

    /**
     * 1102:短信发送失败
     */
    public static final ResultCodeInfo C1102 = ResultCodeInfo.getResultCodeInfo(1102, "短信发送失败");

    /**
     * 1103:验证码为空
     */
    public static final ResultCodeInfo C1103 = ResultCodeInfo.getResultCodeInfo(1103, "验证码为空");

    /**
     * 1104:校验验证码失败
     */
    public static final ResultCodeInfo C1104 = ResultCodeInfo.getResultCodeInfo(1104, "校验验证码失败");

    /**
     * 1105:验证码不正确
     */
    public static final ResultCodeInfo C1105 = ResultCodeInfo.getResultCodeInfo(1105, "验证码不正确");

    /**
     * 1106:密码为空
     */
    public static final ResultCodeInfo C1106 = ResultCodeInfo.getResultCodeInfo(1106, "密码为空");

    /**
     * 1107:粉团名为空
     */
    public static final ResultCodeInfo C1107 = ResultCodeInfo.getResultCodeInfo(1107, "粉团名为空");

    /**
     * 1108:注册新用户失败
     */
    public static final ResultCodeInfo C1108 = ResultCodeInfo.getResultCodeInfo(1108, "注册新用户失败");

    /**
     * 1109:该用户已注册
     */
    public static final ResultCodeInfo C1109 = ResultCodeInfo.getResultCodeInfo(1109, "该用户已注册");

    /**
     * 1110:用户不存在
     */
    public static final ResultCodeInfo C1110 = ResultCodeInfo.getResultCodeInfo(1110, "用户不存在");

    /**
     * 1111:密码错误
     */
    public static final ResultCodeInfo C1111 = ResultCodeInfo.getResultCodeInfo(1111, "密码错误");

    /**
     * 1112:登陆失败
     */
    public static final ResultCodeInfo C1112 = ResultCodeInfo.getResultCodeInfo(1112, "登陆失败");

    /**
     * 1113:环信用户注册失败
     */
    public static final ResultCodeInfo C1113 = ResultCodeInfo.getResultCodeInfo(1113, "环信用户注册失败");

    /**
     * 1114:获取环信用户信息失败
     */
    public static final ResultCodeInfo C1114 = ResultCodeInfo.getResultCodeInfo(1114, "获取环信用户信息失败");

    /**
     * 1115:头像地址无效
     */
    public static final ResultCodeInfo C1115 = ResultCodeInfo.getResultCodeInfo(1115, "头像地址为空");

    /**
     * 1116:错误的头像地址
     */
    public static final ResultCodeInfo C1116 = ResultCodeInfo.getResultCodeInfo(1116, "头像地址错误");

    /**
     * 1117:用户id为空
     */
    public static final ResultCodeInfo C1117 = ResultCodeInfo.getResultCodeInfo(1117, "用户id为空");

    /**
     * 1118:用户不存在
     */
    public static final ResultCodeInfo C1118 = ResultCodeInfo.getResultCodeInfo(1118, "用户不存在");

    /**
     * 1119:用户未登录
     */
    public static final ResultCodeInfo C1119 = ResultCodeInfo.getResultCodeInfo(1118, "用户未登录");

    /*****************************************用户相关11xx end*****************************************/


    /*****************************************粉团相关12xx begin*****************************************/

    /**
     * 1201:粉团列表为空
     */
    public static final ResultCodeInfo C1201 = ResultCodeInfo.getResultCodeInfo(1302, "粉团列表为空");

    /**
     * 1202:粉团id为空
     */
    public static final ResultCodeInfo C1202 = ResultCodeInfo.getResultCodeInfo(1202, "粉团id为空");

    /**
     * 1203:没有查到该粉团信息
     */
    public static final ResultCodeInfo C1203 = ResultCodeInfo.getResultCodeInfo(1203, "没有查到该粉团信息");

    /**
     * 1204:用户关注粉团已达到上限
     */
    public static final ResultCodeInfo C1204 = ResultCodeInfo.getResultCodeInfo(1204, "用户关注粉团已达到上限");

    /**
     * 1205:粉团关注/取消关注失败
     */
    public static final ResultCodeInfo C1205 = ResultCodeInfo.getResultCodeInfo(1205, "粉团关注/取消关注失败");

    /**
     * 1206:用户关注粉团列表为空
     */
    public static final ResultCodeInfo C1206 = ResultCodeInfo.getResultCodeInfo(1206, "用户关注粉团列表为空");

    /**
     * 1207:粉团说吧消息内容为空
     */
    public static final ResultCodeInfo C1207 = ResultCodeInfo.getResultCodeInfo(1207, "粉团说吧消息内容为空");

    /**
     * 1208:粉团发表说吧内容失败
     */
    public static final ResultCodeInfo C1208 = ResultCodeInfo.getResultCodeInfo(1208, "粉团发表说吧内容失败");

    /*****************************************粉团相关12xx end*****************************************/

    /*****************************************粉逗圈相关13xx begin*****************************************/

    /**
     * 1301:粉逗圈消息列表为空
     */
    public static final ResultCodeInfo C1301 = ResultCodeInfo.getResultCodeInfo(1301, "粉逗圈消息列表为空");

    /**
     * 1302:粉逗圈消息内容为空
     */
    public static final ResultCodeInfo C1302 = ResultCodeInfo.getResultCodeInfo(1302, "粉逗圈消息内容为空");

    /**
     * 1303:粉逗圈匿名爆料标识为空
     */
    public static final ResultCodeInfo C1303 = ResultCodeInfo.getResultCodeInfo(1303, "粉逗圈匿名爆料标识为空");

    /**
     * 1304:粉逗圈id为空
     */
    public static final ResultCodeInfo C1304 = ResultCodeInfo.getResultCodeInfo(1304, "粉逗圈id为空");

    /**
     * 1305:没有查到该粉逗圈消息
     */
    public static final ResultCodeInfo C1305 = ResultCodeInfo.getResultCodeInfo(1305, "没有查到该粉逗圈消息");

    /**
     * 1306:粉逗圈消息删除失败
     */
    public static final ResultCodeInfo C1306 = ResultCodeInfo.getResultCodeInfo(1306, "粉逗圈消息删除失败");

    /**
     * 1307:粉逗圈消息浏览次数增加失败
     */
    public static final ResultCodeInfo C1307 = ResultCodeInfo.getResultCodeInfo(1307, "粉逗圈消息浏览次数增加失败");

    /**
     * 1308:粉逗圈消息点赞/取消点赞失败
     */
    public static final ResultCodeInfo C1308 = ResultCodeInfo.getResultCodeInfo(1308, "粉逗圈消息点赞/取消点赞失败");

    /**
     * 1309:粉逗圈消息评论失败
     */
    public static final ResultCodeInfo C1309 = ResultCodeInfo.getResultCodeInfo(1309, "粉逗圈消息评论失败");

    /**
     * 1310:粉逗圈消息评论id为空
     */
    public static final ResultCodeInfo C1310 = ResultCodeInfo.getResultCodeInfo(1310, "粉逗圈消息评论id为空");

    /**
     * 1311:粉逗圈消息删除评论失败
     */
    public static final ResultCodeInfo C1311 = ResultCodeInfo.getResultCodeInfo(1311, "粉逗圈消息删除评论失败");
    /*****************************************粉逗圈相关13xx end*****************************************/

}

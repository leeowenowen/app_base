package com.owo.app.theme;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.common.Check;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.DimensionUtil;

public class DefaultThemeProvider implements IThemeProvider {

    public DefaultThemeProvider() {

    }

    @Override
    public int color(ColorId id) {
        switch (id) {
        /*
         * common color
		 */
            case highlight_color2:
                return 0xffe21313;
            case highlight_color:
                return Color.RED;
            case view_divider:
                return 0xffdadada;
            case main_text_color:
                return Color.BLACK;
            case main_text_inverse_color:
                return 0xfff2f2f2;
            case gray_text_color:
                return 0xff8d8d8d;
            case common_gray_bg:
                return 0xcc8d8d8f;
            case common_light_gray_bg:
                return 0x778d8d8f;
            case base_page_title_bg:
                return 0xfff5f5f5;
            /*
             * studio
			 */
            case studio_top_layout_bg:
                return 0xffe1f4f6;

            // seat select
            case hall_screen_direction_bg:
                return 0xff28bdcd;

            //
            case talk_sep:
                return Color.WHITE;
            case main_page_center_bg:
                return 0xffe85b5b;
            case main_page_top_bg:
                return 0xffe64343;
            case main_page_movie_title_bg:
                return 0xff6acccb;
            case back_page_seperator:
                return Color.BLACK;
            case main_page_text_color:
                return 0xffffefef;
            case main_page_inverse_text_color:
                return Color.BLACK;
            case main_page_bottom_bg:
                return 0xffebecee;
            case main_bg:
                return Color.WHITE;
            case movie_view_bottom_text_color:
                return Color.BLACK;
            case cinema_group_title_bg:
                return 0xffd6d6d6;
            case hall_view_title_bg_color:
                return 0xff28bdcd;
            case hall_item_view_fenSiZhuanChange_bg:
                return 0xff96d7ff;
			/*
			 * register login
			 */
            case register_edit_text_hint:
                return 0xffe5dfe1;
			/*
			 * welcome
			 */
            case welcome_text:
                return 0xFFBC8F8F;
			/*
			 * fendouquan
			 */
            case fendouquan_green_text:
                return 0xff046d73;
            default:
                Check.d(false);
                return -1;
        }
    }

    @Override
    public Drawable drawable(DrawableId id) {
        switch (id) {
		/*
		 * common
		 */

            case common_bottom_btn:
                return TU.makeBtnPressedBG(BitmapId.common_bottom_btn, BitmapId.common_bottom_btn_pressed);

			/*
			 * main
			 */
            case main_account:
                return TU.makeBtnPressedBG(BitmapId.main_account, BitmapId.main_account_pressed);

            case main_add: //
                return TU.makeBtnPressedBG(BitmapId.main_add, BitmapId.main_add_pressed);

            case main_chat_send:
                return TU.makeBtnPressedBG(BitmapId.main_chat_send, BitmapId.main_chat_send_pressed);

            case main_down_menu:
                return TU.makeBtnPressedBG(BitmapId.main_down_menu, BitmapId.main_down_menu_pressed);

            case main_face:
                return TU.makeBtnPressedBG(BitmapId.main_face, BitmapId.main_face_pressed);
            case main_tab_fentuan:
                return TU.makeBtnSelectedBG(BitmapId.main_tab_fentuan_normal, BitmapId.main_tab_fentuan_selected);

            case main_tab_fendouquan:
                return TU.makeBtnSelectedBG(BitmapId.main_tab_fendouquan_normal, BitmapId.main_tab_fendouquan_selected);

            case main_tab_crowdfunding:
                return TU.makeBtnSelectedBG(BitmapId.main_tab_crowdfunding_normal, BitmapId.main_tab_crowdfunding_selected);
            case main_star_focus:
                return TU.makeBtnSelectedBG(BitmapId.main_mark_add, BitmapId.main_mark_has_add);
			/*
			 * movie info
			 */

			/*
			 * select cinema
			 */

			/*
			 * select hall
			 */
            case hall_price_valid://
            case hall_price_invalid://

			/*
			 * select seat
			 */
            case seat_item:
                return TU.makeBtnPressedBG(BitmapId.seat_item, null);
			/*
			 * order
			 */

			/*
			 * studio
			 */

			/*
			 * me
			 */
            case me_member_mic:
                return TU.makeBtnSelectedBG(BitmapId.me_member_invite_mic, BitmapId.me_member_invite_mic_selected);
			/*
			 * chat
			 */

			/*
			 * pay
			 */

			/*
			 * register
			 */

            case register_sex_radio:
                return TU.makeBtnCheckBg(BitmapId.register_sex_radio, BitmapId.register_sex_radio_selected);

			/*
			 * undefined
			 */

			/*
			 * dummy
			 */

            /**
             * fendouquan
             */
            case fendouquan_zan:
                return TU.makeBtnSelectedBG(BitmapId.fendouquan_zan, BitmapId.fendouquan_has_zan);
            case fendouquan_focus:
                return TU.makeBtnSelectedBG(BitmapId.fendouquan_add_gray, BitmapId.fendouquan_has_add);
            default:
                Check.d(false);
                return null;
        }
    }

    @Override
    public Bitmap bitmap(BitmapId id) {
        switch (id) {
		/*
		 * common
		 */
            case common_left_arow:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_left_arow);

            case common_right_arow:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_right_arow);

            case common_down_arow:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_down_arrow);

            case common_right_arow_black:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_right_arow_black);

            case common_bottom_btn:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_bottom_btn);

            case common_bottom_btn_pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_bottom_btn_pressed);

            case common_app_logo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.app_logo);

            case common_bottom_line:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.common_bottom_line);

			/*
			 * main
			 */
            case main_account:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_account);
            case main_account_pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_account_pressed);
            case main_add: //
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_add);
            case main_add_pressed: //
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_add_pressed);
            case main_center_left:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_center_left);
            case main_center_right:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_center_right);
            case main_chat_big:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_chat_big);
            case main_chat_send:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_chat_send);
            case main_chat_send_pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_chat_send_pressed);
            case main_chat_small:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_chat_small);
            case main_club:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_club);
            case main_down_menu:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_down_menu);
            case main_down_menu_pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_down_menu_pressed);
            case main_face:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_face);
            case main_face_pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_face_pressed);
            case main_heart:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_heart);
            case main_kiss:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_kiss);
            case main_location:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_location);
            case main_mark_add:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_mark_add);
            case main_mark_has_add:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_mark_has_add);
            case main_mark_hammer:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_mark_hammer);
            case main_fendouquan_small:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_fendouquan_small);
            case main_round:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_round);
            case main_studio:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_studio);

            case main_tab_fentuan_normal:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_fentuan_normal);
            case main_tab_fentuan_selected:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_fentuan_selected);
            case main_tab_fendouquan_normal:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_fendouquan_normal);
            case main_tab_fendouquan_selected:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_fendouquan_selected);
            case main_tab_crowdfunding_normal:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_crowdfunding_normal);
            case main_tab_crowdfunding_selected:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.main_tab_crowdfunding_selected);
            case qianghongbao_animation1:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.qianghongbao_animation1);
            case qianghongbao_animation2:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.qianghongbao_animation2);
            case qianghongbao_animation3:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.qianghongbao_animation3);
            case qianghongbao_animation4:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.qianghongbao_animation4);
			/*
			 * movie info
			 */
            case movie_bao_dian_ying:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_bao_dian_ying);
            case movie_bao_fans:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_bao_fans);
            case movie_bao_fans_special_performance:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_bao_fans_special_performance);
            case movie_main_actor:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_main_actor);
            case movie_story_content:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_story_content);
            case movie_zan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_zan);
            case movie_popup_circle_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_popup_circle_bg);
            case movie_ok:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_ok);
            case movie_cancel:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.movie_cancel);

			/*
			 * select cinema
			 */
            case cinema_3d:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.cinema_3d);
            case cinema_favorite:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.cinema_favorite);
            case cinema_nearby:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.cinema_nearby);
            case cinema_zuo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.cinema_zuo);

			/*
			 * select hall
			 */
            case hall_price_valid://
            case hall_price_invalid://

			/*
			 * select seat
			 */
            case seat_item:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.seat_item);
			/*
			 * order
			 */

			/*
			 * studio
			 */
            case studio_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_bg);
            case studio_host_topic:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_host_topic);
            case studio_mic_phone:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_mic_phone);
            case studio_today_topic_icon:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_today_topic_icon);
            case studio_today_topic_add:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_today_topic_add);
            case studio_bubble_right:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_bubble_right);
            case studio_bubble_left:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.studio_bubble_left);
			/*
			 * me
			 */
            case me_bind:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_bind);
            case me_default_photo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_default_photo);
            case me_femail:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_femail);
            case me_recharge:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_recharge);
            case me_member_help:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_member_help);
            case me_member_invite_as_host_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_member_invite_as_host_bg);
            case me_member_invite_mic:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_member_invite_mic);
            case me_member_invite_mic_selected:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.me_member_invite_mic_selected);
			/*
			 * chat
			 */
            case chat_msg_left:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.chat_msg_left);
            case chat_msg_right:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.chat_msg_right);

			/*
			 * pay
			 */
            case pay_zhifubao_big:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.pay_zhifubao_big);
            case pay_zhifubao_small:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.pay_zhifubao_small);
			/*
			 * login
			 */
            case login_fen:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.app_logo);
            case login_get_verify_code:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_get_verify_code);
            case login_lock:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_lock);
            case login_phone_num:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_phone_num);
            case login_qq:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_qq);
            case login_sina_blog:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_sina_blog);
            case login_weixin:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_weixin);
            case login_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.login_bg);
			/*
			 * register
			 */
            case register_city:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_city);
            case register_default_photo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_default_photo);
            case register_female:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_female);
            case register_fentuan_name:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_fentuan_name);
            case register_male:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_male);
            case register_sex:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_sex);
            case register_star:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_star);
            case register_divider:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_divider);
            case register_sex_radio:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_sex_radio);
            case register_sex_radio_selected:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_sex_radio_selected);
            case register_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.register_bg);
			/*
			 * undefined
			 */
            case barcode:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.barcode);
            case compass:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.compass);

            case location:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.location);
            case pencile:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.pencile);

            case pressed:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.pressed);
			/*
			 * dummy
			 */
            case star_photo:
                return BitmapHelper.getResourceBitmap(R.drawable.title_image, DimensionUtil.w(50), DimensionUtil.w(50));

			/*
			 * popup
			 */
            case popup_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.popup_bg);
			/*
			 * welcome
			 */
            case welcome_logo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.app_logo);
            case welcome_text_logo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.welcome_text_logo);
            case welcome_bottom_text:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.welcome_bottom_text);
			/*
			 * hongbao
			 */
            case hongbao_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_bg);
            case hongbao_fentuan_icon:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_fentuan_icon);
            case hongbao_green:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_green);
            case hongbao_input:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_input);
            case hongbao_share_mingxingtieba:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_mingxingtieba);
            case hongbao_share_qq:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_qq);
            case hongbao_share_sms:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_sms);
            case hongbao_share_weibo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_weibo);
            case hongbao_share_weixin:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_weixin);
            case hongbao_share_weixinquan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_share_weixinquan);
            case hongbao_yellow:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_yellow);
            case hongbao_small:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.hongbao_small);
			/*
			 * fendouquan
			 */
            case fendouquan_add_gray:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_add_gray);
            case fendouquan_add_image:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_add_image);
            case fendouquan_comment:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_comment);
            case fendouquan_has_add:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_has_add);
            case fendouquan_has_zan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_has_zan);
            case fendouquan_hot_comment:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_hot_comment);
            case fendouquan_liulan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_liulan);
            case fendouquan_top_send:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_top_send);
            case fendouquan_zan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_zan);
            case share_qq_space:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.share_qq_space);
            case douyiduan_send:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.douyiduan_send);
            case fendouquan_shenbao:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_shenbao);
            case fendouquan_douyiduan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_douyiduan);
            case fendouquan_comment_bg:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.fendouquan_comments_bg);
			/*
			 * demo_img
			 */
            case demo_img:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.demo_img);

            /*
                star photos
             */
            case star_dengziqi:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_dengziqi);
            case start_songzhixiao:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.start_songzhixiao);
            case star_wuyifan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_wuyifan);
            case star_puxinhui:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_puxinhui);
            case star_zhoujielun:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_zhoujielun);
            case star_liminhao:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_liminhao);
            case star_lizhunji:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_lizhunji);
            case star_luhan:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_luhan);
            case star_zhanggenshuo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_zhanggenshuo);
            case star_puyoutian:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_puyoutian);
            case star_lizhongshuo:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_lizhongshuo);
            case star_bigbang:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_bigbang);
            case star_zhangyixing:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_zhangyixing);
            case star_songchegnxian:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_songchegnxian);
            case star_songhuiqiao:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_songhuiqiao);
            case star_liyifeng:
                return BitmapHelper.getScaledResourceBitmap(R.drawable.star_liyifeng);


            default:
                Check.d(false);
                return null;
        }
    }

}

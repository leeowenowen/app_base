package com.msjf.fentuan.me.message;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msjf.fentuan.R;
import com.msjf.fentuan.ui.common.OkCancelDialogView;
import com.msjf.fentuan.ui.util.LU;
import com.msjf.fentuan.ui.util.TU;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.ColorId;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ThemeUtil;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by wangli on 15-5-24.
 */
public class StarNewsDialogContentView extends OkCancelDialogView {
    private FrameLayout mContainer;
    private StarNewsView mStarNewsView;

    public StarNewsDialogContentView(Context context) {
        super(context);

        mContainer = new FrameLayout(context);
        mStarNewsView = new StarNewsView(context);
        mContainer.addView(mStarNewsView);
        setContentView(mContainer);
        post(new Runnable() {
            @Override
            public void run() {
                makeScreenPop();
            }
        });
        mTitle.setText("#汪峰迎娶国际章#");

    }


    private void startScreenPopAnimation(final View v , int index) {
        final AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation showAnimation = new AlphaAnimation(0, 1);
        showAnimation.setDuration(1000);
        animationSet.addAnimation(showAnimation);

        TranslateAnimation moveAnimation = new TranslateAnimation(
                DimensionUtil.w(50) * index, 0, DimensionUtil.h(500), 0);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        moveAnimation.setInterpolator(interpolator);
        moveAnimation.setDuration(5000);
        moveAnimation.setStartOffset(1000);
        animationSet.addAnimation(moveAnimation);

        AlphaAnimation hideAnimation = new AlphaAnimation(1, 0);
        hideAnimation.setDuration(1000);
        hideAnimation.setStartOffset(5000);
        animationSet.addAnimation(hideAnimation);


        animationSet.setDuration(6000);
        animationSet.setInterpolator(new BounceInterpolator());
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.startAnimation(animationSet);
            }
        });
        v.startAnimation(animationSet);
    }

    private int randomLMargin() {
        return new Random(System.currentTimeMillis()).nextInt() % 200;
    }

    private int randomTMargin() {
        return new Random(System.currentTimeMillis()).nextInt() % 200 + 500;
    }

    private void makeScreenPop() {
        ScreenPopView[] popViews = new ScreenPopView[]{
                new ScreenPopView(getContext(), "牛逼"),
                new ScreenPopView(getContext(), "非常牛逼"),
                new ScreenPopView(getContext(), "赞一个")
        };
        for (int i = 0; i < popViews.length; ++i) {
            final int index = i;
            final ScreenPopView v = popViews[i];
            mContainer.addView(v, LP.FWWC);
            //LU.setMargin(v, randomLMargin(), randomTMargin(), 0, 0);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startScreenPopAnimation(v,index);
                }
            }, 500 * i);

        }


    }

    private class ScreenPopView extends LinearLayout implements ThemeObserver {
        private ImageView mImageView;
        private TextView mText;

        public ScreenPopView(Context context, String text) {
            super(context);

            mImageView = new ImageView(context);
            mText = new TextView(context);
            mText.setText(text);

            addView(mImageView, LP.lp(DimensionUtil.w(50), DimensionUtil.w(50)));
            addView(mText);

            LU.setMargin(mText, 20, 0, 0, 0);

            onThemeChanged();
        }

        @Override
        public void onThemeChanged() {
            TU.setTextColor(ColorId.main_text_inverse_color, mText);
            TU.setTextSize(25, mText);
            TU.setImageBitmap(BitmapId.me_default_photo, mImageView);
            setBackgroundDrawable(ThemeUtil.getNinePatchDrawable(R.drawable.me_message_screen_pop));
        }
    }
}

package com.msjf.fentuan.app.main;

/**
 * Created by wangli on 15-6-22.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

public class QiangHongBaoAnimationView extends FrameLayout {
    private ImageView[] mImageViews;
    private Bitmap[] mBitmaps;
    private Runnable mFinishCallback;

    public QiangHongBaoAnimationView(Context context) {
        super(context);
    }

    public void setFinishCallback(Runnable finishCallback)
    {
        mFinishCallback = finishCallback;
    }

    public void setBitmap(Bitmap[] bmps) {
        mBitmaps = bmps;
        mImageViews = new ImageView[bmps.length];
        for (int i = 0; i < bmps.length; ++i) {
            mImageViews[i] = new ImageView(getContext());
            mImageViews[i].setImageBitmap(mBitmaps[i]);
            FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.TOP | Gravity.LEFT);
            addView(mImageViews[i], fLayoutParams);
            mImageViews[i].setVisibility(INVISIBLE);
        }
    }

    void showView(int index) {
        for (int i = 0; i < mImageViews.length; ++i) {
            mImageViews[i].setVisibility((i == index) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private Random mRandom = new Random(System.currentTimeMillis());

    int randomTop() {
        if (getHeight() == 0) {
            return 0;
        }
        return mRandom.nextInt(getHeight() - mBitmaps[0].getHeight());
    }

    int randomLeft() {
        if (getWidth() == 0) {
            return 0;
        }
        return mRandom.nextInt(getWidth() - mBitmaps[0].getWidth());
    }

    private int mCurImageIndex = 0;
    private int mAnimationCount = 20;

    int nextImageIndex() {
        mCurImageIndex++;

        mCurImageIndex = mCurImageIndex % 4;
        return mCurImageIndex;
    }

    public void start() {
        for (int i = 0; i < mBitmaps.length; ++i) {
            mImageViews[i].setVisibility(View.INVISIBLE);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                doAnimation();
            }
        }, 500);
    }

    private void stop() {
        for (int i = 0; i < mBitmaps.length; ++i) {
            mImageViews[i].setVisibility(View.INVISIBLE);
        }
        mAnimationCount = 20;
        mCurImageIndex = 0;
        if(mFinishCallback != null)
        {
            mFinishCallback.run();
        }
    }

    public void doAnimation() {
        int index = nextImageIndex();
        showView(index);
        final ImageView view = mImageViews[index];
        final int left = randomLeft();
        final int top = randomTop();

        view.layout(left, top, left + view.getWidth(), top + view.getHeight());
        mAnimationCount--;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAnimationCount > 0) {
                    doAnimation();
                } else {
                    stop();
                }
            }
        }, 500);

    }
}


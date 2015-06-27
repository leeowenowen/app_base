package com.msjf.fentuan.login;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.msjf.fentuan.R;
import com.owo.ui.utils.LP;

public class VideoBgLoginView extends FrameLayout {
	private LoginView mLoginView;
	private VideoView mVideoView;

	public VideoBgLoginView(final Context context) {
		super(context);

		mLoginView = new LoginView(context);
		mVideoView = new VideoView(context) {
			// System VideoView is not fullScreen , we should adjust it by
			// ourselves
			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				int width = getDefaultSize(0, widthMeasureSpec);
				int height = getDefaultSize(0, heightMeasureSpec);
				setMeasuredDimension(width, height);
			}
		};
		addView(mVideoView, LP.FMM);
		addView(mLoginView, LP.FMM);

		mVideoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/"
				+ R.raw.bg_video));
		mVideoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mVideoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName()
						+ "/" + R.raw.bg_video));
				mVideoView.start();
			}
		});
		mVideoView.start();
	}

}

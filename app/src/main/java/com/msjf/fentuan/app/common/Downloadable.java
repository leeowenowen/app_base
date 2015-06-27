package com.msjf.fentuan.app.common;

import com.owo.base.common.Callback;

/**
 * Created by wangli on 15-6-7.
 */
public abstract class Downloadable {
    private DownloadState mState = DownloadState.None;

    public void setDownloadState(DownloadState state) {
        mState = state;
    }

    public DownloadState getDownloadState() {
        return mState;
    }
}

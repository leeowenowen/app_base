package com.msjf.fentuan.user;

import com.msjf.fentuan.log.Logger;
import com.owo.base.pattern.Singleton;

public class Self {
    private Self() {
    }

    private String mVerifyCodeSessionId;

    private User user = new User();
    private boolean isRegistered;

    public static User user()
    {
        return Singleton.of(Self.class).getUser();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        Logger.v("TestUserId", "my:" + user.getId());
        UserData.addUser(user);
    }

    public void setVerifyCodeSessionId(String sessionId) {
        mVerifyCodeSessionId = sessionId;
    }

    public String getVerifyCodeSessionId() {
        return mVerifyCodeSessionId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

}

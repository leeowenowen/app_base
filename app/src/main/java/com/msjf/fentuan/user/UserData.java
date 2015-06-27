package com.msjf.fentuan.user;

import com.owo.base.pattern.Singleton;

import java.util.HashMap;

public class UserData extends HashMap<String, User> {
	private UserData() {
	}

	public static void addUser(User user)
	{
		Singleton.of(UserData.class).put(user.getId(), user);
	}

	public static User getUser(String userId)
	{
        return Singleton.of(UserData.class).get(userId);
	}
}

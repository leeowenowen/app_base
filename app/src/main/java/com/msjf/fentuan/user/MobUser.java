package com.msjf.fentuan.user;


import com.alibaba.fastjson.JSONObject;

public class MobUser {
	private String uuid;
	private String type;
	private long created;
	private long modified;
	private String username;
	private boolean activated;
	private String nickname;

	private String password;

	public static MobUser fromJson(JSONObject json) {
		MobUser user = new MobUser();
		user.setUuid(json.getString("uuid"));
		user.setType(json.getString("type"));
		user.setCreated(json.getLongValue("created"));
		user.setModified(json.getLongValue("modified"));
		user.setUsername(json.getString("username"));
		user.setActivated(json.getBooleanValue("activated"));
		user.setNickname(json.getString("nickname"));
		user.setPassword(json.getString("password"));
		return user;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

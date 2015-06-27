package com.msjf.fentuan.me.message;

import android.location.Location;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.gateway_service.fendouquan.CircleInfo;

public class MessageDataItem {
    private String id;
    private Location location;
    private String senderId;
    private String content;
    private String title;
    private boolean sendReceiveFlag;
    private String sendTime;
    private boolean readFlag;
    private CircleInfo.Photo phtos;
    private boolean receiveDeleteFlag;
    private int messageType;
    private boolean sendDeleteFlag;

    public static MessageDataItem fromJson(JSONObject json) {
        MessageDataItem item = new MessageDataItem();
        item.setSenderId(json.getString("sendId"));
        String[] ct = json.getString("content").split("private");
        item.setContent(ct[0]);
        item.setTitle(ct[1]);
        item.setSendTime(json.getString("sendTime"));
        item.setSendReceiveFlag(json.getBoolean("sendReceiveFlag"));
        item.setReadFlag(json.getBoolean("readFlag"));
        // item.setImage
        item.setReceiveDeleteFlag(json.getBoolean("receiveDeleteFlag"));
        item.setId(json.getString("tid"));
        item.setMessageType(json.getIntValue("messageType"));
        item.setSendDeleteFlag(json.getBoolean("sendDeleteFlag"));

        return item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSendReceiveFlag() {
        return sendReceiveFlag;
    }

    public void setSendReceiveFlag(boolean sendReceiveFlag) {
        this.sendReceiveFlag = sendReceiveFlag;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isReadFlag() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }

    public CircleInfo.Photo getPhtos() {
        return phtos;
    }

    public void setPhtos(CircleInfo.Photo phtos) {
        this.phtos = phtos;
    }

    public boolean isReceiveDeleteFlag() {
        return receiveDeleteFlag;
    }

    public void setReceiveDeleteFlag(boolean receiveDeleteFlag) {
        this.receiveDeleteFlag = receiveDeleteFlag;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public boolean isSendDeleteFlag() {
        return sendDeleteFlag;
    }

    public void setSendDeleteFlag(boolean sendDeleteFlag) {
        this.sendDeleteFlag = sendDeleteFlag;
    }
}

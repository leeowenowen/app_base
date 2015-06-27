package com.msjf.fentuan.gateway_service.fendouquan;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.app.common.Downloadable;

/**
 * 粉逗圈图片实体类
 *
 * @ClassName: CircleImage
 * @Description: TODO
 * @author: 唐建飞
 * @date:2015年5月23日 下午4:53:18
 */
public class CircleImage extends Downloadable {
    private Bitmap bmp;

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    /**
     * 主键id
     */
    private String tid;

    /**
     * 粉逗圈消息id
     */
    private String circleId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原文件名
     */
    private String oldFileName;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 路径
     */
    private String path;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 删除标识(0:未删除.1:已删除)
     */
    private Integer deleteFlag;

    /**
     * 七牛云存储key
     */
    private String qiniuKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    private String url;
    private String fileHash;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    private String thumbnail;


    public Bitmap getThumbnailBmp() {
        return thumbnailBmp;
    }

    public void setThumbnailBmp(Bitmap thumbnailBmp) {
        this.thumbnailBmp = thumbnailBmp;
    }

    private Bitmap thumbnailBmp;



    /**
     * 获取 主键id
     *
     * @return
     */
    public String getTid() {
        return tid;
    }

    /**
     * 设置  主键id
     *
     * @param tid
     */
    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    /**
     * 获取 粉逗圈消息id
     *
     * @return
     */
    public String getCircleId() {
        return circleId;
    }

    /**
     * 设置  粉逗圈消息id
     *
     * @param circleId
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    /**
     * 获取 文件名
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置  文件名
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取 原文件名
     *
     * @return
     */
    public String getOldFileName() {
        return oldFileName;
    }

    /**
     * 设置  原文件名
     *
     * @param oldFileName
     */
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName == null ? null : oldFileName.trim();
    }

    /**
     * 获取 后缀
     *
     * @return
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置  后缀
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * 获取 路径
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置  路径
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取 创建时间
     *
     * @return
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置  创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 删除标识(0:未删除.1:已删除)
     *
     * @return
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置  删除标识(0:未删除.1:已删除)
     *
     * @param deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取 七牛云存储key
     *
     * @return
     */
    public String getQiniuKey() {
        return qiniuKey;
    }

    /**
     * 设置  七牛云存储key
     *
     * @param qiniuKey
     */
    public void setQiniuKey(String qiniuKey) {
        this.qiniuKey = qiniuKey == null ? null : qiniuKey.trim();
    }

    public static CircleImage fromJson(JSONObject json) {
        CircleImage image = new CircleImage();
        image.setFileName(json.getString("fileName"));
        image.setSuffix(json.getString("suffix"));
        image.setDeleteFlag(json.getIntValue("deleteFlag"));
        image.setPath(json.getString("path"));
        image.setUrl(json.getString("url"));
        image.setFileHash(json.getString("fileHash"));
        image.setCircleId(json.getString("circleId"));
        image.setTid(json.getString("tid"));
        image.setCreateTime(json.getLongValue("createTime"));
        image.setOldFileName(json.getString("oldFileName"));
        image.setQiniuKey(json.getString("qiniuKey"));
        image.setThumbnail(json.getString("thumbnail"));
        return image;
    }
}
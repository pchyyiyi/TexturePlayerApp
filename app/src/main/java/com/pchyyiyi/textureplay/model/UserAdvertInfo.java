package com.pchyyiyi.textureplay.model;

import java.io.Serializable;

/**
 * @ClassName 用户广告信息
 * @Description ShowWatchadsDialog
 * @Author fayxx
 * @Date 2021/4/23 下午1:43
 * @Version 1.0
 */
public class UserAdvertInfo implements Serializable {
    /**
     * Aid
     */
    private int aid;
    /**
     * Content
     */
    private String adContent;
    /**
     * Aclass //2 视频 //1 图片 //0 文字
     */
    private int aclass;
    /**
     * Url
     */
    private String picUrl;
    /**
     * VideoUrl
     */
    private String videoUrl;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public int getAclass() {
        return aclass;
    }

    public void setAclass(int aclass) {
        this.aclass = aclass;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}

package com.example.gankiouilife;

import java.util.List;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:50
 * @描述: 这是一个 AndroidDateModel 类.
 */
public class AndroidDateModel {
    /**
     * _id : 5a0b9888421aa90fef203530
     * createdAt : 2017-11-15T09:29:44.280Z
     * desc : Android与Python爱之初体验
     * publishedAt : 2017-11-16T12:01:05.619Z
     * source : web
     * type : Android
     * url : https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488220&idx=1&sn=834f2b928d2852ebdc4db1439ee0e08b
     * used : true
     * who : 陈宇明
     * images : ["http://img.gank.io/a3fc2a25-adea-45de-b186-17884187280c"]
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "AndroidDateModel{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", images=" + images +
                '}';
    }
}

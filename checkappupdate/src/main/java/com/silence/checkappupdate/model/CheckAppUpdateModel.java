package com.silence.checkappupdate.model;

import java.util.Date;

public class CheckAppUpdateModel {

    private String updateUrl;		// APP更新地址

    private String name;			// APP名字

    private String version;			// APP版本

    private Date updateDate;		// APP更新日期

    private String updateContent;	// 版本更新信息


    public CheckAppUpdateModel() {
        super();
    }

    public CheckAppUpdateModel(String updateUrl, String name, String version, Date updateDate, String updateContent) {
        this.updateUrl = updateUrl;
        this.name = name;
        this.version = version;
        this.updateDate = updateDate;
        this.updateContent = updateContent;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    @Override
    public String toString() {
        return "CheckAppUpdateModel{" +
                "updateUrl='" + updateUrl + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", updateDate=" + updateDate +
                ", updateContent='" + updateContent + '\'' +
                '}';
    }
}

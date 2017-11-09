package com.duimy.dmimsdk.model;

import org.jivesoftware.smack.util.XmlStringBuilder;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by haley on 2017/7/17.
 */

@Table(name = "T_ImageBody")
public class DMImageMessageBody extends DMFileMessageBody {

    @Column(name = "_width")
    public String width;
    @Column(name = "_height")
    public String height;
    @Column(name = "_thumDisplayName")
    public String thumDisplayName;
    @Column(name = "_thumLocalPath")
    public String thumLocalPath;
    @Column(name = "_thumWidth")
    public String thumWidth;
    @Column(name = "_thumHeight")
    public String thumHeight;
    @Column(name = "_thumFileLenth")
    public String thumFileLenth;
    @Column(name = "_thumDownloadStatus")
    public String thumDownloadStatus;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThumDisplayName() {
        return thumDisplayName;
    }

    public void setThumDisplayName(String thumDisplayName) {
        this.thumDisplayName = thumDisplayName;
    }

    public String getThumLocalPath() {
        return thumLocalPath;
    }

    public void setThumLocalPath(String thumLocalPath) {
        this.thumLocalPath = thumLocalPath;
    }

    public String getThumWidth() {
        return thumWidth;
    }

    public void setThumWidth(String thumWidth) {
        this.thumWidth = thumWidth;
    }

    public String getThumHeight() {
        return thumHeight;
    }

    public void setThumHeight(String thumHeight) {
        this.thumHeight = thumHeight;
    }

    public String getThumFileLenth() {
        return thumFileLenth;
    }

    public void setThumFileLenth(String thumFileLenth) {
        this.thumFileLenth = thumFileLenth;
    }

    public String getThumDownloadStatus() {
        return thumDownloadStatus;
    }

    public void setThumDownloadStatus(String thumDownloadStatus) {
        this.thumDownloadStatus = thumDownloadStatus;
    }

    public DMImageMessageBody() {
        this.setBodyType(DMMessageBody.Body_Type_Image);
    }

    public DMImageMessageBody(String width, String height, String localPath, String fileLength) {
        this.width = width;
        this.height = height;
        this.localPath = localPath;
        this.fileLength = fileLength;
        this.setBodyType(DMMessageBody.Body_Type_Image);
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder buf = new XmlStringBuilder();
        buf.openElement(getElementName());

        buf.element("remotepath", remotePath);
        buf.element("filelength", fileLength);
        buf.element("width", width);
        buf.element("height", height);

        buf.closeElement(getElementName());
        return buf;
    }

    @Override
    public String toString() {
        return "DMImageMessageBody{" +
                "width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", localPath='" + localPath + '\'' +
                ", fileLength='" + fileLength + '\'' +
                ", thumDisplayName='" + thumDisplayName + '\'' +
                ", thumLocalPath='" + thumLocalPath + '\'' +
                ", thumWidth='" + thumWidth + '\'' +
                ", thumHeight='" + thumHeight + '\'' +
                ", thumFileLenth='" + thumFileLenth + '\'' +
                ", thumDownloadStatus='" + thumDownloadStatus + '\'' +
                '}';
    }
}

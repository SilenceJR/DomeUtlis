package com.duimy.dmimsdk.model;

import org.jivesoftware.smack.util.XmlStringBuilder;
import org.xutils.db.annotation.Column;

/**
 * Created by haley on 2017/7/17.
 */

public class DMFileMessageBody extends DMMessageBody {

    @Column(name = "_displayName")
    public String displayName;
    @Column(name = "_localPath")
    public String localPath;
    @Column(name = "_remotePath")
    public String remotePath;
    @Column(name = "_fileLength")
    public String  fileLength;
    @Column(name = "_downloadStatus")
    public String downloadStatus;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public DMFileMessageBody() {
        this.setBodyType(DMMessageBody.Body_Type_File);
    }

    public DMFileMessageBody(String localPath, String fileLength) {
        this.localPath = localPath;
        this.fileLength = fileLength;
        this.setBodyType(DMMessageBody.Body_Type_File);
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder buf = new XmlStringBuilder();
        buf.openElement(getElementName());

        buf.element("remotepath", remotePath);
        buf.element("filelength", fileLength);

        buf.closeElement(getElementName());
        return buf;
    }

    @Override
    public String toString() {
        return "DMFileMessageBody{" +
                "displayName='" + displayName + '\'' +
                ", localPath='" + localPath + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", fileLength='" + fileLength + '\'' +
                ", downloadStatus='" + downloadStatus + '\'' +
                '}';
    }
}

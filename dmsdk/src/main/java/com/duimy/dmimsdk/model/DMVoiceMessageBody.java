package com.duimy.dmimsdk.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by haley on 2017/7/17.
 */

@Table(name = "T_VoiceBody")
public class DMVoiceMessageBody extends DMFileMessageBody {

    @Column(name = "_duration")
    public String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public DMVoiceMessageBody() {
        this.setBodyType(DMMessageBody.Body_Type_Voice);
    }

    public DMVoiceMessageBody(String localPath, String duration, String fileLength) {
        this.localPath = localPath;
        this.duration = duration;
        this.fileLength = fileLength;
        this.setBodyType(DMMessageBody.Body_Type_Voice);
    }

    @Override
    public String toString() {
        return "DMVoiceMessageBody{" +
                "duration='" + duration + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", localPath='" + localPath + '\'' +
                ", fileLength='" + fileLength + '\'' +
                '}';
    }
}

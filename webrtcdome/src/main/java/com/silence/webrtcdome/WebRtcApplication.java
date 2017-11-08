package com.silence.webrtcdome;

import android.app.Application;

import org.webrtc.PeerConnectionFactory;

public class WebRtcApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PeerConnectionFactory.initializeAndroidGlobals(this, true, true, false, null);
    }
}

package com.silence.webrtcdome;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static final String VIDEO_TRACK_ID = "VIDEO";
    private static final String LOCAL_MEDIA_STREAM_ID = "LOCAL_MEDIA_STREAM_ID";

    @InjectView(R.id.bt_video)
    Button mBtVideo;
    @InjectView(R.id.glview_call)
    GLSurfaceView mGlviewCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        String s = "";
        s.matches("\\d{4,11}");

    }


    @OnClick(R.id.bt_video)
    public void onViewClicked() {





//        PeerConnectionFactory peerConnectionFactory = new PeerConnectionFactory();
//        int deviceCount = VideoCapturerAndroid.getDeviceCount();
//        String name = null;
//        if (deviceCount > 0) {
//            String nameOfBackFacingDevice = VideoCapturerAndroid.getNameOfBackFacingDevice();
//            String nameOfFrontFacingDevice = VideoCapturerAndroid.getNameOfFrontFacingDevice();
//
//            name = nameOfBackFacingDevice == null || nameOfBackFacingDevice.equals("") ? nameOfFrontFacingDevice : nameOfBackFacingDevice;
//        }
//        VideoCapturerAndroid capturerAndroid = VideoCapturerAndroid.create(name);
//        VideoSource videoSource = peerConnectionFactory.createVideoSource(capturerAndroid, new MediaConstraints());
//        VideoTrack videoTrack = peerConnectionFactory.createVideoTrack(VIDEO_TRACK_ID, videoSource);
//
//
//        VideoRendererGui.setView(mGlviewCall, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        try {
//            VideoRenderer renderer = VideoRendererGui.createGui(200, 200, mGlviewCall.getLayoutParams().width, mGlviewCall
//                    .getLayoutParams().height, VideoRendererGui.ScalingType.SCALE_ASPECT_FILL, false);
//            videoTrack.addRenderer(renderer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        MediaStream mediaStream = peerConnectionFactory.createLocalMediaStream(LOCAL_MEDIA_STREAM_ID);
//        mediaStream.addTrack(videoTrack);
//
//        peerConnectionFactory.createPeerConnection()
    }
}

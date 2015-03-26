package com.bernabeborrero.theblackkeys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoActivity extends Activity {

    static final String VIDEO_PATH = "https://dl.dropboxusercontent.com/s/mf7uz7njwjj82ne/gold_on_the_ceiling.mp4?dl=0";

    private VideoView videoClip;

    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        setUpGUI();

        mediaController = new MediaController(this);
        videoClip.setMediaController(mediaController);
        videoClip.setVideoPath(VIDEO_PATH);
        videoClip.start();
        videoClip.requestFocus();
    }

    private void setUpGUI() {
        videoClip = (VideoView) findViewById(R.id.videoclip);
    }
}

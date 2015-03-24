package com.bernabeborrero.theblackkeys;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.bernabeborrero.bluetea.BlueTea;
import com.dd.CircularProgressButton;

import java.io.File;
import java.io.IOException;


public class SingActivity extends ActionBarActivity {

    private static final String LOG_TAG = "AudioRecording";
    private static String audioFileName;

    TextView txtLyrics;
    CircularProgressButton btnRecord;

    MediaPlayer karaokePlayer;
    MediaRecorder recorder;

    public SingActivity() {
        audioFileName = "userRecording-goldOnTheCeiling.3gp";
        File path = new File(Environment.getExternalStorageDirectory(), LOG_TAG);
        if (!path.exists()) {
            path.mkdirs();
        }

        audioFileName = new File(path, audioFileName).getAbsolutePath();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing);

        setUpGUI();
    }

    /**
     * Set up the GUI components
     */
    private void setUpGUI() {
        txtLyrics = (TextView) findViewById(R.id.txtLyrics);
        btnRecord = (CircularProgressButton) findViewById(R.id.btnRecord);

        txtLyrics.setText(getString(R.string.lyrics));

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRecord.getProgress() == 0) {
                    startPlaying();
                    startRecording();
                    startProgress(btnRecord, karaokePlayer.getDuration());

                    BlueTea.logStep(6, "Record_Voice");
                } else {
                    stopPlaying();
                    stopRecording();
                    btnRecord.setProgress(0);
                }
            }
        });
    }

    /**
     * Start the progress of the recording button
     * @param button the button
     * @param milliseconds the length of the song in milliseconds
     */
    private void startProgress(final CircularProgressButton button, int milliseconds) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(milliseconds);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }

    /**
     * Start playing the karaoke song
     */
    private void startPlaying() {
        karaokePlayer = MediaPlayer.create(this, R.raw.gold_on_the_ceiling);
        karaokePlayer.start();
    }

    /**
     * Stop playing the karaoke song
     */
    private void stopPlaying() {
        karaokePlayer.stop();
        karaokePlayer.release();
        karaokePlayer = null;
    }

    /**
     * Start recording the user voice
     */
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(audioFileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            btnRecord.setEnabled(false);
        }

        recorder.start();
    }

    /**
     * Stop recording the user voice
     */
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlaying();
        stopRecording();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_play_recording) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

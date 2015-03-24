package com.bernabeborrero.theblackkeys;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.bernabeborrero.bluetea.BlueTea;
import com.dd.CircularProgressButton;


public class SingActivity extends ActionBarActivity {

    TextView txtLyrics;
    CircularProgressButton btnRecord;

    MediaPlayer karaokePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing);

        karaokePlayer = MediaPlayer.create(this, R.raw.gold_on_the_ceiling);

        setUpGUI();
    }

    private void setUpGUI() {
        txtLyrics = (TextView) findViewById(R.id.txtLyrics);
        btnRecord = (CircularProgressButton) findViewById(R.id.btnRecord);

        txtLyrics.setText(getString(R.string.lyrics));

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRecord.getProgress() == 0) {
                    karaokePlayer.start();
                    startProgress(btnRecord, karaokePlayer.getDuration());

                    BlueTea.logStep(6, "Record_Voice");
                } else {
                    karaokePlayer.stop();
                    btnRecord.setProgress(0);
                }
            }
        });
    }

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

    @Override
    public void onStop() {
        super.onStop();
        if(karaokePlayer.isPlaying()) {
            karaokePlayer.stop();
        }

        karaokePlayer.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

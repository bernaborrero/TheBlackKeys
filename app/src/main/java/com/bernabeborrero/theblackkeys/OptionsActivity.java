package com.bernabeborrero.theblackkeys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class OptionsActivity extends ActionBarActivity {

    Button btnVideo, btnHistory, btnSing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setUpGUI();
    }

    private void setUpGUI() {
        btnVideo = (Button) findViewById(R.id.btnVideo);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnSing = (Button) findViewById(R.id.btnSing);

        // listeners
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSing = new Intent(getBaseContext(), SingActivity.class);
                startActivity(startSing);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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

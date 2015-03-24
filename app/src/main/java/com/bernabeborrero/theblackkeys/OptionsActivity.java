package com.bernabeborrero.theblackkeys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bernabeborrero.bluetea.BlueTea;


public class OptionsActivity extends ActionBarActivity {

    Button btnVideo, btnHistory, btnSing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getSupportActionBar().hide();
        }

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
                BlueTea.logStep(5, "Open_Video");
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueTea.logStep(4, "Open_History");
            }
        });

        btnSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueTea.logStep(3, "Open_Sing");
                Intent startSing = new Intent(getBaseContext(), SingActivity.class);
                startActivity(startSing);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        BlueTea.logStep(2, "Open_Menu");
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

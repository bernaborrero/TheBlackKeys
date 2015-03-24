package com.bernabeborrero.theblackkeys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bernabeborrero.bluetea.BlueTea;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class StartActivity extends ActionBarActivity {
    static final int CAMERA_APP_CODE = 100;
    File tempImageFile;
    Button btnGo;
    ImageView imgPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getSupportActionBar().hide();
        }

        BlueTea.startSession("theblackkeys");   // http://bluetea.tk/bluetea.php?appName=theblackkeys

        setUpGUI();
        setUpCamera();
    }

    private void setUpGUI() {
        btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionsIntent = new Intent(getBaseContext(), OptionsActivity.class);
                startActivity(optionsIntent);
            }
        });
    }

    private void setUpCamera(){
        imgPersona = (ImageView) findViewById(R.id.imgPerson);
        imgPersona.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, CAMERA_APP_CODE);
            }
        });
    }

    /**
     * Metode que comprova si hi ha algun medi per tal de realitzar la captura de la imatge
     * @param context
     * @param action
     * @return
     */
    private static boolean isIntentAvaliable(Context context, String action){
       final PackageManager packageManager = context.getPackageManager();
       final Intent intent = new Intent(action);
       List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
       return list.size()> 0;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_APP_CODE){
            if(resultCode == RESULT_OK){
                try {
                    imgPersona.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(tempImageFile)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *     Metode de creació del fitxer de la imatge
     */
    private File crearFitxerImatge(){
        String timeStamp = new SimpleDateFormat("yyyMMdd").format(new Date());
        String imageFileName = "the_black_keys_" + timeStamp + ".jpg";
        File path = new File(Environment.getExternalStorageDirectory(), this.getPackageName());
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, imageFileName);
    }

    /**
     *     Metode que comprova si hi ha medi per tal de realitzar la captura de la imatge
     *     En cas afirmatiu engega el medi, en cas contrari mostra un missatge.
     */
    public void ferFoto(View view) throws IOException{
        if(isIntentAvaliable(this, MediaStore.ACTION_IMAGE_CAPTURE)){
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempImageFile= crearFitxerImatge();
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));
            startActivityForResult(takePhotoIntent, CAMERA_APP_CODE);
        }else{
            Toast.makeText(this, "No hi hi cap medi per realitzar una fotogafia", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        BlueTea.logStep(1, "Start_App");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inici, menu);
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

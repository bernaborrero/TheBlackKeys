package com.bernabeborrero.theblackkeys;

/**
 * Created by Bosccoma on 20/03/2015.
 */
import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class PrevisualitzacioCamera extends SurfaceView implements
        SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Camera camera;
    private Context context;

    public PrevisualitzacioCamera(Context context, Camera camera) {
        super(context);
        this.context = context;
        this.camera = camera;
        // configurar el SurfaceHolder
        holder = getHolder();
        holder.addCallback(this);
        // esta deprecated, pero cal en versions anteriors a Android 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        if (holder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // aturar la previsualitzacio abans de fer canvis
        try {
            camera.stopPreview();
        } catch (Exception e) {

        }

        // establir la mida de previsualitzacio i redimensionar o rotar
        // iniciar la previsualitzacio amb els nous canvis
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // connectar la camera amb la vista
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.release();
    }
}

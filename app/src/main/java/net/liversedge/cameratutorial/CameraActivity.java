package net.liversedge.cameratutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static net.liversedge.cameratutorial.Constants.DEBUG;
import static net.liversedge.cameratutorial.Constants.EXTRA_MESSAGE;

public class CameraActivity extends AppCompatActivity {

    // SECTION 2 - Tutorial 3
    protected PreviewView cameraView;
    protected Preview preview;
    protected Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // SECTION 1 - Tutorial 3
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        Log.d(DEBUG, "Camera activity started: " + message);

        // SECTION 2 - Tutorial 3
        cameraView = findViewById(R.id.cameraView);
        startCamera();
    }

    // SECTION 2 - Tutorial 3
    private void startCamera() {

        // Specify which camera we would like
        final CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        // Listener to wait until we have found the camera
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {

                ProcessCameraProvider cameraProvider = null;
                try {
                    // Attempt to find the camera provider
                    cameraProvider = cameraProviderFuture.get();
                    cameraProvider.unbindAll();

                    // Bind the camera to the preview
                    preview = new Preview.Builder().build();
                    camera = cameraProvider.bindToLifecycle(
                            CameraActivity.this,
                            cameraSelector,
                            preview);
                    preview.setSurfaceProvider(cameraView.createSurfaceProvider(camera.getCameraInfo()));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {

    }
}

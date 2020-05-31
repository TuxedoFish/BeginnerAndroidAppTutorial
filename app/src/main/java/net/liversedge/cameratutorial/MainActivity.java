package net.liversedge.cameratutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static net.liversedge.cameratutorial.Constants.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    int requestNumber = 1;
    protected static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    // SECTION 1 - Tutorial 3
    protected Button takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Has permission: " + hasPermission(PERMISSION_CAMERA));
        if(!hasPermission(PERMISSION_CAMERA)) {
            requestPermission(PERMISSION_CAMERA, "Camera");
        }

        // SECTION 1 - Tutorial 3

        takePhoto = findViewById(R.id.button);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Started by pressing button");
                startActivity(intent);
            }
        });
    }

    protected boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    protected void requestPermission(String permission, String permissionName) {
        System.out.println(Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA)) {
                Toast.makeText(
                        MainActivity.this,
                        permissionName + " permission is required for this demo",
                        Toast.LENGTH_LONG)
                        .show();
            }
            requestPermissions(new String[] {permission}, requestNumber);
            requestNumber ++;
        }
    }
}

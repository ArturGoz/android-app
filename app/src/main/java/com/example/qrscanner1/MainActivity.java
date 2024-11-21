package com.example.qrscanner1;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int CAMERA_PERMISSION_CODE = 100;
    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Activity created");

        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setText(getString(R.string.scan_button));
        btn_scan.setOnClickListener(v -> scanCode());
    }



    private void scanCode() {
        Log.d(TAG, "scanCode: Starting QR scan");
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() != null){
            Log.d(TAG, "QR Code scanned: " + result.getContents());
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.result_title));
            builder.setMessage(result.getContents());
            builder.setPositiveButton(getString(R.string.ok_button), (dialog, which) -> dialog.dismiss()).show();
        }
        else {
            Log.w(TAG, "No QR Code content detected");
        }
    }
);
}
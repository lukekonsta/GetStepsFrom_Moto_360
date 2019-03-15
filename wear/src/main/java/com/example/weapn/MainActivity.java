package com.example.weapn;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends WearableActivity implements SensorEventListener {

    private TextView mTextView;
    SensorManager sensorManager;
    boolean running = false;
    private int STORAGE_PERMISSION_CODE = 1;
    private Sensor mAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setText("ome");


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
            //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if(mAccelerometer!=null){
                System.out.println("found");
                //sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
            }else{
                //Toast.makeText(this,"not found",Toast.LENGTH_LONG).show();
                System.out.println("not found");
            }

        } else {
            requestStoragePermission();
            //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if(mAccelerometer!=null){
                System.out.println("found");
                //sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
            }else{
                //Toast.makeText(this,"not found",Toast.LENGTH_LONG).show();
                System.out.println("not found");
            }
        }



        // Enables Always-on
        setAmbientEnabled();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.BODY_SENSORS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.BODY_SENSORS}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.BODY_SENSORS}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }




        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            mTextView.setText("Step Counter Detected : " + value);
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            mTextView.setText("Step Detector Detected : " + value);
        }else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            Toast.makeText(this, "Permission YES", Toast.LENGTH_SHORT).show();

                /*simpleStepDetector.updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2]);*/
            }










    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

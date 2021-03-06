package com.example.johnb.motionsensor3in1;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class theCombination extends AppCompatActivity implements SensorEventListener {

    /*
    Sensor declaration
     */
    Sensor mMagnetic;
    SensorManager mMagnetMan;
    public TextView MagnetView;

    Sensor mAccelerometer;
    SensorManager mAccelerometerMan;
    TextView AccelerometerView;

    Sensor mGyroscope;
    SensorManager mGyroscopeMan;
    TextView GyroscopeView;

    Sensor mLight;
    SensorManager mLightMan;
    TextView LightView;

    Sensor mTemperature;
    SensorManager mTemperaturetMan;
    TextView TemperatureView;

    Button toastButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_combination);

        /*
        * Sets up sensor drain and output to screen.
         */

        mMagnetMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagnetic = mMagnetMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mMagnetMan.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_FASTEST); //continuous samppling rate
        MagnetView = (TextView) findViewById(R.id.MagneticData);

        mAccelerometerMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mAccelerometerMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccelerometerMan.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST); //continuous samppling rate
        AccelerometerView = (TextView) findViewById(R.id.AccelerometerData);

        mGyroscopeMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGyroscope = mGyroscopeMan.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGyroscopeMan.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_FASTEST); //continuous samppling rate
        GyroscopeView = (TextView) findViewById(R.id.GyroscopeData);

        mLightMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mGyroscopeMan.getDefaultSensor(Sensor.TYPE_LIGHT);
        mLightMan.registerListener(this, mLight, SensorManager.SENSOR_DELAY_FASTEST); //continuous samppling rate
        LightView = (TextView) findViewById(R.id.LightData);

        mTemperaturetMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        mTemperature = mTemperaturetMan.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mTemperaturetMan.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_FASTEST); //continuous samppling rate
        TemperatureView = (TextView) findViewById(R.id.TempuratureData);


        TextView wakeLockStatus = (TextView) findViewById(R.id.WakeLockStatus);

        BatteryManager mBatteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        /*
        * In theory, the try/catch below is not longer important and should be able to be removed.
         */

        try {
            int x = 1 / 0;
        } catch (Exception E) {
        } finally {

        }

        /*
        * Another way to try draining power that didn't work as well as floating point math.
         */

        int n = 100;

        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = n;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = n;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }

        }

        /*
        * Starts the attack and tells you whether it's on or off.
         */

        toastButton = (Button) findViewById(R.id.serviceButton);

        if (isServiceRunning(TheService.class)){
            toastButton.setText("Stop Service");
        }
        else
        {
            toastButton.setText("Start Service");
        }


        toastButton.setOnClickListener(new View.OnClickListener() { //we need to overside onClick() in the new View.OnClickListener()
            @Override
            public void onClick(View v) {
                //Log.e("MAIN", "Pressed Button");

                if (isServiceRunning(TheService.class)){
                    toastButton.setText("Start Service");
                }
                else
                {
                    toastButton.setText("Stop Service");
                    startService(new Intent(theCombination.this, TheService.class));
                }


            }
        });
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /*
    * Output sesnsors data to the screen.
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
            MagnetView.setText("x: " + event.values[0] + "\n" + "y: " + event.values[1] + "\n" + "z: " + event.values[2]);
        } else if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            AccelerometerView.setText("x: " + event.values[0] + "\n" + "y: " + event.values[1] + "\n" + "z: " + event.values[2]);
        } else if (sensorType == Sensor.TYPE_GYROSCOPE) {
            GyroscopeView.setText("x: " + event.values[0] + "\n" + "y: " + event.values[1] + "\n" + "z: " + event.values[2]);
        } else if (sensorType == Sensor.TYPE_LIGHT) {
            //LightView.setText("x: " + event.values[0] + "\n" + "y: " + event.values[1] + "\n" + "z: " + event.values[2]);
        } else if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) { //all temp no longer valid
            //TemperatureView.setText("x: " + event.values[0] + "\n" + "y: " + event.values[1] + "\n" + "z: " + event.values[2]);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "No Sensors Detected",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

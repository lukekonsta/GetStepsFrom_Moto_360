package com.example.weapn;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    SensorManager sensorManager;
    boolean running = false;
    private int STORAGE_PERMISSION_CODE = 1;
    private Sensor mAccelerometer;
    int finalValue = 0;
    String comparisonDate = "";
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    Calendar c;
    SimpleDateFormat sdf;
    int steps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = myPrefs.edit();

        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("MM/dd/yyyy");


        String getCurrentDateTime = sdf.format(c.getTime());
        System.out.println("current date" + getCurrentDateTime);
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mDate =  myPrefs.getString("Date","nothing");
        System.out.println("current Mdate" + mDate);


        if (getCurrentDateTime.compareTo(mDate) < 0)
        {
            comparisonDate = getCurrentDateTime;
            System.out.println("CHOICE 1");
            editor.putString("Date", comparisonDate);
            editor.commit();

        }
        else if(getCurrentDateTime.compareTo(mDate) == 0){

            System.out.println("CHOICE 2");


        }else
        {
            //Log.d("Return","getMyTime older than getCurrentDateTime ");
            System.out.println("CHOICE 3");
            comparisonDate = getCurrentDateTime;
            editor.putString("Date", mDate);
            editor.commit();
        }







    }
}

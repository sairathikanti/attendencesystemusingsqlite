package com.example.sandy.attendencesystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private  TextView amrpm;
    private String AMPM;
    private  TextView day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tasksRepo = new TasksRepo(getApplicationContext());
        //for sqlite db opening
        try {

            Stetho.InitializerBuilder initializerBuilder =
                    Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
            initializerBuilder.enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this)
            );

// Enable command line interface

            initializerBuilder.enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(this)
            );

// Use the InitializerBuilder to generate an Initializer

            Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer


            Stetho.initialize(initializer);

        } catch (Exception e) {

        }



        try {
            TextView hours = (TextView) findViewById(R.id.hours);
            TextView mins = (TextView) findViewById(R.id.mins);
            day = (TextView) findViewById(R.id.day);
            // TextView newuser = (TextView) findViewById(R.id.update);
            amrpm = (TextView) findViewById(R.id.amorpm);
            Button btn = (Button) findViewById(R.id.update);
            Calendar cc = Calendar.getInstance();
            int hrs = cc.get(Calendar.HOUR_OF_DAY);//24
            Log.e("TAG","dskjah"+hrs);
            int min = cc.get(Calendar.MINUTE);//59
            Log.e("TAG","ssss"+min);
            hours.setText(String.valueOf(hrs));
            mins.setText(String.valueOf(min));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,NewUser.class);
                    startActivity(i);
                }
            });

            if (cc.get(Calendar.AM_PM) == 0) {
                AMPM = "AM";
            } else {
                AMPM = "PM";
            }

        } catch (Exception e) {

        }
        amrpm.setText(AMPM);
        TextView date = (TextView) findViewById(R.id.date);
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dd = df.format(c.getTime());

        date.setText(dd);

        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        day.setText(dayOfTheWeek);

    }



    }


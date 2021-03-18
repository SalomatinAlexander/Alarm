package com.example.gpsalarm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gpsalarm.R;
import com.example.gpsalarm.alarm.RiseAndShine;

import java.util.Date;

public class RunningActivity extends AppCompatActivity {
    RiseAndShine rh = new RiseAndShine(this);
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;
    TextView tvEnabledNet;
    TextView tvStatusNet;
    TextView tvLocationNet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_running);
        Button stopBtn = findViewById(R.id.stop_btn);
        startAll();
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAll();
            }
        });



    }

    public void stopAll() {
        rh.stopAudio();
        rh.stopVibration();

    }


    public void startAll() {
        rh.startAudio();
        rh.startVibration();

    }

}


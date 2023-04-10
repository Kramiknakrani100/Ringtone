package com.swaminarayanbhagwan.ringtone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final int FOREGROUND_SERVICE_ID = 1;

    Button start;
    Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        Intent intent = new Intent(this, MyBackgroundService.class);
        // Start the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        start.setOnClickListener(v->{

        });

        stop.setOnClickListener(v->{
            stopService(intent);
        });

    }
}
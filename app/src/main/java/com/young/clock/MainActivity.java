package com.young.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart;
    Button btnStop;
    YoungClock youngClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        youngClock = findViewById(R.id.young_clock);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                youngClock.startClock();
                break;
            case R.id.stop:
                youngClock.stopClock();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        youngClock.startClock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        youngClock.stopClock();
    }
}

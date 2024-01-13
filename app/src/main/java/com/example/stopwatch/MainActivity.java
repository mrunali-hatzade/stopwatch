package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds;
    private boolean running, wasrunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if there is a saved instance state
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasrunning = savedInstanceState.getBoolean("wasrunning");
        }

        runTimer();
    }

    public void onStart(View view) {
        running = true;
    }

    public void onStop(View view) {
        running = false;
    }

    public void onReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasrunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasrunning", wasrunning);
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.textView);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }

                // Post the handler with a delay of 1000 milliseconds (1 second)
                handler.postDelayed(this, 1000);
            }
        });
    }
}

package com.example.counter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton settings;
    TextView txtCounter;
    Button btnUp, btnMinus;

    Setup setup;
    Vibrator vibrator = null;
    MediaPlayer mediaPlayer = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = (FloatingActionButton) findViewById(R.id.flAcBtnSettings);
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        btnUp = (Button) findViewById(R.id.btnAdd);
        btnMinus = (Button) findViewById(R.id.btnDown);

        settings.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        Context context = getApplicationContext();
        setup = Setup.getInstance(context);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(context, R.raw.beeptone);

        btnUp.setOnClickListener(view -> {
            valueUpdate(1);
        });
        btnMinus.setOnClickListener(view -> {
            valueUpdate(-1);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action == KeyEvent.ACTION_DOWN) {
                    valueUpdate(5);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action == KeyEvent.ACTION_DOWN) {
                    valueUpdate(-5);
                }
                return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void valueUpdate(int step) {
        if (step < 0) {
            if (setup.currentValue + step < setup.lowerLimit) {
                setup.currentValue = setup.lowerLimit;
                if(setup.downVol)
                    mediaPlayer.start();
                if(setup.downVib)
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                setup.currentValue += step;
            }
        } else {
            if(setup.currentValue + step > setup.upperLimit && setup.upperLimit != 0) {
                setup.currentValue = setup.upperLimit;
                if(setup.upperVol)
                    mediaPlayer.start();
                if(setup.upperVib)
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else
                setup.currentValue += step;
        }
        txtCounter.setText(String.valueOf(setup.currentValue));
    }

    @Override
    protected void onPause() {
        super.onPause();
        setup.saveValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCounter.setText(String.valueOf(setup.currentValue));
    }
}
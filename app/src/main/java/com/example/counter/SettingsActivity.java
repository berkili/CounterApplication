package com.example.counter;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    TextView txtUpperLimit, txtDownLimit;
    Button btnUpPlus, btnUpMinus, btnDownPlus, btnDownMinus;
    Switch swUpVol, swUpVib, swDownVol, swDownVib;
    int upLimit, downLimit;
    boolean upVol, upVib, downVol, downVib;

    Setup setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Context context = getApplicationContext();
        setup = Setup.getInstance(context);

        btnUpPlus = (Button) findViewById(R.id.btnUpperAdd);
        btnUpMinus = (Button) findViewById(R.id.btnUpperDel);
        btnDownPlus = (Button) findViewById(R.id.btnDownAdd);
        btnDownMinus = (Button) findViewById(R.id.btnDownDel);

        swUpVib = (Switch) findViewById(R.id.swUpperVib);
        swUpVol = (Switch) findViewById(R.id.swUpperSound);
        swDownVib = (Switch) findViewById(R.id.swDownVib);
        swDownVol = (Switch) findViewById(R.id.swDownSound);

        txtUpperLimit = findViewById(R.id.txtUpper);
        txtDownLimit = findViewById(R.id.txtBottom);

        ImageView leftIcon = (ImageView) findViewById(R.id.left_icon);

        leftIcon.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        btnUpPlus.setOnClickListener(view -> {
            setup.upperLimit++;
            txtUpperLimit.setText(String.valueOf(setup.upperLimit));
        });
        btnUpMinus.setOnClickListener(view -> {
            setup.upperLimit--;
            txtUpperLimit.setText(String.valueOf(setup.upperLimit));
        });
        txtUpperLimit.setOnFocusChangeListener((view, hasFocus) -> {
            setup.upperLimit = Integer.valueOf(txtUpperLimit.getText().toString());
        });
        btnDownPlus.setOnClickListener(view -> {
            setup.lowerLimit++;
            txtDownLimit.setText(String.valueOf(setup.lowerLimit));
        });
        btnDownMinus.setOnClickListener(view -> {
            setup.lowerLimit--;
            txtDownLimit.setText(String.valueOf(setup.lowerLimit));
        });
        txtDownLimit.setOnFocusChangeListener((view, hasFocus) -> {
            setup.lowerLimit = Integer.valueOf(txtDownLimit.getText().toString());
        });
        swUpVol.setOnCheckedChangeListener((view, hasFocus) -> {
            setup.upperVol = Boolean.valueOf(swUpVol.isChecked());
        });
        swUpVib.setOnCheckedChangeListener((view, hasFocus) -> {
            setup.upperVib = Boolean.valueOf(swUpVib.isChecked());
        });
        swDownVol.setOnCheckedChangeListener((view, hasFocus) -> {
            setup.downVol = Boolean.valueOf(swDownVol.isChecked());
        });
        swDownVib.setOnCheckedChangeListener((view, hasFocus) -> {
            setup.downVib = Boolean.valueOf(swDownVib.isChecked());
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        setup.saveValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtUpperLimit.setText(String.valueOf(setup.upperLimit));
        txtDownLimit.setText(String.valueOf(setup.lowerLimit));
        swUpVib.setChecked(setup.upperVib);
        swUpVol.setChecked(setup.upperVol);
        swDownVib.setChecked(setup.downVib);
        swDownVol.setChecked(setup.downVol);
    }
}
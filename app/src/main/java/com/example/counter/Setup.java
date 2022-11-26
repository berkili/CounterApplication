package com.example.counter;

import android.content.Context;
import android.content.SharedPreferences;

public class Setup {
    int upperLimit = 0, lowerLimit = 0, currentValue = 0;
    boolean upperVib = false, downVib = false, downVol = false, upperVol = false;

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    static Setup setup = null;
    private Setup(Context context) {
        this.context= context;
        preferences = this.context.getSharedPreferences("counterSetup", Context.MODE_PRIVATE);
        editor = preferences.edit();
        loadValues();
    }
    public static Setup getInstance(Context context) {
        if(setup == null)
            setup = new Setup(context);
        return setup;
    }

    public void saveValue()
    {
        editor.putInt("upperLimit", upperLimit);
        editor.putInt("lowerLimit", lowerLimit);
        editor.putInt("currentValue", currentValue);
        editor.putBoolean("upperVib", upperVib);
        editor.putBoolean("upperVol", upperVol);
        editor.putBoolean("downVib", downVib);
        editor.putBoolean("downVol", downVol);
        editor.commit();
    }

    public void loadValues()
    {
        upperLimit = preferences.getInt("upperLimit",30);
        lowerLimit = preferences.getInt("lowerLimit",0);
        currentValue = preferences.getInt("currentValue",0);
        upperVib = preferences.getBoolean("upperVib", false);
        upperVol = preferences.getBoolean("upperVol", false);
        downVib = preferences.getBoolean("downVib", false);
        downVol = preferences.getBoolean("downVol", false);
    }

}

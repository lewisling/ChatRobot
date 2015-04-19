package com.example.ahtcfg24.isfirstrun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ActivityWelcome extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /*The first param is a String key of the file's name.If the file is not exist,it will be created.
        * the second param is a int mode ,it stands for a mode of the file's write-read way.*/
        SharedPreferences mySharedPreferences = getSharedPreferences("fileName", MODE_PRIVATE);

        init(mySharedPreferences);
        /*Destroy the welcome activity*/
        finish();
    }

    /**
     * @param view There are a Onclick attribute value in the xml,so it also need a View param.
     *             What's more,public and void are required.
     */
    public void goMain(View view)
    {
        startActivity(new Intent(this, Activity_Main.class));
    }

    public void goGuide(View view)
    {
        startActivity(new Intent(this, Activity_Guide.class));
    }

    /**
     * performing during the stage of initiation.Made a decision about which activity will be started.
     *
     * @param mySharedPreferences deliver a SharedPreference file object.
     */
    public void init(SharedPreferences mySharedPreferences)
    {
        /*Get the value of the key :isFirstRun.
        If the isFirstRun's value is not exists,it will be set to true.*/
        if (mySharedPreferences.getBoolean("isFirstRun", true))
        {
            startActivity(new Intent(this, Activity_Guide.class));
            /*The edit() method will return a interface object,so we evaluate it to myEditor.*/
            SharedPreferences.Editor myEditor = mySharedPreferences.edit();
            /*Modified the value to false by calling the putBoolean*/
            myEditor.putBoolean("isFirstRun", false);
            /*After modification,it must be save.*/
            myEditor.apply();
        } else
        {
            startActivity(new Intent(this, Activity_Main.class));
        }
    }
}

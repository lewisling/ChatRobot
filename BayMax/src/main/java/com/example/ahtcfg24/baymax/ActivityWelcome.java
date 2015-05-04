package com.example.ahtcfg24.baymax;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class ActivityWelcome extends Activity
{
    private long DELAY_MILLS = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*Get the Activity fulled on the screen.(It has to be added before setContentView.)*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);


        /*The Handler object is bound to the thread I created.
         * The postDelayed()'s first param is a Runnable object which will be executed after the message queue.
         * The second param is the time of period that is delayed.*/
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                 /*The first param is a String key of the file's name.If the file is not exist,it will be created.
                  * the second param is a int mode ,it stands for a mode of the file's write-read way.*/
                init(getSharedPreferences("fileName", MODE_PRIVATE));
                /*Destroy the welcome activity*/
                finish();
            }
        }, DELAY_MILLS);
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
            startActivity(new Intent(this, ActivityGuide.class));
            /*The edit() method will return a interface object,so we evaluate it to myEditor.*/
            SharedPreferences.Editor myEditor = mySharedPreferences.edit();
            /*Modified the value to false by calling the putBoolean*/
            myEditor.putBoolean("isFirstRun", false);
            /*After modification,it must be save.*/
            myEditor.apply();
        } else
        {
            startActivity(new Intent(this, ActivityLogin.class));
        }
    }
}

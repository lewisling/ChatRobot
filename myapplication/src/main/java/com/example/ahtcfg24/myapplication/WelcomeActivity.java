package com.example.ahtcfg24.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class WelcomeActivity extends Activity
{
    private long delayTime=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);





        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPreferences myPreferences = getSharedPreferences("abc", MODE_PRIVATE);
                if (myPreferences.getBoolean("isFirst", true))
                {
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));

                    SharedPreferences.Editor myEditor = myPreferences.edit();
                    myEditor.putBoolean("isFirst", false);
                    myEditor.apply();

                } else
                {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, delayTime);








    }
}

package com.example.ahtcfg24.baymax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ActivityRegister extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void goMain(View view)
    {
        startActivity(new Intent(ActivityRegister.this, ActivityMain.class));
    }
}

package com.example.ahtcfg24.isfirstrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityMain extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goGuide(View view)
    {
        startActivity(new Intent(this, ActivityGuide.class));
    }
}

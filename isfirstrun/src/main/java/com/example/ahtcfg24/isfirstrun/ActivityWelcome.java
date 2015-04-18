package com.example.ahtcfg24.isfirstrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityWelcome extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
}

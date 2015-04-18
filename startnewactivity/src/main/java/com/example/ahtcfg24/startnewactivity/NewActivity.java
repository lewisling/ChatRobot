package com.example.ahtcfg24.startnewactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class NewActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /*We need input a param about it's parent object*/
        TextView textView = new TextView(this);
        super.onCreate(savedInstanceState);
        /*We don't need a layout*/
        setContentView(textView);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
    }
}

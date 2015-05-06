package com.example.ahtcfg24.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * <p>Description: </p>
 *
 * @author XuDing
 * @version 1.0
 * @date 2015/4/18 22:38
 */
public class MainActivity extends Activity
{
    /*It is a good practice for define keys for intent's extras using package name as a prefix.
    It ensures our extras are unique. (forgive me using English...23333*/
    public final static String EXTRA_MESSAGE = "com.example.ahtcfg24.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view)
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        /*This is an very concise style of code,we can separate it into several part.*/
        startActivity(new Intent(this, NewActivity.class).putExtra(EXTRA_MESSAGE, editText.getText().toString()));
    }


}

package com.example.ahtcfg24.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*匿名内部类访问外部变量，外部变量要用final*/
        final CheckBox checkBox_show = (CheckBox) findViewById(R.id.show_password);
        final EditText edit_password = (EditText) findViewById(R.id.edit_password);

        checkBox_show.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (checkBox_show.isChecked())
            {
                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else
            {
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        Button button_about = (Button) findViewById(R.id.button_about);

        button_about.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));
    }
}

package com.example.ahtcfg24.baymax;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * <p>Description: </p>
 *
 * @author XuDing
 * @version 1.0
 * @date 2015/5/3
 */

public class ActivityLogin extends Activity
{
    SharedPreferences.Editor user_editor;
    private EditText edit_username;
    private SharedPreferences userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        userPreference = getSharedPreferences("user", MODE_PRIVATE);

        readUserPreference();
        final CheckBox checkBox_show = (CheckBox) findViewById(R.id.show_password);
        final EditText edit_password = (EditText) findViewById(R.id.edit_password);

        edit_username.setText(userPreference.getString("edit_username", null));
        edit_password.setText(userPreference.getString("password", null));
        checkBox_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (checkBox_show.isChecked())
                {
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                {
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public void goAbout(View view) {startActivity(new Intent(ActivityLogin.this, ActivityAbout.class));}

    public void goRegister(View view)
    {
        startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
    }

    public void goMain(View view)
    {
        startActivity(new Intent(ActivityLogin.this, ActivityMain.class));
        saveUserPreference();

    }

    public void saveUserPreference()
    {
        user_editor = userPreference.edit();
        EditText password = (EditText) findViewById(R.id.edit_password);
        user_editor.putString("edit_username", edit_username.getText().toString());
        user_editor.putString("password", password.getText().toString());
        user_editor.apply();
    }

    public void readUserPreference()
    {

    }
}

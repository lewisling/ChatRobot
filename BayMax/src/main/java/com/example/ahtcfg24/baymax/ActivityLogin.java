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
    private EditText edit_username;
    private EditText edit_password;
    private SharedPreferences userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final CheckBox checkBox_show = (CheckBox) findViewById(R.id.show_password);
        final EditText editText_password = (EditText) findViewById(R.id.edit_password);

        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = editText_password;

        readUserPreference(edit_username, edit_password);

        checkBox_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (checkBox_show.isChecked())
                {
                    editText_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                {
                    editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        SharedPreferences.Editor user_editor = userPreference.edit();
        user_editor.putString("local_username", edit_username.getText().toString());
        user_editor.putString("local_password", edit_password.getText().toString());
        user_editor.apply();
    }

    public void readUserPreference(EditText edit_username, EditText edit_password)
    {
        userPreference = getSharedPreferences("user", MODE_PRIVATE);
        String username = userPreference.getString("local_username", null);
        String password = userPreference.getString("local_password", null);
        edit_username.setText(username);
        edit_password.setText(password);
    }
}

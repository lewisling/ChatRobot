package cn.iamding.chatrobot.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.globals.MyURL;
import cn.iamding.chatrobot.globals.MyVariable;

import static cn.iamding.chatrobot.netutils.URLNetUtil.getByURLConnection;
import static cn.iamding.chatrobot.netutils.URLNetUtil.regexOutput;

public class MainActivity extends AppCompatActivity {
    private EditText inputEditText;
    private Button sendButton;
    private TextView chatTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputData = inputEditText.getText().toString();
                inputEditText.setText("");
                String httpArg = null;
                //URL中编码会变，因此要处理
                try {
                    httpArg = "key=" + MyVariable.TULING_APIKEY + "&info=" + URLEncoder.encode(inputData, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String httpURL = MyURL.TULING + "?" + httpArg;
                //访问网络前需要先对网络是否可用进行检验，用到权限查看network
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new MyAysTask().execute(httpURL);
                } else {
                    Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络连接后重试", Toast.LENGTH_SHORT).show();
                }

                //不能在主线程中使用网络相关的功能
            }
        });

    }

    private void initView() {
        inputEditText = (EditText) findViewById(R.id.input_edittext);
        sendButton = (Button) findViewById(R.id.send_button);
        chatTextView = (TextView) findViewById(R.id.chat_textview);
    }


    private class MyAysTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] urls) {
            try {
                return getByURLConnection(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "无法访问该URL";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            chatTextView.setText(regexOutput(result));
        }
    }
}

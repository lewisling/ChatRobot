package cn.iamding.chatrobot.Activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.turing.androidsdk.HttpRequestWatcher;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.TuringApiConfig;
import com.turing.androidsdk.TuringApiManager;
import com.turing.androidsdk.constant.Constant;
import com.turing.androidsdk.tts.TTSListener;
import com.turing.androidsdk.tts.TTSManager;
import com.turing.androidsdk.voice.VoiceRecognizeListener;
import com.turing.androidsdk.voice.VoiceRecognizeManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.globals.MyURL;
import cn.iamding.chatrobot.globals.MyVariable;

import static cn.iamding.chatrobot.netutils.URLNetUtil.getByURLConnection;
import static cn.iamding.chatrobot.netutils.URLNetUtil.regexOutput;

public class MainActivity extends Activity implements VoiceRecognizeListener, TTSListener {
    private EditText inputEditText;
    private Button sendButton;
    private TextView chatTextView;
    private VoiceRecognizeManager voiceRecognizeManager;
    private TTSManager ttsManager;
    private TuringApiManager mTuringApiManager;

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
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new MyAysTask().execute(httpURL);
                } else {
                    Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络连接后重试", Toast.LENGTH_SHORT)
                         .show();
                }

                //不能在主线程中使用网络相关的功能
            }
        });

        initMscAndTTS();

        initTulingApiManager();

        ttsManager.startTTS("你好啊", Constant.XunFei);
    }

    private void initView() {
        inputEditText = (EditText) findViewById(R.id.input_edittext);
        sendButton = (Button) findViewById(R.id.send_button);
        chatTextView = (TextView) findViewById(R.id.chat_textview);
    }

    /**
     * 初始化网络接口管理
     */
    private void initTulingApiManager() {
        TuringApiConfig turingApiConfig = new TuringApiConfig(this, MyVariable.TULING_APIKEY);
        turingApiConfig.init(this, new InitListener() {

            @Override
            public void onFail() {
            }

            @Override
            public void onComplete() {
                // 获取userid成功，此时，才支持主动请求的功能
            }
        });
        mTuringApiManager = new TuringApiManager(turingApiConfig, new HttpRequestWatcher() {

            @Override
            public void onSuceess(String arg0) {

                // api请求内容后，服务器返回数据获取位置
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    if (jsonObject.has("text")) {
                        handler.obtainMessage(1, jsonObject.get("text")).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String arg0) {
            }
        });
    }

    /**
     * 初始化识别和tts
     */
    private void initMscAndTTS() {
        // 识别管理类
        voiceRecognizeManager = new VoiceRecognizeManager(this, this);
        // tts管理类
        ttsManager = new TTSManager(this, this);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ttsManager.startTTS((String) msg.obj, Constant.XunFei);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onSpeechCancel() {
    }

    @Override
    public void onSpeechError(int arg0) {
    }

    @Override
    public void onSpeechFinish() {
        voiceRecognizeManager.startRecognize(Constant.XunFei);
    }

    @Override
    public void onSpeechPause() {
    }

    @Override
    public void onSpeechProgressChanged() {
    }

    @Override
    public void onSpeechStart() {
    }

    @Override
    public void onRecognizeError(String arg0) {
    }

    @Override
    public void onRecognizeResult(String arg0) {
        // 识别到话语后，将其发向服务器，进行语义分析，并回答
        mTuringApiManager.requestTuringAPI(arg0);
    }

    @Override
    public void onRecordEnd() {
    }

    @Override
    public void onRecordStart() {
    }

    @Override
    public void onStartRecognize() {
        // 仅针对百度识别有效
    }

    @Override
    public void onVolumeChange(int arg0) {
        // 仅针对调用讯飞时有效
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

package cn.iamding.chatrobot.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.globals.MyVariable;

public class MainActivity extends Activity {
    private ListView chatList;
    private Button sendButton;
    private ImageButton voiceButton;
    private EditText inputEdit;
    private VoiceRecognizeManager voiceRecognizeManager;
    private TTSManager ttsManager;
    private Handler handler = new Handler() {
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
    private TuringApiManager mTuringApiManager;
    private MyTTSListener myTTSListener;
    private MyVoiceRecognizeListener myVoiceRecognizeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initMscAndTTS();
        initTuringApiManager();

        ttsManager.startTTS("你好啊", Constant.XunFei);//开始把文本合成语音
    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        chatList = (ListView) findViewById(R.id.chat_list);
        inputEdit = (EditText) findViewById(R.id.input_edit);
        sendButton = (Button) findViewById(R.id.send_button);
        voiceButton = (ImageButton) findViewById(R.id.voice_button);
    }


    /**
     * 初始化语音识别和TTS
     */
    private void initMscAndTTS() {
        myVoiceRecognizeListener = new MyVoiceRecognizeListener();
        myTTSListener = new MyTTSListener();
        voiceRecognizeManager = new VoiceRecognizeManager(this, myVoiceRecognizeListener);
        ttsManager = new TTSManager(this, myTTSListener);
    }

    /**
     * 初始化图灵API网络接口
     */
    private void initTuringApiManager() {
        TuringApiConfig turingApiConfig = new TuringApiConfig(this, MyVariable.TULING_APIKEY);
        turingApiConfig.init(this, new InitListener() {//初始化配置信息，并生成userid
            /**
             * 生成userid成功时调用
             */
            @Override
            public void onComplete() {
                Log.i("userid", "userid生成成功");
            }

            /**
             * 生成userid失败时调用
             */
            @Override
            public void onFail() {

            }
        });
        mTuringApiManager = new TuringApiManager(turingApiConfig, new HttpRequestWatcher() {//根据配置请求网络数据并解析获得的数据

            /**
             * 获取数据成功后解析出其中的text信息
             *
             * @param arg0 从服务器获取到的数据
             */
            @Override
            public void onSuceess(String arg0) {
                try {
                    JSONObject jsonObject = new JSONObject(arg0);
                    if (jsonObject.has("text")) {
                        handler.obtainMessage(1, jsonObject.get("text"))
                               .sendToTarget();//用handler获取解析出来的text
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 获取数据失败时调用
             *
             * @param arg0 失败时得到的信息
             */
            @Override
            public void onError(String arg0) {
                Log.e("onError arg0", arg0);
                Toast.makeText(getApplicationContext(), "获取数据失败，信息：" + arg0, Toast.LENGTH_SHORT)
                     .show();
            }
        });
    }

    /**
     * 发送文字消息
     *
     * @param view sendButton
     */
    public void sendMessage(View view) {
        // TODO: 2015/12/13
    }

    /**
     * 发送语音
     *
     * @param view voiceButton
     */
    public void sendVoice(View view) {
        // TODO: 2015/12/13
    }


    /**
     * TTS机器语音合成监听器，用于监听机器说话的状态
     */
    private class MyTTSListener implements TTSListener {

        /**
         * TTS开始
         */
        @Override
        public void onSpeechStart() {
            Log.i("MyTTSListener", "TTS开始");
        }

        /**
         * TTS结束（文本合成了语音，并且输出了语音）
         */
        @Override
        public void onSpeechFinish() {
            Log.i("MyTTSListener", "TTS完成");
            voiceRecognizeManager.startRecognize(Constant.XunFei);//开始识别用户录音
        }

        /**
         * TTS暂停
         */
        @Override
        public void onSpeechPause() {
            Log.i("MyTTSListener", "TTS暂停");
        }

        /**
         * TTS状态发生了改变
         */
        @Override
        public void onSpeechProgressChanged() {
            Log.i("MyTTSListener", "TTS状态改变");
        }

        /**
         * 机器语音合成(TTS)被取消，（仅对百度TTS有效）
         */
        @Override
        public void onSpeechCancel() {
            Log.i("MyTTSListener", "百度TTS被取消了");
        }

        /**
         * TTS出错，（仅对百度TTS有效）
         */
        @Override
        public void onSpeechError(int arg0) {
            Log.i("MyTTSListener", "百度TTS出错" + arg0);
        }

    }

    /**
     * 语音识别监听器，用于识别用户语音
     */
    private class MyVoiceRecognizeListener implements VoiceRecognizeListener {
        /**
         * 语音识别开始监听，此时可以提示用户说话（仅针对实用百度语音识别时有用）
         */
        @Override
        public void onStartRecognize() {
            Log.i("VoiceRecognizeListener", "百度语音开始监听");
        }

        /**
         * 检测到有语音输入
         */
        @Override
        public void onRecordStart() {
            Log.i("VoiceRecognizeListener", "有语音输入");
        }

        /**
         * 检测到语音终点，等待网络识别
         */
        @Override
        public void onRecordEnd() {
            Log.i("VoiceRecognizeListener", "语音输入完毕");
        }

        /**
         * 网络识别结果返回
         *
         * @param arg0 网络识别的结果
         */
        @Override
        public void onRecognizeResult(String arg0) {
            Log.i("VoiceRecognizeListener", "识别结果为：" + arg0);
            mTuringApiManager.requestTuringAPI(arg0);// 识别到话语后，将其发向图灵服务器，进行语义分析
        }

        /**
         * 语音识别出错
         *
         * @param arg0 错误信息
         */
        @Override
        public void onRecognizeError(String arg0) {
            Log.i("VoiceRecognizeListener", "语音识别出错：" + arg0);
        }

        /**
         * 用户说话的音量发生了改变（仅对讯飞有效）
         *
         * @param arg0 音量
         */
        @Override
        public void onVolumeChange(int arg0) {
            Log.i("VoiceRecognizeListener", "音量改变：" + arg0);
        }

    }
}

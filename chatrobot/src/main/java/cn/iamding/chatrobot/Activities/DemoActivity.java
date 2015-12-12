package cn.iamding.chatrobot.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

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

public class DemoActivity extends Activity implements VoiceRecognizeListener,
        TTSListener {

    private VoiceRecognizeManager voiceRecognizeManager;
    private TTSManager ttsManager;
    private TuringApiManager mTuringApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initMscAndTTS();

        initTulingApiManager();

        ttsManager.startTTS("你好啊", Constant.XunFei);
    }

    /**
     * 初始化网络接口管理类
     *
     * @author changjingpei
     * @date 2015年11月13日 下午4:19:30
     */
    private void initTulingApiManager() {
        TuringApiConfig turingApiConfig = new TuringApiConfig(this,
                "590d17782e8beebbc9efcb60b018b2c2");
        turingApiConfig.init(this, new InitListener() {

            @Override
            public void onFail() {
            }

            @Override
            public void onComplete() {
                // 获取userid成功，此时，才支持主动请求的功能
            }
        });
        mTuringApiManager = new TuringApiManager(turingApiConfig,
                new HttpRequestWatcher() {

                    @Override
                    public void onSuceess(String arg0) {

                        // api请求内容后，服务器返回数据获取位置
                        try {
                            JSONObject jsonObject = new JSONObject(arg0);
                            if (jsonObject.has("text")) {
                                handler.obtainMessage(1, jsonObject.get("text"))
                                        .sendToTarget();
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
     *
     * @author changjingpei
     * @date 2015年11月13日 下午4:18:46
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
        };
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

    public void start(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}

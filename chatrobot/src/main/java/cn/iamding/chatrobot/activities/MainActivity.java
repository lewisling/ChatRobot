package cn.iamding.chatrobot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.adapters.ChatListAdapter;
import cn.iamding.chatrobot.globals.MyVariable;
import cn.iamding.chatrobot.model.OneMessage;
import cn.iamding.chatrobot.ui.MyProgressDialog;

public class MainActivity extends AppCompatActivity {
    private MyProgressDialog myProgressDialog;
    private List<OneMessage> messageList;
    private ListView chatList;
    private Button sendButton;
    private ImageButton voiceButton;
    private EditText inputEdit;
    private VoiceRecognizeManager voiceRecognizeManager;
    private TTSManager ttsManager;
    private ChatListAdapter chatListAdapter;
    private TuringApiManager mTuringApiManager;
    private MyTTSListener myTTSListener;
    private MyVoiceRecognizeListener myVoiceRecognizeListener;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ttsManager.startTTS((String) msg.obj, Constant.BaiDu);
                    messageList.add(new OneMessage(OneMessage.From.NET, (String) msg.obj));
                    chatListAdapter.notifyDataSetChanged();
                    chatList.setSelection(messageList.size() - 1);
                    break;
                case 2:
                    messageList.add(new OneMessage(OneMessage.From.NET, (String) msg.obj,2));
                    chatListAdapter.notifyDataSetChanged();
                    chatList.setSelection(messageList.size() - 1);
                case 3:
                    messageList.add(new OneMessage(OneMessage.From.NET, (String) msg.obj,3));
                    chatListAdapter.notifyDataSetChanged();
                    chatList.setSelection(messageList.size() - 1);
                default:
                    break;
            }
        }
    };

    /**
     * 自定义ActionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 定义ActionBar选项被选之后的动作
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        initMscAndTTS();
        initTuringApiManager();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return isFirstRun();
            }

            @Override
            protected void onPostExecute(String s) {
                ttsManager.startTTS(s, Constant.BaiDu);//开始把文本合成语音
                messageList.add(new OneMessage(OneMessage.From.NET, s));
                chatListAdapter = new ChatListAdapter(MainActivity.this, messageList);
                chatList.setAdapter(chatListAdapter);
            }
        }.execute();

    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        chatList = (ListView) findViewById(R.id.chat_list);
        inputEdit = (EditText) findViewById(R.id.input_edit);
        sendButton = (Button) findViewById(R.id.send_button);
        voiceButton = (ImageButton) findViewById(R.id.voice_button);
        messageList = new ArrayList<>();
        chatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 15-12-14 长按消息监听
                return false;
            }
        });

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
            @Override
            public void onComplete() {
                Log.i("userid", "userid生成成功");
            }

            @Override
            public void onFail() {

            }
        });
        mTuringApiManager = new TuringApiManager(turingApiConfig, new MyHttpRequestWatcher());
    }

    /**
     * 检测是不是第一次运行
     *
     * @return 第一句要说的话
     */
    private String isFirstRun() {
        String firstWord;
        SharedPreferences mySharedPreferences = getSharedPreferences("fileName", MODE_PRIVATE);
        if (mySharedPreferences.getBoolean("isFirstRun", true)) {
            firstWord = "主人，我美不美呀";
            SharedPreferences.Editor myEditor = mySharedPreferences.edit();
            myEditor.putBoolean("isFirstRun", false);
            myEditor.apply();
        } else {
            firstWord = "你好呀";
        }
        return firstWord;

    }


    /**
     * 点击sendButton发送文字消息
     *
     * @param view sendButton
     */
    public void sendMessage(View view) {
        String inputInfo = inputEdit.getText().toString();
        inputEdit.setText("");
        mTuringApiManager.requestTuringAPI(inputInfo);
        messageList.add(new OneMessage(OneMessage.From.LOCAL, inputInfo));
        chatListAdapter.notifyDataSetChanged();
        chatList.setSelection(messageList.size() - 1);

        // 切换软键盘状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击语音按钮开始录音
     *
     * @param view voiceButton
     */
    public void sendVoice(View view) {
        voiceRecognizeManager.startRecognize(Constant.BaiDu);//开始识别用户录音
        myProgressDialog = new MyProgressDialog(MainActivity.this, "录音中...");
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
            myProgressDialog.dismiss();

        }

        /**
         * 网络识别结果返回
         *
         * @param arg0 网络识别的结果
         */
        @Override
        public void onRecognizeResult(String arg0) {
            Log.i("VoiceRecognizeListener", "识别结果为：" + arg0);
            mTuringApiManager.requestTuringAPI(arg0);// 识别到话语后，将其发向图灵服务器
            messageList.add(new OneMessage(OneMessage.From.LOCAL, arg0));
            chatListAdapter.notifyDataSetChanged();
            chatList.setSelection(messageList.size() - 1);
        }

        /**
         * 语音识别出错
         *
         * @param arg0 错误信息
         */
        @Override
        public void onRecognizeError(String arg0) {
            Log.i("VoiceRecognizeListener", "语音识别出错：" + arg0);
            myProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), arg0, Toast.LENGTH_SHORT).show();
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

    /**
     * 图灵API服务器请求结果监听器
     */
    private class MyHttpRequestWatcher implements HttpRequestWatcher {//根据配置请求网络数据并解析获得的数据

        /**
         * 从图灵服务获取机器人的回复
         *
         * @param arg0 从服务器获取到的数据
         */
        @Override
        public void onSuceess(String arg0) {
            try {
                JSONObject jsonObject = new JSONObject(arg0);
                if (jsonObject.has("text")) {
                    handler.obtainMessage(1, jsonObject.get("text"))
                           .sendToTarget();//用handler获取解析出来的text，标记为1,发送到target(target==this)
                }
                if (jsonObject.has("url")) {
                    handler.obtainMessage(2, jsonObject.get("url")).sendToTarget();
                }if (jsonObject.has("list")){
                    handler.obtainMessage(3,jsonObject.get("list")).sendToTarget();
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
            Toast.makeText(getApplicationContext(), "获取数据失败，信息：" + arg0, Toast.LENGTH_SHORT).show();
        }
    }
}

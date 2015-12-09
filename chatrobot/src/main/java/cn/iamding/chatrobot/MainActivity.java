package cn.iamding.chatrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static cn.iamding.chatrobot.URLNetUtil.input;
import static cn.iamding.chatrobot.URLNetUtil.regexOutput;
import static cn.iamding.chatrobot.URLNetUtil.request;

public class MainActivity extends AppCompatActivity {
    private static final String key = "c8ded35d005d142d84e4efb641b48b9c";
    private static final String baseURL ="http://www.tuling123.com/openapi/api";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        while (true) {
            String info = input();
            String httpArg = "key=" + key + "&info=" + info
//                        + "&userid=" + userid
                    ;
            String httpURL =baseURL+"?"+httpArg;
            if (info.equals("exit")) break;
            else {
                regexOutput(request(httpURL));
            }
        }
    }
}

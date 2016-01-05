package cn.iamding.chatrobot;

import android.app.Application;
import android.content.Context;

/**
 * Created by Xuding on 2016/1/5 16:29
 */
public class MyApplication extends Application {
    private static Context context;

    /**
     * 获得最高生命周期的Context，方便别的类中使用
     *
     * @return Context
     */
    public static Context getContext() {
        return context;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}

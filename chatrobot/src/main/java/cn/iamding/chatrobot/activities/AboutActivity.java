package cn.iamding.chatrobot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.iamding.chatrobot.R;
import cn.iamding.chatrobot.globals.MyVariable;

public class AboutActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<HashMap<String, String>> about_list;
    private HashMap<String, String> map1, map2, map3, map4, map5, map6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        listView = (ListView) findViewById(R.id.about_list);
        about_list = new ArrayList<>();
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        map3 = new HashMap<>();
        map4 = new HashMap<>();
        map5 = new HashMap<>();
        map6 = new HashMap<>();
        map1.put("title", "作者：");
        map1.put("value", MyVariable.NAME);
        map2.put("title", "日期：");
        map2.put("value", "2015/12/14");
        map3.put("title", "版本：");
        map3.put("value", "1.01");
        map4.put("title", "邮箱：");
        map4.put("value", MyVariable.MAIL);
        map5.put("title", "微博：");
        map5.put("value", "点此访问");
        map6.put("title", "博客 ：");
        map6.put("value", "点此访问");
        about_list.add(map1);
        about_list.add(map2);
        about_list.add(map3);
        about_list.add(map4);
        about_list.add(map5);
        about_list.add(map6);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, about_list, R.layout.about_list, new String[]{
                "title", "value"
        }, new int[]{R.id.list_title, R.id.list_value});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 4) {
                    toWeb(MyVariable.SINA_WEIBO);
                }
                if (position == 5) {
                    toWeb(MyVariable.BLOG);
                }
            }
        });
    }


    /**
     * 访问address
     *
     * @param address url
     */
    public void toWeb(String address) {
        Intent ToWeb = new Intent(Intent.ACTION_VIEW);
        ToWeb.setData(Uri.parse(address));
        ToWeb = Intent.createChooser(ToWeb, null);
        startActivity(ToWeb);
    }

}

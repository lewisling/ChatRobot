package cn.iamding.chatrobot.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.iamding.chatrobot.R;

public class AboutActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getActionBar();
        ArrayList<HashMap<String, String>> about_list = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        HashMap<String, String> map4 = new HashMap<>();
        HashMap<String, String> map5 = new HashMap<>();
        HashMap<String, String> map6 = new HashMap<>();
        map1.put("title", "作者：");
        map1.put("value", "绝世盗草人");
        map2.put("title", "日期：");
        map2.put("value", "2015/12/14");
        map3.put("title", "版本：");
        map3.put("value", "1.0");
        map4.put("title", "邮箱：");
        map4.put("value", "ahtcfg24@163.com");
        map5.put("title", "微博：");
        map5.put("value", "点击此处访问");
        map6.put("title", "博客：");
        map6.put("value", "点击此处访问");
        about_list.add(map1);
        about_list.add(map2);
        about_list.add(map3);
        about_list.add(map4);
        about_list.add(map5);
        about_list.add(map6);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, about_list, R.layout.about_list, new String[]{
                "title", "value"
        }, new int[]{R.id.list_title, R.id.list_value});
        setListAdapter(simpleAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position == 4) {
            toWeb("http://m.weibo.cn/u/3180900562?jumpfrom=weibocom");
        }
        if (position == 5) {
            toWeb("http://www.iamding.cn");
        }

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

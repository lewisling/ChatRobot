package com.example.ahtcfg24.baymax;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ahtcfg24 on 2015/3/3.
 * register activity
 */
public class ActivityAbout extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ArrayList<HashMap<String, String>> about_list = new ArrayList<>();
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        HashMap<String, String> map4 = new HashMap<>();
        HashMap<String, String> map5 = new HashMap<>();
        HashMap<String, String> map6 = new HashMap<>();
        HashMap<String, String> map7 = new HashMap<>();
        HashMap<String, String> map8 = new HashMap<>();
        map1.put("title", "开发者：");
        map1.put("value", "徐鼎");
        map2.put("title", "日期：");
        map2.put("value", "2015/3/8");
        map3.put("title", "版本：");
        map3.put("value", "1.01");
        map4.put("title", "邮箱：");
        map4.put("value", "ahtcfg24@163.com");
        map5.put("title", "QQ号：");
        map5.put("value", "896433809");
        map6.put("title", "手机号：");
        map6.put("value", "15600909030");
        map7.put("title", "微信号：");
        map7.put("value", "ahtcfg24");
        map8.put("title", "新浪微博：");
        map8.put("value", "绝世盗草人");
        about_list.add(map1);
        about_list.add(map2);
        about_list.add(map3);
        about_list.add(map4);
        about_list.add(map5);
        about_list.add(map6);
        about_list.add(map7);
        about_list.add(map8);
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                about_list,
                R.layout.about_list,
                new String[]{"title", "value"},
                new int[]{R.id.list_title, R.id.list_value});
        setListAdapter(simpleAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        System.out.println("" + id + " " + position);
        if (position == 7)
            toWeb("http://m.weibo.cn/u/3180900562?jumpfrom=weibocom");
        if(position==5)
            toDial("tel:15600909030");

    }
    public void toDial(String num){
        Intent ToDial=new Intent();
        ToDial.setAction(Intent.ACTION_DIAL);
        ToDial.setData(Uri.parse(num));
        startActivity(ToDial);
    }

    public void toWeb(String address) {
        Intent ToWeb = new Intent(Intent.ACTION_VIEW);
        ToWeb.setData(Uri.parse(address));
        ToWeb = Intent.createChooser(ToWeb, null);
        startActivity(ToWeb);
    }
}


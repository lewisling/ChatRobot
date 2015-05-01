package com.example.ahtcfg24.isfirstrun;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ActivityGuide extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //find the view of ViewPager
        ViewPager myViewPager = (ViewPager) findViewById(R.id.guide_viewpager);

        List<View> view_list = new ArrayList<>();
        //get a LayoutInflater object from default context
        LayoutInflater inflater = getLayoutInflater();

        View view1 = inflater.inflate(R.layout.guide_page1, null);
        View view2 = inflater.inflate(R.layout.guide_page2, null);
        View view3 = inflater.inflate(R.layout.guide_page3, null);

        view_list.add(view1);
        view_list.add(view2);
        view_list.add(view3);

        myViewPager.setAdapter(new AdapterGuide(view_list));
        myViewPager.setCurrentItem(0);
    }


}

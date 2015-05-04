package com.example.ahtcfg24.baymax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ahtcfg24.baymax.adapter.AdapterGuide;

import java.util.ArrayList;
import java.util.List;

public class ActivityGuide extends Activity
{
    private List<View> view_list;
    private int currentIndex;
    private ViewPager myViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initPage();
        initDots();
    }

    public void initPage()
    {
        /*find the view of ViewPager*/
        myViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        /*the list is used to save pages.*/
        view_list = new ArrayList<>();
        /*initial the page resource*/
        int[] resource = {R.layout.guide_page1, R.layout.guide_page2, R.layout.guide_page3};
        /*inflate the pages into the list.*/
        inflatePage(resource);
        /*fill the viewpager*/
        myViewPager.setAdapter(new AdapterGuide(view_list));
    }

    public void initDots()
    {
        /*find dots*/
        LinearLayout dot_layout = (LinearLayout) findViewById(R.id.my_layout);
        final ImageView[] dots = new ImageView[3];
        for (int i = 0; i < 3; i++)
        {
            dots[i] = (ImageView) dot_layout.getChildAt(i);
            dots[i].setEnabled(true);
        }
        //set first dot on highlight
        dots[0].setEnabled(false);
        //set listener
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            /**
             * called when the new page is selected.
             * @param position the number of new page
             */
            @Override
            public void onPageSelected(int position)
            {
                dots[position].setEnabled(false);
                dots[currentIndex].setEnabled(true);
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    public void inflatePage(int[] resource)
    {
        /*get a LayoutInflater object from default context*/
        LayoutInflater inflater = getLayoutInflater();
        for (int aResource : resource)
        {
            view_list.add(inflater.inflate(aResource, null));
        }
    }

    public void goMain(View view)
    {
        startActivity(new Intent(ActivityGuide.this, ActivityLogin.class));
        ActivityGuide.this.finish();
    }
}

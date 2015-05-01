package com.example.ahtcfg24.isfirstrun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityGuide extends Activity
{
    private int currentIndex;

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

        //get three page
        View view1 = inflater.inflate(R.layout.guide_page1, null);
        View view2 = inflater.inflate(R.layout.guide_page2, null);
        View view3 = inflater.inflate(R.layout.guide_page3, null);
        //add the page
        view_list.add(view1);
        view_list.add(view2);
        view_list.add(view3);
        //fill the viewpager
        myViewPager.setAdapter(new AdapterGuide(view_list));
        //  myViewPager.setCurrentItem(0);

        //find dots
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
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

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
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        view3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ActivityGuide.this, ActivityMain.class));
                ActivityGuide.this.finish();
            }
        });
    }

    public void goMain(View view)
    {
        startActivity(new Intent(ActivityGuide.this, ActivityMain.class));
        ActivityGuide.this.finish();
    }
}

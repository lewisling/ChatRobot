package com.example.ahtcfg24.isfirstrun;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>Description: Adapter for loading the page of ViewPager</p>
 *
 * @author XuDing
 * @version 1.0
 * @date 2015/4/29
 */
public class AdapterGuide extends PagerAdapter
{
    private List<View> view_list;

    public AdapterGuide(List<View> view_list)
    {
        this.view_list = view_list;
    }


    /**
     * Create the page for the given position.  The adapter is responsible
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(view_list.get(position));
        return view_list.get(position);
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(view_list.get(position));
    }


    /**
     * Return the number of views available.
     */
    @Override
    public int getCount()
    {
        return view_list.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     */
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}

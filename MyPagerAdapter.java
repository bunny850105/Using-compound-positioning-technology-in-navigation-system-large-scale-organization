package com.itshareplus.googlemapdemo;

/**
 * Created by test on 2017/10/23.
 */

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
public class MyPagerAdapter extends PagerAdapter{
    private ArrayList<View> views;
    private ArrayList<String> titles;
    public MyPagerAdapter(ArrayList<View> views,ArrayList<String> titles){
        this.views = views;
        this.titles = titles;
    }
    @Override
    public int getCount() {
        return this.views.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }
    //標題， 如果不要標題可以去掉這裏
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    //頁面view
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }
}

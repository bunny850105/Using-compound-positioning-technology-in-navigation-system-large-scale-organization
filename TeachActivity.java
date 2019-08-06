package com.itshareplus.googlemapdemo;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
public class TeachActivity extends Activity {
    //翻頁控件
    private ViewPager mViewPager;
    private PagerTitleStrip mPagerTitleStrip;
    //這5個是底部顯示當前狀態點imageView
    private ImageView mPage0;
    private ImageView mPage1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //去掉標題欄全屏顯示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);
        mPagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pagertitle);


        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPage0 = (ImageView)findViewById(R.id.page0);
        mPage1 = (ImageView)findViewById(R.id.page1);


      /*
       * 這裏是每一頁要顯示的布局，根據應用需要和特點自由設計顯示的內容
       * 以及需要顯示多少頁等
       */
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.activity_teach, null);
        View view2 = mLi.inflate(R.layout.activity_teach1, null);

      	/*
      	 * 這裏將每一頁顯示的view存放到ArrayList集合中
      	 * 可以在ViewPager适配器中順序調用展示
      	 */
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);



        /*
      	 * 每個頁面的Title數據存放到ArrayList集合中
      	 * 可以在ViewPager适配器中調用展示
      	 */
        final ArrayList<String> titles = new ArrayList<String>();
        titles.add("校園導航");
        titles.add("演講公告");


        //填充ViewPager的數據适配器
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views,titles);
        mViewPager.setAdapter(mPagerAdapter);
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {


        public void onPageSelected(int page) {
//翻頁時當前page,改變當前狀態园點圖片
            switch (page) {
                case 0:
                    mPage0.setImageDrawable(getResources().getDrawable(R.drawable.a0001));
                    mPage1.setImageDrawable(getResources().getDrawable(R.drawable.a0002));
                    break;

                case 1:
                    mPage1.setImageDrawable(getResources().getDrawable(R.drawable.a0002));
                    mPage0.setImageDrawable(getResources().getDrawable(R.drawable.a0001));
                    break;
            }
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        public void onPageScrollStateChanged(int arg0) {
        }
    }


}

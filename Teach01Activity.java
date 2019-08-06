package com.itshareplus.googlemapdemo;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class Teach01Activity extends Activity {
    final int[] imagesId = new int[]{R.drawable.a0001, R.drawable.a0002};

    ImageSwitcher imageSwitcher;
    Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach01);

        //取得ImageSwitcher和Gallery
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        gallery = (Gallery) findViewById(R.id.gallery);

        //為ImageSwitcher設置ViewFactory，用來處理圖片切換的顯示
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());

                return imageView;
            }
        });
        //為ImageSwitcher設置淡入淡出動畫
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        imageSwitcher.setAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));

        //為Gallery設置Adapter以讓Gallery顯示圖片
        gallery.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return imagesId.length;
            }

            @Override
            public Object getItem(int position) {
                return imagesId[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(imagesId[position]);
                //設定圖片尺寸等比例縮放
                imageView.setAdjustViewBounds(true);
                imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        //為Gallery設置一個OnItemSelectedListener，置於中間的縮圖為被Selected
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //將對應選到的縮圖的大圖放置於ImageSwitcher中
                imageSwitcher.setImageResource(imagesId[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //什麼也不必做
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.itshareplus.googlemapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by test on 2017/7/18.
 */

public class SettingActivity extends FragmentActivity  {
    private GridView gridView;
    private ImageView logo;
    private int[] image = {
            R.drawable.information, R.drawable.news, R.drawable.manual,
            R.drawable.logout
    };
    private String[] imgText = {
            "個人基本資料", "最新公告", "使用說明", "登出"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        logo = (ImageView) findViewById(R.id.imageView11);
        logo.setImageResource(R.drawable.fivemap);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item_setting, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.main_page_gridview);
        gridView.setNumColumns(2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SettingActivity.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent intent = new Intent();
                    intent.setClass(SettingActivity.this, InformationActivity.class);
                    startActivity(intent);
                    //SettingActivity.this.finish();
                }
                if(position==1) {

                    Intent intent = new Intent();
                    intent.setClass(SettingActivity.this, PostActivity.class);
                    startActivity(intent);
                    //SettingActivity.this.finish();
                }

                if(position==2) {
                    Intent intent = new Intent();
                    intent.setClass(SettingActivity.this, Teach01Activity.class);
                    startActivity(intent);
                   // SettingActivity.this.finish();
                }
                if(position==3) {
                    Intent intent = new Intent();
                    intent.setClass(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                   // SettingActivity.this.finish();
                }

            }
        });
    }
}
package com.itshareplus.googlemapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class LookvrActivity extends FragmentActivity  {
    private GridView gridView;
    private ImageView logo;
    private int[] image = {
            R.drawable.management_building, R.drawable.library01, R.drawable.administration_building
    };
    private String[] imgText = {
            "管理學院導覽", "圖書館導覽", "行政大樓導覽"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookvr);
        logo = (ImageView) findViewById(R.id.imageView2);
        logo.setImageResource(R.drawable.fivemap);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.main_page_gridview);
        gridView.setNumColumns(1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LookvrActivity.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("nnndhu.com");
                    startActivity(intent);
                }
                if(position==1) {
                    Intent intent = new Intent();
                    intent.setClass(LookvrActivity.this, SpeechActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==2) {
                    Intent intent = new Intent();
                    intent.setClass(LookvrActivity.this, SettingActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }

            }
        });
    }
}

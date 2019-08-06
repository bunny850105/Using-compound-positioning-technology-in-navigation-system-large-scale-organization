package com.itshareplus.googlemapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class StartMenuActivity extends Activity {
    private GridView gridView;
    private ImageView logo;
    private int[] image = {
            R.drawable.map_128, R.drawable.book_128, R.drawable.school,R.drawable.speecher,R.drawable.bookre,
            R.drawable.setting_128
    };
    private String[] imgText = {
            "東華校園導航", "圖書館導航", "學院導覽","演講資訊公告", "圖書館藏書推薦","設定"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
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
        gridView.setNumColumns(2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(StartMenuActivity.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, MapsActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==1) {
                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, LibraryActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==2) {
                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, LookvrActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==3) {
                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, SpeechGridActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==4) {
                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, RecommendationActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }
                if(position==5) {

                    Intent intent = new Intent();
                    intent.setClass(StartMenuActivity.this, SettingActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }

            }
        });
    }
}



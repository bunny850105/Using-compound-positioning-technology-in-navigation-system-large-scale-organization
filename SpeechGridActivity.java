package com.itshareplus.googlemapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeechGridActivity extends FragmentActivity {
    private GridView gridView;
    private ImageView logo;
    private int[] image = {
            R.drawable.schoolspeech, R.drawable.management
    };
    private String[] imgText = {
            "全校演講", "學院演講"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_grid);
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
                items, R.layout.grid_speech, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.main_page_gridview);
        gridView.setNumColumns(1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpeechGridActivity.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                if(position==0) {
                    Intent intent = new Intent();
                    intent.setClass(SpeechGridActivity.this, SchoolSpeechActivity.class);
                    startActivity(intent);
                }
                if(position==1) {
                    Intent intent = new Intent();
                    intent.setClass(SpeechGridActivity.this, SpeechActivity.class);
                    startActivity(intent);
                    //StartMenuActivity.this.finish();
                }

            }
        });
    }
}

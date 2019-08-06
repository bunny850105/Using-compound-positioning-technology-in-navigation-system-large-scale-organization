package com.itshareplus.googlemapdemo;



import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class SchoolSpeechActivity extends Activity {
    private WebView webView;
    private final String URL = "http://msg.ndhu.edu.tw/mail_page.php?sort=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_speech);
        webView = (WebView)this.findViewById(R.id.webView1);
        webView.loadUrl(URL);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }
}

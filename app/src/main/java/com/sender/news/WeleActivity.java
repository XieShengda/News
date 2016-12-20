package com.sender.news;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class WeleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wele);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!connectivityManager.getActiveNetworkInfo().isAvailable()){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WeleActivity.this, NewsActivity.class);
                WeleActivity.this.startActivity(intent);
                WeleActivity.this.finish();
            }
        }, 2000);
    }
}

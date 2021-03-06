package com.yq.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yq.customview.activity.DhActivity;
import com.yq.customview.activity.ProgressActivity;
import com.yq.customview.activity.RectActivity;
import com.yq.customview.activity.RulerActivity;
import com.yq.customview.activity.UpdateActivity;
import com.yq.customview.activity.XiaoMiActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_dh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DhActivity.class));
            }
        });
        findViewById(R.id.btn_rect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RectActivity.class));
            }
        });

        findViewById(R.id.btn_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProgressActivity.class));
            }
        });

        findViewById(R.id.btn_ruler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RulerActivity.class));
            }
        });

        findViewById(R.id.btn_xiaomi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XiaoMiActivity.class));
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpdateActivity.class));
            }
        });
    }
}

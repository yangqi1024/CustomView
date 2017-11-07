package com.yq.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yq.customview.R;
import com.yq.customview.view.DhView;

/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class RectActivity extends AppCompatActivity {

    private DhView mDh;
    private boolean isCheck;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);
    }
}

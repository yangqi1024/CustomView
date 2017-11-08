package com.yq.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yq.customview.R;
import com.yq.customview.bean.RectBean;
import com.yq.customview.view.RectView;

import java.util.ArrayList;

/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class RectActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);
        ArrayList<RectBean> rectBeans = new ArrayList<>();
        rectBeans.add(new RectBean("froyo", 10));
        rectBeans.add(new RectBean("GB", 30));
        rectBeans.add(new RectBean("iCS", 30));
        rectBeans.add(new RectBean("JB", 210));
        rectBeans.add(new RectBean("kitkat", 400));
        rectBeans.add(new RectBean("L", 450));
        rectBeans.add(new RectBean("M", 170));
        RectView rect = findViewById(R.id.rect);
        rect.setData(rectBeans);
    }
}

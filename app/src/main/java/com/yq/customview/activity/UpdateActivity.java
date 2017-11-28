package com.yq.customview.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yq.customview.R;
import com.yq.customview.view.UpdateProgressView;

/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class UpdateActivity extends AppCompatActivity {

    private UpdateProgressView update;
    private ValueAnimator mValueAnimator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        update = findViewById(R.id.upv);

        mValueAnimator = ValueAnimator.ofInt(0, 100);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        mValueAnimator.setDuration(3000);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueAnimator.start();
            }
        });
    }
}

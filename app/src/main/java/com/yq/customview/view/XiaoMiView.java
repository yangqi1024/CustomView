package com.yq.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/22
 * @since V1.0.1
 */
public class XiaoMiView extends View {

    private Paint mPaint;
    private int mRadius;
    private int mCenterX;

    public XiaoMiView(Context context) {
        this(context, null);
    }

    public XiaoMiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoMiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getMeasuredWidth();
        mRadius = width / 2 - 26;
        mCenterX = width / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mRandowRadius = mRadius;
        for (int i = 0; i < 8; i++) {
            Random random = new Random();
            int r = random.nextInt(3);
            mRandowRadius += r;
            int i1 = random.nextInt(2) - 1;
            canvas.drawCircle(mCenterX + i1, mCenterX + i1, mRandowRadius, mPaint);
        }
    }
}

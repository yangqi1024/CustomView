package com.yq.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yq.customview.bean.RectBean;

import java.util.ArrayList;
import java.util.Collections;


/**
 * 直方图
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class RectView extends View {
    private static final String TAG = "RectView";
    private int spaceWidth;
    private int rectWidth;
    private int leftMargin = dip2px(20);
    private int bottomMargin = dip2px(20);
    private Path linePath = new Path();
    private Path rectPath = new Path();
    private Paint mLinePaint;
    private Paint mRectPaint;
    private ArrayList<RectBean> mData;
    private int mMeasuredWidth;
    private int mMeasuredHeight;

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#FFFFFFFF"));
        mLinePaint.setStyle(Paint.Style.STROKE);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.parseColor("#FF72B916"));
    }

    public void setData(ArrayList<RectBean> data) {
        this.mData = data;
        if (mData == null) {
            return;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        linePath.moveTo(leftMargin, bottomMargin);
        linePath.lineTo(leftMargin, mMeasuredHeight - bottomMargin);
        linePath.rLineTo(mMeasuredWidth - 2 * leftMargin, 0);


        int eachItemWidth = (mMeasuredWidth - dip2px(20) - 2 * leftMargin) / mData.size();
        rectWidth = eachItemWidth * 5 / 6;
        spaceWidth = eachItemWidth * 1 / 6;

        RectBean max = Collections.max(mData);
        int count = max.getCount();
        Log.d(TAG, "leftMargin=" + leftMargin + ",spaceWidth=" + spaceWidth + ",eachItemWidth=" + eachItemWidth);
        for (int i = 0; i < mData.size(); i++) {
            RectBean rectBean = mData.get(i);
            rectPath.moveTo(leftMargin + spaceWidth * (i + 1) + rectWidth * i, mMeasuredHeight - bottomMargin);
            rectPath.lineTo(leftMargin + spaceWidth * (i + 1) + rectWidth * i, mMeasuredHeight - bottomMargin - (mMeasuredHeight - dip2px(20) - 2 * bottomMargin) * rectBean
                    .getCount() / count);
            rectPath.lineTo(leftMargin + (spaceWidth + rectWidth) * (i + 1), mMeasuredHeight - bottomMargin - (mMeasuredHeight - dip2px(20) - 2 * bottomMargin) * rectBean
                    .getCount() / count);
            rectPath.lineTo(leftMargin + (spaceWidth + rectWidth) * (i + 1), mMeasuredHeight - bottomMargin);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(linePath, mLinePaint);
        canvas.drawPath(rectPath, mRectPaint);
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

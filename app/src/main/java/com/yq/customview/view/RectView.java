package com.yq.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yq.customview.bean.RectBean;

import java.util.ArrayList;


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
    private Paint mLinePaint;
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
    }

    public void setData(ArrayList<RectBean> data) {
        this.mData = data;
        if (mData == null) {
            return;
        }
        int eachItemWidth = (mMeasuredWidth - dip2px(20)) / mData.size();
        rectWidth = eachItemWidth * 5 / 6;
        spaceWidth = eachItemWidth * 1 / 6;
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
        linePath.lineTo(leftMargin, mMeasuredHeight - 2 * bottomMargin);
        linePath.rLineTo(mMeasuredWidth - 2 * leftMargin, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(linePath, mLinePaint);
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

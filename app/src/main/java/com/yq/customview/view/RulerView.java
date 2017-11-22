package com.yq.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;


/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/22
 * @since V1.0.1
 */
public class RulerView extends View {
    private int mOffset = 0;
    private int mItemWidth = dip2px(10);
    private int mItemCount = 10;
    private int mTotalCount = 100;
    private Paint mPaint;
    private float mStartX;
    private int mMViewWight;
    private VelocityTracker mVelocityTracker;
    private OverScroller mOverScroller;
    private int mScaledMaximumFlingVelocity;
    private int mScaledMinimumFlingVelocity;
    private Path mPath;
    private int triangleHeight = dip2px(10);

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initScrooler();
        initTrianglePath();
    }

    private void initTrianglePath() {
        mPath = new Path();
    }

    private void initScrooler() {
        mOverScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mScaledMaximumFlingVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mScaledMinimumFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 把事件交给 VelocityTracker 处理
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                //记录事件
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                mOffset += mStartX - moveX;
                mStartX = moveX;
                mOffset = mOffset > 0 ? mOffset > mTotalCount * mItemWidth ? mTotalCount * mItemWidth : mOffset : 0;
//                Log.d("RulerView", "mOffset=" + mOffset);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //计算当前速度
                mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
//                Log.d("RulerView", "xVelocity=" + xVelocity);
                if (Math.abs(xVelocity) > mScaledMinimumFlingVelocity) {
                    Log.d("RulerView", "xVelocity=" + xVelocity);
                    mOverScroller.fling(mOffset, 0, -xVelocity / 3, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                    invalidate();
                } else {
                    adjust();
                    invalidate();
                }
                //VelocityTracker回收
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                //VelocityTracker回收
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMViewWight = getMeasuredWidth();
        mItemCount = mMViewWight / mItemWidth + 1;
        mPath.moveTo(mMViewWight / 2 - triangleHeight / 2, 0);
        mPath.lineTo(mMViewWight / 2 + triangleHeight / 2, 0);
        mPath.lineTo(mMViewWight / 2, triangleHeight / 2);
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            mOffset = mOverScroller.getCurrX();
            mOffset = mOffset > 0 ? mOffset > mTotalCount * mItemWidth ? mTotalCount * mItemWidth : mOffset : 0;
            Log.d("RulerView", "mOffset=" + mOffset);
            adjust();
            invalidate();
        }
    }


    private void adjust() {
        int des = mOffset % mItemWidth > mItemWidth / 2 ? mItemWidth - mOffset % mItemWidth : -mOffset % mItemWidth;
        mOverScroller.startScroll(mOffset, 0, des, 0);
    }

    private void drawTriangle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取第一个刻度
        int distance = mMViewWight / 2 - mOffset;
        int left = 0;
        if (distance < 0) {
            left = -distance / mItemWidth;
        }

//        Log.d("RulerView", "index=" + index);
        int startX = mMViewWight / 2 - mOffset + left * mItemWidth;
//        Log.d("RulerView", "startX=" + startX);
        int index = left;
        for (int i = 0; i <= ((mTotalCount - left) < (mMViewWight / mItemWidth) ? mTotalCount - left : (mMViewWight / mItemWidth)); i++) {
            int x = startX + i * mItemWidth;
            if (index % 10 == 0) {
                mPaint.setColor(Color.GRAY);
                mPaint.setStrokeWidth(4);
                canvas.drawLine(x, 0, x, 60, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize(25);
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(String.valueOf(index), x, 100, mPaint);
            } else {
                mPaint.setColor(Color.GRAY);
                mPaint.setStrokeWidth(2);
                canvas.drawLine(x, 0, x, 30, mPaint);
            }
            index++;
        }
        drawTriangle(canvas);
    }

    //初始化画笔
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

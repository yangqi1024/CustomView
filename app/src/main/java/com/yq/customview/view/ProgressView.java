package com.yq.customview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/8
 * @since V1.0.1
 */
public class ProgressView extends View {

    private Paint mSrcPaint;
    private Paint mTextPaint;
    private int mProgress;
    private int mWidth;
    private int padding = dip2px(5);
    private RectF rect = new RectF();
    private Rect mTextRect = new Rect();
    private ObjectAnimator mObjectAnimator;
    private boolean isAnimIng = false;
    private int mCenterX;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        setStart();
    }

    private void initPaint() {
        mSrcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSrcPaint.setColor(Color.parseColor("#FFE41C63"));
        mSrcPaint.setStyle(Paint.Style.STROKE);
        mSrcPaint.setStrokeWidth(dip2px(10));
        mSrcPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#FFFFFFFF"));
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(45);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int width = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        int radiu = mWidth / 2 - padding;
        mCenterX = mWidth / 2;
        rect.set(padding, padding, mWidth - padding, mWidth - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, -90, mProgress, false, mSrcPaint);
        String text = String.valueOf(Math.rint(mProgress * 100 / 360) + "%");
        mTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
        canvas.drawText(text, mCenterX - mTextRect.width() / 2, mCenterX + mTextRect.height() / 2, mTextPaint);
        if (!isAnimIng) {
            isAnimIng = true;
            mObjectAnimator.start();
        }
    }

    public void setStart() {
        isAnimIng = false;
        mObjectAnimator = ObjectAnimator.ofInt(this, "progress", 0, 360);
        mObjectAnimator.setDuration(2000);
        postInvalidate();
    }

    private void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

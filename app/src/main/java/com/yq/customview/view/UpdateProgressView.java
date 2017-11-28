package com.yq.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yq.customview.R;


/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/27
 * @since V1.0.1
 */
public class UpdateProgressView extends View {

    private Paint mPaint;
    private int progress_height;
    private int progress_weight = dip2px(200);
    private int text_margin_top = dip2px(3);
    private float each_progress_weight;
    private float offset_left;
    private int mProgress = 0;
    private RectF mProgressRectF;
    private RectF mBgRectF;

    private Rect mTextRect = new Rect();
    private float mTextSize;
    private int mTextColor;
    private int mProgressBg;

    public UpdateProgressView(Context context) {
        this(context, null);
    }

    public UpdateProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpdateProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initPaint();
        initRectF();
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UpdateProgressView);
        mTextColor = ta.getColor(R.styleable.UpdateProgressView_text_color, Color.parseColor("#008eff"));
        mProgressBg = ta.getColor(R.styleable.UpdateProgressView_progress_bg, Color.parseColor("#ffffff"));
        mTextSize = ta.getDimension(R.styleable.UpdateProgressView_text_size, sp2px(12));
        progress_height = (int) ta.getDimension(R.styleable.UpdateProgressView_progress_height, dip2px(8));
        ta.recycle();
    }

    private void initRectF() {
        mProgressRectF = new RectF();
        mBgRectF = new RectF();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredWidth = getMeasuredWidth();
        String text = "100%";
        mPaint.getTextBounds(text, 0, text.length(), mTextRect);
        offset_left = mTextRect.width() * 1.0f / 2;
        progress_weight = measuredWidth - mTextRect.width();
        each_progress_weight = progress_weight * 1.0f / 100;
        mBgRectF.set(offset_left, 0, 100 * each_progress_weight + offset_left, progress_height);
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        this.mProgress = progress;
        mProgressRectF.set(offset_left, 0, progress * each_progress_weight + offset_left, progress_height);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        mPaint.setColor(mProgressBg);
        canvas.drawRoundRect(mBgRectF, progress_height / 2, progress_height / 2, mPaint);
        mPaint.setColor(mTextColor);
        canvas.drawRoundRect(mProgressRectF, progress_height / 2, progress_height / 2, mPaint);
        String text = mProgress + "%";
        mPaint.getTextBounds(text, 0, text.length(), mTextRect);
        canvas.drawText(text, mProgress * each_progress_weight + offset_left - mTextRect.width() / 2 - dip2px(1), progress_height + mTextRect
                .height() + text_margin_top, mPaint);
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

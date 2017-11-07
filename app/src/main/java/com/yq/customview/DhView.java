package com.yq.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * 对号View
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class DhView extends View {
    private static final String TAG = "DhView";
    private static final String TAG1 = "DhView1";
    private int mRadius;
    private Paint mPaintRing;
    private Paint mPaintTick;
    private Paint mPaintCircle;
    private RectF mRect = new RectF();
    private int defaultPaintWidth = dip2px(2);
    private int scale = 2;
    private int tickRadiusOffset = dip2px(4);
    private int mTickRadius = dip2px(12);
    private float[] mPoints = new float[8];
    private boolean mIsChecked = false;

    private int mCenterX;
    private int mCenterY;
    private int mRingProgress;
    private int mCircleRadius = -1;
    private int mTickAlpha;
    private int duringTime = 1000;
    private boolean isAnimationRunning;
    private AnimatorSet mAnimatorSet;

    public DhView(Context context) {
        this(context, null);
    }

    public DhView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DhView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initAnim() {
        ObjectAnimator ringProgress = ObjectAnimator.ofInt(this, "ringProgress", 0, 360);
        ringProgress.setDuration(duringTime);
        ringProgress.setInterpolator(null);
        ObjectAnimator circleRadius = ObjectAnimator.ofInt(this, "circleRadius", mRadius - 5, 0);
        circleRadius.setInterpolator(new DecelerateInterpolator());
        circleRadius.setDuration(500);

        ObjectAnimator tickAlpha = ObjectAnimator.ofInt(this, "tickAlpha", 0, 255);
        circleRadius.setDuration(200);

        ObjectAnimator ringStrokeWidth = ObjectAnimator.ofFloat(this, "ringStrokeWidth", mPaintRing.getStrokeWidth(), mPaintRing
                .getStrokeWidth() * scale, mPaintRing
                .getStrokeWidth() / scale);
        circleRadius.setDuration(500);
        AnimatorSet alphaAndScaleAnimatorSet = new AnimatorSet();
        alphaAndScaleAnimatorSet.playTogether(tickAlpha, ringStrokeWidth);
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(ringProgress, circleRadius, alphaAndScaleAnimatorSet);
    }

    private void setRingStrokeWidth(float ringStrokeWidth) {
        Log.d(TAG, "ringStrokeWidth=" + ringStrokeWidth);
        mPaintRing.setStrokeWidth(ringStrokeWidth);
        postInvalidate();
    }

    private float getRingStrokeWidth() {
        return mPaintRing.getStrokeWidth();
    }

    private void setTickAlpha(int tickAlpha) {
        mPaintTick.setAlpha(tickAlpha);
        postInvalidate();
    }

    private int getTickAlpha() {
        return 0;
    }

    private void setCircleRadius(int circleRadius) {
        this.mCircleRadius = circleRadius;
        postInvalidate();
    }

    private int getCircleRadius() {
        return mCircleRadius;
    }

    private void setRingProgress(int ringProgress) {
        this.mRingProgress = ringProgress;
        postInvalidate();
    }

    private int getRingProgress() {
        return mRingProgress;
    }


    private void initPaint() {

        mPaintRing = new Paint();
        mPaintRing.setARGB(255, 242, 242, 242);
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setStrokeWidth(defaultPaintWidth);


        mPaintTick = new Paint();
        mPaintTick.setARGB(255, 242, 242, 242);
        mPaintTick.setAntiAlias(true);
        mPaintTick.setStyle(Paint.Style.STROKE);
        mPaintTick.setStrokeWidth(defaultPaintWidth);
//        mPaintTick.setAlpha(mIsChecked ? 0 : 255);

        //mPaintTick.setARGB(255, 239, 209, 87);
        mPaintCircle = new Paint();
        mPaintCircle.setARGB(255, 255, 255, 255);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureWidth(getMeasuredWidth() + defaultPaintWidth * scale * 2, widthMeasureSpec);
        int height = measureHeight(getMeasuredHeight() + defaultPaintWidth * scale * 2, heightMeasureSpec);
        mRadius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
        width = height = Math.max(width, height);
        setMeasuredDimension(width, height);
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
        mRect.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        Log.d(TAG1, "onMeasureRect.=" + mRect);
        mPoints[0] = mCenterX - mTickRadius + tickRadiusOffset;
        mPoints[1] = (float) mCenterY;
        mPoints[2] = mCenterX - mTickRadius / 2 + tickRadiusOffset;
        mPoints[3] = mCenterY + mTickRadius / 2;
        mPoints[4] = mCenterX - mTickRadius / 2 + tickRadiusOffset;
        mPoints[5] = mCenterY + mTickRadius / 2;
        mPoints[6] = mCenterX + mTickRadius / 2 + tickRadiusOffset;
        mPoints[7] = mCenterY - mTickRadius / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsChecked) {
            canvas.drawArc(mRect, 90, 360, false, mPaintRing);
            canvas.drawLines(mPoints, mPaintTick);
            return;
        }
//        画圆弧
        mPaintRing.setARGB(255, 239, 209, 87);
        canvas.drawArc(mRect, 90, mRingProgress, false, mPaintRing);
        Log.d(TAG1, "mRingProgress.=" + mRingProgress);
        //画黄色背景
        mPaintCircle.setARGB(255, 239, 209, 87);
        canvas.drawCircle(mCenterX, mCenterY, mRingProgress == 360 ? mRadius : 0, mPaintCircle);
        if (mRingProgress == 360) {

            mPaintCircle.setARGB(255, 255, 255, 255);
            canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mPaintCircle);
        }

        if (mCircleRadius == 0) {
            canvas.drawLines(mPoints, mPaintTick);
            canvas.drawArc(mRect, 90, 360, false, mPaintRing);
        }

        //ObjectAnimator动画替换计数器
        if (!isAnimationRunning) {
            isAnimationRunning = true;
            mAnimatorSet.start();
        }
    }


    public void setChecked(boolean isChecked) {
        if (this.mIsChecked != isChecked) {
            mIsChecked = isChecked;
            reset();
        }
    }

    private void reset() {
        initPaint();
        isAnimationRunning = false;
        mAnimatorSet.cancel();
        mRingProgress = 0;
        mCircleRadius = -1;
        invalidate();
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                defaultWidth = size;
            default:
                break;

        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = size;
            default:
                break;

        }
        return defaultHeight;
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

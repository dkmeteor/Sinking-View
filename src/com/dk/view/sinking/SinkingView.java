package com.dk.view.sinking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SinkingView extends FrameLayout {
    private static final int DEFAULT_TEXTCOLOT = 0xff0074a2;
    private static final int DEFAULT_TEXTSIZE = 32;
    private float mPercent;
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private Bitmap mScaledBitmap;
    private float mLeft, mTop;
    private int mSpeed = 10;
    private int mRepeatCount = 0;
    private Status mFlag = Status.NONE;
    private int mTextColot = DEFAULT_TEXTCOLOT;
    private int mTextSize = DEFAULT_TEXTSIZE;

    public SinkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTextColor(int color) {
        mTextColot = color;
    }

    public void setTextSize(int size) {
        mTextSize = size;
    }

    public void setPercent(float percent) {
        mFlag = Status.RUNNING;
        mPercent = percent;
        postInvalidate();

    }

    public void setStatus(Status status) {
        mFlag = status;
    }

    public void clear() {
        mFlag = Status.NONE;
        if (mScaledBitmap != null) {
            mScaledBitmap.recycle();
            mScaledBitmap = null;
        }

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mFlag == Status.RUNNING) {
            if (mScaledBitmap == null) {
                mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.wave);
                mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), getHeight(), false);
                mBitmap.recycle();
                mBitmap = null;
                mRepeatCount = (int) Math.ceil(getWidth() / mScaledBitmap.getWidth() + 0.5) + 1;
            }

            for (int idx = 0; idx < mRepeatCount; idx++) {
                canvas.drawBitmap(mScaledBitmap, mLeft + (idx - 1) * mScaledBitmap.getWidth(), -mPercent * getHeight(), null);
            }
            String str = (int) (mPercent * 100) + "%";
            mPaint.setColor(mTextColot);
            mPaint.setTextSize(mTextSize);
            canvas.drawText(str, (getWidth() - mPaint.measureText(str)) / 2, getHeight() / 2 + mTextSize / 2, mPaint);

            mLeft += mSpeed;
            if (mLeft >= mScaledBitmap.getWidth())
                mLeft = 0;
            postInvalidateDelayed(20);
        }

    }

    public enum Status {
        RUNNING, NONE
    }

}

package com.dk.view.sinking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SinkingView extends FrameLayout {
    private float mPercent;
    private Paint mPaint;
    private Bitmap mBitmap;
    private float mLeft, mTop;

    public SinkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask);
    }

    public void setPercent(float percent) {
        mPercent = percent;
        postInvalidate();
    }

    public void clear() {

    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawBitmap(mBitmap, mLeft, -mBitmap.getHeight() * mPercent, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}

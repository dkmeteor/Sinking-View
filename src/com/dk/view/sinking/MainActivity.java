package com.dk.view.sinking;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainActivity extends Activity {
    private SinkingView mSinkingView;
    private float percent = 0;
    private DisplayImageOptions options;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSinkingView = (SinkingView) findViewById(R.id.sinking);
        mImage = (ImageView) findViewById(R.id.image);
        initImageLoader();

        findViewById(R.id.btn_local).setOnClickListener(mOnclClickListener);
        findViewById(R.id.btn_network).setOnClickListener(mOnclClickListener);

    }

    private View.OnClickListener mOnclClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btn_local:
                localTest();
                break;
            case R.id.btn_network:
                displayNetwordImage();
                break;
            }
        }
    };

    private void localTest() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                percent = 0;
                while (percent <= 1) {
                    mSinkingView.setPercent(percent);
                    percent += 0.01f;
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mSinkingView.clear();
            }
        });
        thread.start();
    }

    private void displayNetwordImage() {

        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().displayImage("http://img.izhuti.cn/public/picture/2012122401/1348137920992.jpg", mImage, null, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                mSinkingView.clear();
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                mSinkingView.clear();
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                mSinkingView.clear();
            }
        }, new ImageLoadingProgressListener() {

            @Override
            public void onProgressUpdate(String arg0, View arg1, int arg2, int arg3) {
                if (arg3 != -1) {
                    mSinkingView.setPercent(arg2 * 1.0f / arg3);
                }
            }
        });
    }

    /**
     * disable caches
     */
    private void initImageLoader() {
        options = new DisplayImageOptions.Builder().cacheOnDisc(true).cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.charming).showImageOnFail(R.drawable.charming).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(options).discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().clearMemoryCache();
    }
}

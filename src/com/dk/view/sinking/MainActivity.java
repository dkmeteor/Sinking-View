package com.dk.view.sinking;


import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private SinkingView mSinkingView;
    private float percent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSinkingView = (SinkingView) findViewById(R.id.sinking);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    mSinkingView.setPercent(percent);
                    percent += 0.01f;
                    if (percent >= 1) {
                        percent = 0;
                    }

                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

}

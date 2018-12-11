package com.example.pluto.cars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int MIN_DISTANCE = 100;
    private static final int MAX_OFF_PATH = 200;
    private static final int THRESHOLD_VELOCITY = 100;
    private GestureDetector mGestureDetector;
    private Button mBuyButton;
    private Button mSellButton;
    private TextView mWelcomeTextView;
    private TextView sellText;
    private TextView buytext;
    private ImageView mswipeimg;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this, new SwipeDetector());

// Set touch listener to parent view of activity layout
// Make sure that setContentView is called before setting touch listener.
        findViewById(R.id.linear).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Let gesture detector handle the event
                return mGestureDetector.onTouchEvent(event);
            }
        });



        mWelcomeTextView = (TextView)findViewById(R.id.txtViewWelcome);
        sellText = (TextView)findViewById(R.id.sell);
        buytext = (TextView)findViewById(R.id.buy);
        mswipeimg =(ImageView)findViewById(R.id.img);

    }
    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 != null && e2 != null) {
                float dy = e1.getY() - e2.getY();
                float dx = e1.getX() - e2.getX();

                // Right to Left swipe
                if (dx > MIN_DISTANCE && Math.abs(dy) < MAX_OFF_PATH &&
                        Math.abs(velocityX) > THRESHOLD_VELOCITY) {
                    startActivity(new Intent(MainActivity.this, SellActivity.class));
                    return true;
                }

                // Left to right swipe
                else if (-dx > MIN_DISTANCE && Math.abs(dy) < MAX_OFF_PATH &&
                        Math.abs(velocityX) > THRESHOLD_VELOCITY) {
                    // Below is sample code to show left to right swipe while launching next activity
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                    return true;
                }
            }
            return false;
        }
    }

}

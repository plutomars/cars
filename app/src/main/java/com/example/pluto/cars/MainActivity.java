package com.example.pluto.cars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private Button mBuyButton;
    private Button mSellButton;
    private TextView mWelcomeTextView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBuyButton = (Button) findViewById(R.id.btnBuy);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                Bundle extras = new Bundle();
                startActivity(intent);
            }
        });

        mSellButton = (Button)findViewById(R.id.btnSell);
        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SellActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        mWelcomeTextView = (TextView)findViewById(R.id.txtViewWelcome);

    }


}

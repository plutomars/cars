package com.example.pluto.cars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    public static String value;
    public static String value1;
    public static String value2;
    public static String value3;
    public static String value4;
    @BindView(R.id.make_title) TextView makeTitle;
    @BindView(R.id.make_info) TextView makeInfo;
    @BindView(R.id.model_title) TextView modelTitle;
    @BindView(R.id.model_info) TextView modelInfo;
    @BindView(R.id.year_title) TextView yearTitle;
    @BindView(R.id.year_info) TextView yearInfo;
    @BindView(R.id.price_title) TextView priceTitle;
    @BindView(R.id.price_info) TextView priceInfo;
    @BindView(R.id.mileage_title) TextView mileageTitle;
    @BindView(R.id.mileage_info) TextView mileageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        Bundle extras = getIntent().getExtras();
        getIntent().getStringArrayListExtra("year");
        if(extras !=null) {
            value = extras.getString("make");
            value1 = extras.getString("model");
            value2 = extras.getString("year");
            value3 = extras.getString("price");
            value4 = extras.getString("mileage");
            makeInfo = (TextView) findViewById(R.id.make_info);
            makeInfo.setText(value);
            modelInfo = (TextView)findViewById(R.id.model_info);
            modelInfo.setText(value1);
            yearInfo = (TextView)findViewById(R.id.year_info);
            yearInfo.setText(value2);
            yearInfo = (TextView)findViewById(R.id.price_info);
            yearInfo.setText(value3);
            yearInfo = (TextView)findViewById(R.id.mileage_info);
            yearInfo.setText(value4);
        }
    }

    @OnClick({R.id.make_title,R.id.make_info,R.id.model_info,R.id.model_title,R.id.year_info,R.id.year_title,R.id.price_info,R.id.price_title,R.id.mileage_title,R.id.mileage_info})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.make_title:
            case R.id.make_info:
                Intent intent = new Intent(SearchActivity.this, MakeActivity.class);
                Bundle extras = new Bundle();
                startActivity(intent);

                break;

            case R.id.model_info:
            case R.id.model_title:
                Intent model = new Intent(SearchActivity.this, ModelActivity.class);
                startActivity(model);
                finish();
                break;

            case R.id.year_title:
            case R.id.year_info:
                Intent year = new Intent(SearchActivity.this, YearActivity.class);
                startActivity(year);
                finish();

                break;

            case R.id.price_info:
            case R.id.price_title:
                Intent price = new Intent(SearchActivity.this, PriceActivity.class);
                startActivity(price);
                finish();
                break;

            case R.id.mileage_info:
            case R.id.mileage_title:
                Intent mileage = new Intent(SearchActivity.this, MakeActivity.class);
                startActivity(mileage);
                finish();
                break;
        }
    }

}

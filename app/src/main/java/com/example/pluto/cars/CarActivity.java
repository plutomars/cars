package com.example.pluto.cars;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import Model.MyImage;
import SQLite.model.CarImage;

public class CarActivity extends AppCompatActivity {

    private static final String EXTRA_CARID="carid";
    private ViewPager viewPager;
    private List<MyImage> imageList;

    private TextView mMakeInfo;
    private TextView mModelInfo;
    private TextView mYearInfo;
    private TextView mPriceInfo;
    private TextView mMileageInfo;
    private TextView mOwnerInfo;

//    public static Intent newIntent(Context context){
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        viewPager = findViewById(R.id.car_image_pager);

        mMakeInfo = findViewById(R.id.car_make_info);
        mModelInfo = findViewById(R.id.car_model_info);
        mYearInfo = findViewById(R.id.car_year_info);
        mPriceInfo = findViewById(R.id.car_price_info);
        mMileageInfo = findViewById(R.id.car_mileage_info);
        mOwnerInfo = findViewById(R.id.car_owner_info);




    }
}

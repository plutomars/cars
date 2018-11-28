package com.example.pluto.cars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

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
    }

    @OnClick({R.id.make_title,R.id.make_info,R.id.model_info,R.id.model_title,R.id.year_info,R.id.year_title,R.id.price_info,R.id.price_title,R.id.mileage_title,R.id.mileage_info})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.make_title:
            case R.id.make_info:

                break;

            case R.id.model_info:
            case R.id.model_title:

                break;

            case R.id.year_title:
            case R.id.year_info:
                Intent intent = new Intent();

                break;

            case R.id.price_info:
            case R.id.price_title:

                break;

            case R.id.mileage_info:
            case R.id.mileage_title:

                break;
        }
    }

}

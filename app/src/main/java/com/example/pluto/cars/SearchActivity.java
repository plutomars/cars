package com.example.pluto.cars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import Model.Constant;
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
    private static String defaultMake="All";
    private String make="";
    private String model="";
    private String price="";
    private String mileage="";
    private String year="";
    private Intent mIntent;
    private static final String TAG="SearchActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){

            case 100:
                make = data.getExtras().getString("make");
                makeInfo.setText(make);
                break;
            case 101:
                setIntent(data);
                make = data.getExtras().getString("make");
                model = data.getExtras().getString("model");
                makeInfo.setText(make);
                modelInfo.setText(model);
                break;
            case 102:
                String year = data.getExtras().getString("year");
                yearInfo.setText(year);
                break;
            case 103:
                String price = data.getExtras().getString("price");
                priceInfo.setText(price);
                break;
            case 104:
                String mileage = data.getExtras().getString("mileage");
                mileageInfo.setText(mileage);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        make=defaultMake;
        model = defaultMake;
        makeInfo.setText(make);
        modelInfo.setText(model);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"newIntent enter");
        setIntent(intent);

        Log.d(TAG,make+"@");
        Log.d(TAG,model+"@");
        make= intent.getStringExtra("make");
        model = intent.getStringExtra("model");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume enter");
        mIntent=getIntent();

        make = mIntent.getStringExtra("make");
        model = mIntent.getStringExtra("model");
        if(TextUtils.isEmpty(make)){
            makeInfo.setText(defaultMake);
        }else{
            makeInfo.setText(make);
        }
        if(TextUtils.isEmpty(model)){
            modelInfo.setText(defaultMake);
        }else{
            modelInfo.setText(model);
        }
        Log.d(TAG,make+"@");
        Log.d(TAG,model+"@");

    }

    @OnClick({R.id.make_title,R.id.make_info,R.id.model_info,R.id.model_title,R.id.year_info,R.id.year_title,R.id.price_info,R.id.price_title,R.id.mileage_title,R.id.mileage_info,R.id.search_car})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.make_title:
            case R.id.make_info:
                Intent makeIntent = new Intent(SearchActivity.this, MakeActivity.class);
                startActivity(makeIntent);
                break;

            case R.id.model_info:
            case R.id.model_title:
                mIntent = new Intent(SearchActivity.this, ModelActivity.class);
                Log.d(TAG,make);
                if(!TextUtils.isEmpty(make)){
                    Bundle bundle = new Bundle();
                    bundle.putString("make",make);
                    bundle.putInt("single",1);
                    mIntent.putExtras(bundle);
                }
                startActivityForResult(mIntent,Constant.ModelRequestCode);
                break;

            case R.id.year_title:
            case R.id.year_info:
                Intent yearIntent = new Intent(SearchActivity.this,YearActivity.class);
                startActivityForResult(yearIntent,Constant.YearRequestCode);
                break;

            case R.id.price_info:
            case R.id.price_title:
                Intent priceIntent = new Intent(SearchActivity.this,PriceActivity.class);
                startActivityForResult(priceIntent,Constant.PriceRequestCode);
                break;

            case R.id.mileage_info:
            case R.id.mileage_title:
                Intent mileageIntent = new Intent(SearchActivity.this, MileageActivity.class);
                startActivityForResult(mileageIntent,Constant.MileageRequestCode);
                break;
            case R.id.search_car:
                Intent searchIntent = new Intent(SearchActivity.this,ResultActivity.class);
                Bundle allBundle = new Bundle();
                allBundle.putString("make",make);
                allBundle.putString("model",model);
                allBundle.putString("year",year);
                allBundle.putString("price",price);
                allBundle.putString("mileage",mileage);
                searchIntent.putExtras(allBundle);
//                startActivity(searchIntent,allBundle);
                startActivity(searchIntent);
                break;
        }
    }
}

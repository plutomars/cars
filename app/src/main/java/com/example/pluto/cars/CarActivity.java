package com.example.pluto.cars;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;

import Model.Car;
import Model.MyImage;
import SQLite.helper.MyDBHelper;
import SQLite.model.CarImage;
import SQLite.model.CarSchema;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarActivity extends AppCompatActivity {

    //private static final String EXTRA_CARID="carid";
    private static final String TAG="CarActivity";

    private List<MyImage> imageList;
    private Intent mIntent;

    @BindView(R.id.car_make_info_title) TextView makeTitle;
    @BindView(R.id.car_make_info) TextView makeInfo;
    @BindView(R.id.car_model_info_title) TextView modelTitle;
    @BindView(R.id.car_model_info) TextView modelInfo;
    @BindView(R.id.car_year_info_title) TextView yearTitle;
    @BindView(R.id.car_year_info) TextView yearInfo;
    @BindView(R.id.car_price_info_title) TextView priceTitle;
    @BindView(R.id.car_price_info) TextView priceInfo;
    @BindView(R.id.car_mileage_info_title) TextView mileageTitle;
    @BindView(R.id.car_mileage_info) TextView mileageInfo;
    @BindView(R.id.car_owner_info_title) TextView ownerTitle;
    @BindView(R.id.car_owner_info) TextView ownerInfo;
    @BindView(R.id.car_image_pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        mIntent=getIntent();
        Car car = (Car)mIntent.getSerializableExtra("Car");
        //String carid = mIntent.getStringExtra("car_id");
        //Log.d(TAG,carid);
//        Car car = null;
//        car = mIntent.getSerializableExtra("Car");


        //CarSchema carSchema = new CarSchema(this);

        //Car car = CarSchema.getTestCar();
        Log.d(TAG,car.getCarid());

        makeInfo.setText(car.getMake());
        modelInfo.setText(car.getModel());
        yearInfo.setText(String.valueOf(car.getYear()));
        priceInfo.setText(String.valueOf(car.getPrice()));
        mileageInfo.setText(String.valueOf(car.getMileage()));
        ownerInfo.setText(car.getOwner());

        Log.d(TAG,String.valueOf(car.getImages().size()));

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return CarImageFragment.newInstance(car.getImages().get(i));
            }

            @Override
            public int getCount() {
                return car.getImages().size();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mIntent=getIntent();
//        Car car = (Car)mIntent.getSerializableExtra("Car");
    }


}

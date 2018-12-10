package com.example.pluto.cars;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import Adapter.CategoryAdapter;
import Adapter.SearchResultAdapter;
import Model.Car;
import Model.CategorySingleton;
import Model.Constant;
import Model.DividerItemDecoration;
import SQLite.model.CarSchema;
import Utils.PjUtils;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Intent mIntent;
    private static int position;
    private String make="";
    private String model="";
    private String price="";
    private String mileage="";
    private int i_price =0;
    private int i_mileage=0;
    private String year="";
    private static final String TAG="ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_list);
        mIntent = getIntent();
        make = mIntent.getStringExtra("make");
        model = mIntent.getStringExtra("model");
        year = mIntent.getStringExtra("year");
        price = mIntent.getStringExtra("price");
        mileage = mIntent.getStringExtra("mileage");

        try{
            i_price=Integer.parseInt(price);
        }catch (NumberFormatException ne){
            i_price=Constant.MAX_PRICE;
        }

        try{
            i_mileage=Integer.parseInt(mileage);
        }catch (NumberFormatException ne){
            i_mileage=Constant.MILEAGE;
        }

        Log.d(TAG,"year="+year);
        String[] yearArr = PjUtils.getYear(year);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        CarSchema carSchema = new CarSchema(this);
        Log.d(TAG,String.valueOf(yearArr.length));
        Cursor cursor = carSchema.queryCar(make,model,i_price,i_mileage,yearArr);
        List<Car> carList = carSchema.getCarList(cursor);
        SearchResultAdapter SR = new SearchResultAdapter(this,carList);

        SR.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent getCarIntent = new Intent();
                //mIntent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Car",carList.get(position));
                Log.d(TAG,"Position="+String.valueOf(position));
                //bundle.putString("car_id",carList.get(position).getCarid());
                Car testCar = carList.get(position);
                Log.d(TAG,"Test carid ="+testCar.getImages().get(0).getCarid());
                Log.d(TAG,"Test imgno="+String.valueOf(testCar.getImages().get(0).getImg_no()));
                Log.d(TAG,String.valueOf(testCar.getImages().get(0).getImage().length));
                getCarIntent.putExtras(bundle);
                //mIntent.putExtra("car_id",carList.get(position).getCarid());
                getCarIntent.setClass(ResultActivity.this, CarActivity.class);

                Toast.makeText(ResultActivity.this,"going",Toast.LENGTH_SHORT).show();
                startActivity(getCarIntent);
            }
        });

        recyclerView.setAdapter(SR);

    }
}
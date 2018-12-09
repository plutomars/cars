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
import Model.DividerItemDecoration;
import SQLite.model.CarSchema;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Intent mIntent;
    private static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String make = getIntent().getExtras().getString("make");
//        String model = getIntent().getExtras().getString("model");
//        String year = getIntent().getExtras().getString("year");
//        String price = getIntent().getExtras().getString("price");
//        String mileage = getIntent().getExtras().getString("mileage");
        setContentView(R.layout.search_result_list);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        CarSchema carSchema = new CarSchema(this);
        Cursor cursor = carSchema.queryCar("Honda","Civic",99999,999999, new String[]{"2016","2017"});
        List<Car> carList = carSchema.getCarList(cursor);
        SearchResultAdapter SR = new SearchResultAdapter(this,carList);


        SR.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mIntent = getIntent();
                mIntent.setClass(ResultActivity.this, CarActivity.class);
                Toast.makeText(ResultActivity.this,"going",Toast.LENGTH_SHORT).show();
                startActivity(mIntent);

            }
        });

        recyclerView.setAdapter(SR);

    }
}
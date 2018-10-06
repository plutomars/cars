package com.example.pluto.cars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Map;

import Model.Category;
import SQLite.model.CarDB;


public class TestActivity extends AppCompatActivity {
    private static final String TAG="TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //loadingData();

    }
    private void loadingData(){
        CarDB db = new CarDB(this);
        List<Category> categoryList = CarDB.getAllMake();
        Map<String,List<Category>> modelList = CarDB.getAllModels();
//        for(String s:modelList.keySet()){
//            for(Category c : modelList.get(s)){
//                Log.d(TAG,s+","+c.getType()+","+c.getCategoryName());
//            }
//        }
    }




}

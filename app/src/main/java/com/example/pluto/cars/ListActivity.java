package com.example.pluto.cars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import Adapter.CategoryAdapter;
import Model.CategorySingleton;
import Model.DividerItemDecoration;

public class ListActivity extends AppCompatActivity {
    private static final String TAG="ListActivity";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"create a recyclerview");
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        Log.d(TAG,"create a singleton");
        CategoryAdapter ca = new CategoryAdapter(this,categorySingleton.getCategoryList());
        Log.d(TAG,"create a recycler adapter");
        recyclerView.setAdapter(ca);
        Log.d(TAG,"set the adapter to the recycler view");
    }
}

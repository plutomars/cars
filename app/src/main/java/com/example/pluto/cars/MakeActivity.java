package com.example.pluto.cars;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import Adapter.CategoryAdapter;
import Model.CategorySingleton;
import Model.DividerItemDecoration;

public class MakeActivity extends AppCompatActivity {
    private static final String TAG="MakeActivity";
    private RecyclerView recyclerView;
    private static String choosenMake = "";
    private static final String EXTRA_MAKE="make";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"create a recyclerview");
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        Log.d(TAG,"create a singleton");
        CategoryAdapter ca = new CategoryAdapter(this,categorySingleton.getCategoryList());
        Log.d(TAG,"create a recycler adapter");
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MakeActivity.this,"show",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(ca);
        Log.d(TAG,"set the adapter to the recycler view");
    }
}

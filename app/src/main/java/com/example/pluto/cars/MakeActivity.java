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
    public static String choosenMake = "";
    private static final String EXTRA_MAKE="make";
    private static int position;
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
        Intent intent = new Intent(MakeActivity.this, ModelActivity.class);
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosenMake = String.valueOf(ca.getValue(position));
                intent.putExtra("make",choosenMake);
                Toast.makeText(MakeActivity.this,choosenMake,Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(ca);

        Log.d(TAG,"set the adapter to the recycler view");
    }
}

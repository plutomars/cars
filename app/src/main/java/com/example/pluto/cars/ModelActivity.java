package com.example.pluto.cars;

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

public class ModelActivity extends AppCompatActivity {

    private static final String TAG="ModelActivity";
    private RecyclerView recyclerView;
    private static String choosenModel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"create a recyclerview");
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        Log.d(TAG,"create a singleton");
//        CategoryAdapter ca = new CategoryAdapter(this,categorySingleton.getCategoryList());
        Bundle extras = getIntent().getExtras();
        String make = extras.getString("make");
        CategoryAdapter ca = new CategoryAdapter(this, categorySingleton.getModelsByMake(make));
        Log.d(TAG,"create a recycler adapter");
        recyclerView.setAdapter(ca);
        Log.d(TAG,"set the adapter to the recycler view");
        Intent intent = new Intent(ModelActivity.this, YearActivity.class);
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosenModel = String.valueOf(ca.getValue(position));
                intent.putExtra("model",choosenModel);
                intent.putExtra("make",make);
                Toast.makeText(ModelActivity.this,choosenModel,Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
    }
}

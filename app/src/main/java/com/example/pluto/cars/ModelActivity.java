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
import Model.Constant;

public class ModelActivity extends AppCompatActivity {

    private static final String TAG="ModelActivity";
    private RecyclerView recyclerView;
    private static String choosenModel = "All";
    private Intent mIntent;
    private String passMake="All";
    private int singleCall=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"create a recyclerview");
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        Log.d(TAG,"create a singleton");
        mIntent= getIntent();
        passMake = mIntent.getStringExtra("make");
        singleCall = mIntent.getIntExtra("single",0);
        CategoryAdapter ca = new CategoryAdapter(this, CategorySingleton.getModelsByMake(passMake));
        Log.d(TAG,"create a recycler adapter");
        recyclerView.setAdapter(ca);
        Log.d(TAG,"set the adapter to the recycler view");
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosenModel = String.valueOf(ca.getValue(position));

                Bundle bundle = new Bundle();
                bundle.putString("make",passMake);
                bundle.putString("model",choosenModel);
                mIntent.putExtras(bundle);
                Log.d(TAG,String.valueOf(singleCall));
                if(singleCall==0){
                    Log.d(TAG,"enter single call=0");
                    mIntent.setClass(ModelActivity.this,SearchActivity.class);
                    startActivity(mIntent);
                }else{
                    setResult(Constant.ModelRequestCode,mIntent);
                }
                ModelActivity.this.finish();
            }
        });
    }
}

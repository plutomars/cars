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

import org.xml.sax.helpers.LocatorImpl;

import Adapter.CategoryAdapter;
import Model.CategorySingleton;
import Model.Constant;
import Model.DividerItemDecoration;

public class MakeActivity extends AppCompatActivity {
    private static final String TAG="MakeActivity";
    private RecyclerView recyclerView;
    public static String choosenMake = "";
    private static final String EXTRA_MAKE="make";
    private static int position;
    private Intent mIntent;
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
        CategoryAdapter ca = new CategoryAdapter(this,CategorySingleton.getCategoryList());
        Log.d(TAG,"create a recycler adapter");
        //Log.d(TAG,getCallingActivity().getClassName());
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosenMake = String.valueOf(ca.getValue(position));
                mIntent = getIntent();
                Log.d(TAG,String.valueOf(mIntent.getIntExtra("source",0)));
                if(mIntent.getIntExtra("source",0)==Constant.SellActivity){

                    Log.d(TAG,"enter sellactivity call");
                    Bundle bundle = new Bundle();
                    bundle.putString("make",choosenMake);
                    mIntent.putExtras(bundle);
                    setResult(Constant.MakeRequestCode,mIntent);
                    MakeActivity.this.finish();
                    return;
                }
                mIntent.setClass(MakeActivity.this,ModelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("make",choosenMake);
                bundle.putInt("single",0);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
        recyclerView.setAdapter(ca);

        Log.d(TAG,"set the adapter to the recycler view");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause enter");
        mIntent=getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("make",choosenMake);
        mIntent.putExtras(bundle);

    }
}

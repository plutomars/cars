package com.example.pluto.cars;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.YearAdapter;
import Model.DividerItemDecoration;
import Model.YearObj;
import Model.YearSingleton;

public class YearActivity extends AppCompatActivity implements YearAdapter.OnItemClickListener {

    private static final String TAG="YearActivity";
    private RecyclerView recyclerView;
    private List<YearObj> yearObjList = new ArrayList<>();
    private YearAdapter yearAdapter = null;
    private Button applyButton;
    private List<YearObj> selection = new ArrayList<>();
    private Intent mIntent;
    private String make;
    private String model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        init();
        mIntent = new Intent(YearActivity.this, PriceActivity.class);
        Bundle extras = getIntent().getExtras();
         make = extras.getString("make");
         model = extras.getString("model");
    }

    private void init(){
        applyButton = (Button)findViewById(R.id.apply_btn);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("make",make);
                mIntent.putExtra("model",model);
                mIntent.putExtra("year",yearConvert(selection));
                startActivity(mIntent);
                Log.d(TAG,Integer.toString(selection.size()));
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.year_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        YearSingleton yearSingleton = YearSingleton.getInstance(this);
        yearAdapter = new YearAdapter(this,YearSingleton.getYearObjList());
        recyclerView.setAdapter(yearAdapter);
        yearAdapter.setOnItemClickListener(this);


    }
    public String yearConvert(List<YearObj> selection){
        StringBuilder sb = new StringBuilder();
        for(YearObj year:selection){
            sb.append(year.getYear());
            sb.append(",");
        }
        return sb.toString();
    }
    @Override
    public void onItemClickListener(int position, List<YearObj> yearObjList) {
        YearObj yearObj = yearObjList.get(position);

        boolean isSelected = yearObj.isSelected;
        if(!isSelected){
            yearObj.isSelected=true;
            selection.add(yearObj);

        }else{
            yearObj.isSelected=false;
            selection.remove(yearObj);

        }
        yearAdapter.notifyDataSetChanged();

    }

}

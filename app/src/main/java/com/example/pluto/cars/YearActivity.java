package com.example.pluto.cars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        init();
    }

    private void init(){
        applyButton = (Button)findViewById(R.id.apply_btn);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

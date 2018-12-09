package com.example.pluto.cars;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.CategorySingleton;
import Model.Constant;
import Model.PriceObj;


public class PriceActivity extends Activity {
    private ListView listView;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.activity_listview,CategorySingleton.getPriceObjList());

        listView = (ListView) findViewById(R.id.list_price);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPrice =(listView.getItemAtPosition(position).toString());
                mIntent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("price",selectedPrice);
                mIntent.putExtras(bundle);
                setResult(Constant.PriceRequestCode,mIntent);
                PriceActivity.this.finish();
            }
        });

    }
}

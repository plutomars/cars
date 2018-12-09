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
import Model.PriceObj;


public class PriceActivity extends Activity {
    private ListView listView;
    private Intent mIntent;
    private String make;
    private String model;
    private String year;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        mIntent = new Intent(PriceActivity.this, MileageActivity.class);
        Bundle extras = getIntent().getExtras();
        make = extras.getString("make");
        model = extras.getString("model");
        year = extras.getString("year");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,CategorySingleton.getPriceObjList());

        listView = (ListView) findViewById(R.id.list_price);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPrice =(listView.getItemAtPosition(position).toString());
                Toast.makeText(PriceActivity.this, selectedPrice, Toast.LENGTH_LONG).show();
                mIntent.putExtra("make",make);
                mIntent.putExtra("model",model);
                mIntent.putExtra("year",year);
                mIntent.putExtra("price",selectedPrice);
                startActivity(mIntent);

            }
        });

    }
}

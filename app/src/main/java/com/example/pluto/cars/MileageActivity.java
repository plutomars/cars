package com.example.pluto.cars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import Model.CategorySingleton;


public class MileageActivity extends Activity {
    private ListView listView;
    private Intent mIntent;
    private String make;
    private String model;
    private String year;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);

        mIntent = new Intent(MileageActivity.this, SearchActivity.class);
        Bundle extras = getIntent().getExtras();
        make = extras.getString("make");
        model = extras.getString("model");
        year = extras.getString("year");
        price = extras.getString("price");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview2,CategorySingleton.getMileageObjList());

        listView = (ListView) findViewById(R.id.list_mileage);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMileage =(listView.getItemAtPosition(position).toString());
                Toast.makeText(MileageActivity.this, selectedMileage, Toast.LENGTH_LONG).show();
                mIntent.putExtra("make",make);
                mIntent.putExtra("model",model);
                mIntent.putExtra("year",year);
                mIntent.putExtra("price",price);
                mIntent.putExtra("mileage",selectedMileage);
                startActivity(mIntent);

            }
        });

    }
}

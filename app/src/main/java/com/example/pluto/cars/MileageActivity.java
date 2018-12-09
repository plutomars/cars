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
import Model.Constant;


public class MileageActivity extends Activity {
    private ListView listView;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);
        CategorySingleton categorySingleton = CategorySingleton.getInstance(this);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview2,CategorySingleton.getMileageObjList());

        listView = (ListView) findViewById(R.id.list_mileage);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMileage =(listView.getItemAtPosition(position).toString());
                mIntent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("mileage",selectedMileage);
                mIntent.putExtras(bundle);
                setResult(Constant.MileageRequestCode,mIntent);
                MileageActivity.this.finish();
            }
        });

    }
}

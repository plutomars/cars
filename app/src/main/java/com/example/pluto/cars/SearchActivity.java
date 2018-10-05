package com.example.pluto.cars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.ListAdapter;

public class SearchActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mListAdapter;
    private static final String TAG = "SearchActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mListView = (ListView)findViewById(R.id.listViewItems);

        List<Map<String,String>> itemList = new ArrayList<Map<String,String>>();
        String[] criteria = getResources().getStringArray(R.array.Search_criteria);

        for (int i =0;i<criteria.length;i++){
            Map<String,String> item = new HashMap<String,String>();
            item.put("Item type",criteria[i]);
            item.put("Item value","All");
            itemList.add(item);
        }

        mListAdapter = new ListAdapter(this,itemList);
        mListView.setAdapter(mListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"You press"+position);
            }
        });
    }
}

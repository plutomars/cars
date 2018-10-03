package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pluto.cars.R;

import java.util.List;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater mLayInflat;
    List<Map<String,String>> mItemList;

    public ListAdapter(Context context,List<Map<String,String>> itemList){
        mLayInflat= LayoutInflater.from(context);
        mItemList=itemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mLayInflat.inflate(R.layout.search_item,parent,false);

        TextView mTextViewType = (TextView)v.findViewById(R.id.textViewType);
        TextView mTextViewValue = (TextView)v.findViewById(R.id.textViewValue);
        mTextViewType.setText(mItemList.get(position).get("Item type").toString());
        mTextViewValue.setText(mItemList.get(position).get("Item value").toString());

        return v;
    }
}

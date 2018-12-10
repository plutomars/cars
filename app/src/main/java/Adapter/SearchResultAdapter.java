package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pluto.cars.R;

import java.util.List;

import Model.Car;
import Model.Category;
import Model.Item_ViewHolder;
import Model.ListResult_ViewHolder;
import Model.Title_ViewHolder;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mInflater;
    private List<Car> mListItems;
    private static final String TAG="SearchResultAdapter";
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);

    }

    public SearchResultAdapter(Context context, List<Car> listItems){
        mListItems = listItems;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View result_view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresult, parent, false);
        return new ListResult_ViewHolder(result_view);
        //return new Title_ViewHolder(car_view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            Car c = mListItems.get(position);
            ListResult_ViewHolder h =(ListResult_ViewHolder)viewHolder;
            h.SetMake(String.format("%s %s %s",c.getMake(),c.getModel(),c.getYear()));
            h.SetMileage(String.format("%s %s","Mileage",String.valueOf(c.getMileage())));
            h.SetPrice(String.format("%s %s","$",String.valueOf(c.getPrice())));
            if(mOnItemClickListener!=null){
                h.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = h.getLayoutPosition();
                        mOnItemClickListener.onItemClick(h.itemView,position);
                    }
                });
            }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public Object getValue(int position){
        return mListItems.get(position);
    }
}

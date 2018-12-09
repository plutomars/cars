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
import Model.Title_ViewHolder;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mInflater;
    private List<Car> mListItems;
    private static final String TAG="SearchResultAdapter";
    private OnItemClickListener mOnItemClickListener;
    public Object value;
    public Car mCar;
    private TextView mModel;
    private TextView mMaker;
    private TextView mYear;
    private TextView mPrice;
    private TextView mMileage;
    private ImageView mSolvedImageView;
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
            View car_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresult,parent,false);
            return new Title_ViewHolder(car_view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            Car c = mListItems.get(position);
            Title_ViewHolder h = (Title_ViewHolder)viewHolder;
            mMaker.setText(c.getMake() +" " + c.getModel() + " " + c.getYear());
            mMileage.setText(String.format("%s %s","Mileage",String.valueOf(c.getMileage())));
            mPrice.setText(String.format("%s %s","$",String.valueOf(c.getPrice())));
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

//    public Object getValue(int positon){
//        return mListItems.get(positon).getCategoryName();
//    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    class Title_ViewHolder extends RecyclerView.ViewHolder{

        public Title_ViewHolder(@NonNull View h) {
            super(h);
            mMaker = (TextView) h.findViewById(R.id.Maker);
            mMileage = (TextView) h.findViewById(R.id.Mileage);
            mPrice = (TextView) h.findViewById(R.id.Price);
        }

        public void setText(String str){

        }
    }
}

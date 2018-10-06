package Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckedTextView;
import com.example.pluto.cars.R;

import java.util.ArrayList;
import java.util.List;

import Model.YearObj;
import butterknife.BindView;
import butterknife.ButterKnife;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder>{
    private Context mContext;
    private List<YearObj> selectedYear;
    private OnItemClickListener mOnItemClickListener;

    public YearAdapter(Context context,List<YearObj> selectedYear){
        this.selectedYear = selectedYear;
        mContext=context;
    }

    public List<YearObj> getSelectedYear(){
        if (selectedYear==null){
            selectedYear = new ArrayList<>();
        }
        return selectedYear;
    }

    public void notifyAdapter(List<YearObj> selectedYear,boolean isAdd){
        if(!isAdd){
            this.selectedYear = selectedYear;
        }else{
            this.selectedYear.addAll(selectedYear);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        YearObj yearObj = selectedYear.get(viewHolder.getAdapterPosition());
        viewHolder.mCheckedTextView.setText(yearObj.getYear());
        viewHolder.mCheckedTextView.setChecked(yearObj.isSelected);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(viewHolder.getAdapterPosition(),selectedYear);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return this.selectedYear.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.multi_item) CheckedTextView mCheckedTextView;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position, List<YearObj> yearObjList);
    }
}

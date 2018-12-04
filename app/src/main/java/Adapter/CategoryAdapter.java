package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.pluto.cars.R;
import java.util.List;

import Model.Category;
import Model.Item_ViewHolder;
import Model.Title_ViewHolder;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_CATEGORY_TITLE=0;
    private static final int TYPE_CATEGORY_ITEM=1;
    private LayoutInflater mInflater;
    private List<Category> mListItems;
    private static final String TAG="CategoryAdpater";
    private OnItemClickListener mOnItemClickListener=null;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public CategoryAdapter(Context context,List<Category> listItems){
        mListItems = listItems;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==TYPE_CATEGORY_TITLE){
            View title_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_title,parent,false);
            return new Title_ViewHolder(title_view);
        }else{
            View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            item_view.setOnClickListener(this);
            return new Item_ViewHolder(item_view);
        }
    }

    public void onClick(View view){
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Category c = mListItems.get(position);
        if(c.getType()==TYPE_CATEGORY_TITLE){
            Title_ViewHolder h = (Title_ViewHolder)viewHolder;
            h.setText(c.getCategoryName());
        }else{
            Item_ViewHolder h = (Item_ViewHolder)viewHolder;
            h.setText(c.getCategoryName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if(mListItems==null || position<0||position>getItemCount())return TYPE_CATEGORY_TITLE;
        Category c = mListItems.get(position);
        return (c.getType()==TYPE_CATEGORY_TITLE)?TYPE_CATEGORY_TITLE:TYPE_CATEGORY_ITEM;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    /*
    create different type of view holder
     */
//    class Title_ViewHolder extends RecyclerView.ViewHolder{
//        private TextView mTextView;
//        public Title_ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mTextView = (TextView)itemView.findViewById(R.id.list_title_view);
//        }
//
//        public void setText(String str){
//            mTextView.setText(str);
//        }
//    }
//
//    class Item_ViewHolder extends RecyclerView.ViewHolder{
//        private TextView mTextView;
//        public Item_ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mTextView = (TextView)itemView.findViewById(R.id.list_item_view);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
//
//        public void setText(String str){
//            mTextView.setText(str);
//        }
//    }
}

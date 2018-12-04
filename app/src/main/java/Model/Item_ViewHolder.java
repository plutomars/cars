package Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pluto.cars.R;

public class Item_ViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextView;
    public Item_ViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.list_item_view);
    }

    public void setText(String str){
        mTextView.setText(str);
    }
}

package Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pluto.cars.R;

public class ListResult_ViewHolder extends RecyclerView.ViewHolder {

    private TextView makeView;
    private TextView priceView;
    private TextView mileageView;

    public ListResult_ViewHolder(@NonNull View itemView) {
        super(itemView);
        makeView = itemView.findViewById(R.id.Maker);
        mileageView = itemView.findViewById(R.id.Mileage);
        priceView = itemView.findViewById(R.id.Price);
    }

    public void SetMake(String s){
        makeView.setText(s);
    }

    public void SetMileage(String s){
        mileageView.setText(s);
    }

    public void SetPrice(String s){
        priceView.setText(s);
    }
}

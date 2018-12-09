package Model;

import android.content.Context;

import com.example.pluto.cars.R;

import java.util.ArrayList;
import java.util.List;

public class PriceSingleton {

    private static PriceSingleton priceSingleton;
    private static List<PriceObj> priceObjList;

    public static PriceSingleton getInstance(Context context){
        if (priceSingleton==null){
            priceSingleton=new PriceSingleton(context);
        }
        return priceSingleton;
    }

    private PriceSingleton(Context context){
        String[] pricelist =context.getResources().getStringArray(R.array.Max_price);
        priceObjList = new ArrayList<>();
        for(String s:pricelist){
            PriceObj obj = new PriceObj(s);
            priceObjList.add(obj);
        }
    }

    public static List<PriceObj> getPriceObjList(){
        return priceObjList;
    }
}

package Model;

import android.content.Context;
import android.util.Log;

import com.example.pluto.cars.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import SQLite.model.CarDB;

public class CategorySingleton {
    private static CategorySingleton mCategorySingleton;
    private static List<Category> categoryList;
    private static Map<String,List<Category>> modelList;
    private static final String TAG="CategorySingleton";
    private static List<String> priceObjList;
    private static List<String> mileageObjList;

    public static CategorySingleton getInstance(Context context){
        if (mCategorySingleton==null){
            mCategorySingleton = new CategorySingleton(context);
        }
        return mCategorySingleton;
    }

    private CategorySingleton(Context context){
        Log.d(TAG,"enter singleton constructor");
        CarDB carDB = new CarDB(context);
        categoryList = CarDB.getAllMake();
        modelList = CarDB.getAllModels();
        String[] pricelist =context.getResources().getStringArray(R.array.Max_price);
        priceObjList = Arrays.asList(pricelist);
        String[] mileagelist =context.getResources().getStringArray(R.array.Mileages);
        mileageObjList = new ArrayList<>();
        mileageObjList =Arrays.asList(mileagelist);
    }

    public static List<Category> getCategoryList(){
        return categoryList;
    }
    public static List<Category> getModelsByMake(String make){
        return modelList.get(make);
    }
    public static List<String> getPriceObjList(){
            return priceObjList;
        }
    public static List<String> getMileageObjList(){
        return mileageObjList;
    }
}

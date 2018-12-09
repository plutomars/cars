package Model;

import android.content.Context;
import android.util.Log;

import com.example.pluto.cars.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import SQLite.model.CarDB;

public class CategorySingleton {
    private static CategorySingleton mCategorySingleton;
    private static List<Category> categoryList;
    private static Map<String,List<Category>> modelList;
    private static final String TAG="CategorySingleton";
    private static PriceSingleton priceSingleton;
    private static List<String> priceObjList;
    private static List<String> mileageObjList;
    public static CategorySingleton getInstance(Context context){
        Log.d(TAG,"Singleton enter");
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
        priceObjList = new ArrayList<>();
        for(String s:pricelist){
//            Category Category = new Category();
            priceObjList.add(s);
        }
        String[] mileagelist =context.getResources().getStringArray(R.array.Mileages);
        mileageObjList = new ArrayList<>();
        for(String s:mileagelist){
//            Category Category = new Category();
            mileageObjList.add(s);
        }
        //categoryList=new ArrayList<Category>();
        //String[] categories = context.getResources().getStringArray(R.array.Make_array);

//        for(String s:categories){
//            Log.d(TAG,s);
//        }
//
//        Log.d(TAG,Integer.toString(categories.length));

//        for(String s:categories){
//            String[] value = s.split("-");
//            Category cat = new Category(Integer.parseInt(value[0]),value[1]);
//            categoryList.add(cat);
//        }
        //to test is anything in the category list
        //Log.d(TAG,categoryList.get(1).getType()+","+categoryList.get(1).getCategoryName());
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

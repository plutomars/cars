package Model;

import android.content.Context;
import android.util.Log;

import com.example.pluto.cars.R;
import java.util.ArrayList;
import java.util.List;

public class CategorySingleton {
    private static CategorySingleton mCategorySingleton;
    private static List<Category> categoryList;
    private static final String TAG="CategorySingleton";

    public static CategorySingleton getInstance(Context context){
        Log.d(TAG,"Singleton enter");
        if (mCategorySingleton==null){
            mCategorySingleton = new CategorySingleton(context);
        }
        return mCategorySingleton;
    }

    private CategorySingleton(Context context){
        Log.d(TAG,"enter singleton constructor");
        categoryList=new ArrayList<Category>();
        String[] categories = context.getResources().getStringArray(R.array.Make_array);

        for(String s:categories){
            Log.d(TAG,s);
        }

        Log.d(TAG,Integer.toString(categories.length));

        for(String s:categories){
            String[] value = s.split("-");
            Category cat = new Category(Integer.parseInt(value[0]),value[1]);
            categoryList.add(cat);
        }
        //to test is anything in the category list
        Log.d(TAG,categoryList.get(1).getType()+","+categoryList.get(1).getCategoryName());
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }
}

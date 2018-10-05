package Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailSingleton {
    private static CategoryDetailSingleton mCategoryDetailSingleton;
    private static List<Category> categoryList;
    private static Context mContext;

    public static CategoryDetailSingleton getInstance(Context context){
        if (context==null){
            mCategoryDetailSingleton = new CategoryDetailSingleton(context);
            mContext=context;
        }
        return mCategoryDetailSingleton;
    }

    private CategoryDetailSingleton(Context context){
        categoryList = new ArrayList<Category>();
    }

    public static void getDetail(String category){
        categoryList.clear();
        //String[] details = mContext.getResources().getStringArray(category);
    }

}

package SQLite.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Category;
import SQLite.helper.MyDBHelper;

public class CarDB {
    private static MyDBHelper helper;
    private static SQLiteDatabase database;
    private static List<Category> makeList;
    private static Map<String,List<Category>> modelList;
    private static final String TAG = "CarDB";
    private static final String QUERY_ALL_MAKE = String.format("SELECT * FROM category_table");
    private static final String QUERY_ALL_MODELS =String.format("SELECT * FROM category_detail_table ORDER BY category_name,category_detail");

    public CarDB(Context context){
        helper = MyDBHelper.getInstance(context);
        database = helper.getReadableDatabase();
        Log.d("CarDB","enter CarDB constructor");
    }

    public synchronized static List<Category> getAllMake(){
        if (makeList == null) {
            makeList = new ArrayList<>();
            Cursor cursor = database.rawQuery(QUERY_ALL_MAKE, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        int mType = cursor.getInt(cursor.getColumnIndex("category_type"));
                        String category_name = cursor.getString(cursor.getColumnIndex("category_name"));
                        Category category = new Category(mType, category_name);
                        makeList.add(category);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
        return makeList;
    }

    public synchronized static Map<String,List<Category>> getAllModels(){

        if(modelList==null){
            modelList = new HashMap<>();
            Cursor cursor = database.rawQuery(QUERY_ALL_MODELS,null);
            try{
                if(cursor.moveToFirst()){
                    do{
                        int mType = cursor.getInt(cursor.getColumnIndex("category_type"));
                        String make = cursor.getString(cursor.getColumnIndex("category_name"));
                        String category_detail = cursor.getString(cursor.getColumnIndex("category_detail"));
                        Category category = new Category(mType,category_detail);

                        if (modelList.containsKey(make)){
                            modelList.get(make).add(category);
                        }else{
                            List<Category> newList = new ArrayList<>();
                            newList.add(category);
                            modelList.put(make,newList);
                        }
                    }while(cursor.moveToNext());
                }
            }catch (Exception e){
                Log.d(TAG,e.getMessage());
            }finally {
                if(cursor!=null && !cursor.isClosed()){
                    cursor.close();
                }
            }
        }

        return modelList;
    }
}

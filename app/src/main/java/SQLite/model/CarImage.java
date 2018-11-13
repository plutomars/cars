package SQLite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Car;
import SQLite.helper.MyDBHelper;

public final class CarImage {

    private static MyDBHelper helper;
    private static SQLiteDatabase sqLiteDatabase;

    public static final String CARID="car_id";
    public static final String IMAGE_1="image_1";
    public static final String IMAEG_2="image_2";
    public static final String IMAEG_3="image_3";
    private static final String TAG="CarImage";

    public CarImage(Context context){
        helper = MyDBHelper.getInstance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Log.d(TAG,"carimage instance initialized");
    }

    public synchronized Car setImages(Car car){
        Cursor cursor = sqLiteDatabase.query(MyDBHelper.CAR_IMAGE_TABLE_NAME,null,CarImage.CARID+" = ?",
                new String[]{car.getCarid()},null,null,null);
        try{
            if(cursor.getCount()==0){
                return car;
            }else{
                car.setImage_1(cursor.getBlob(cursor.getColumnIndex(CarImage.IMAGE_1)));
                car.setImage_2(cursor.getBlob(cursor.getColumnIndex(CarImage.IMAEG_2)));
                car.setImage_3(cursor.getBlob(cursor.getColumnIndex(CarImage.IMAEG_3)));
                return car;
            }
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
    }

    public void insertImages(Car car){
        ContentValues contentValues = getContentValue(car);
        sqLiteDatabase.insert(MyDBHelper.CAR_IMAGE_TABLE_NAME,null,contentValues);
    }

    public synchronized void updateImages(Car car){
        ContentValues contentValues = getContentValue(car);
        sqLiteDatabase.update(MyDBHelper.CAR_IMAGE_TABLE_NAME,contentValues,CarImage.CARID+" = ?",
                new String[]{car.getCarid()});
    }

    private static ContentValues getContentValue(Car car){
        ContentValues c = new ContentValues();
        c.put(CarImage.CARID,car.getCarid());
        c.put(CarImage.IMAGE_1,car.getImage_1());
        c.put(CarImage.IMAEG_2,car.getImage_2());
        c.put(CarImage.IMAEG_3,car.getImage_3());

        return c;
    }
}

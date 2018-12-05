package SQLite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Car;
import Model.MyImage;
import SQLite.helper.MyDBHelper;

public final class CarImage {

    private static MyDBHelper helper;
    private static SQLiteDatabase sqLiteDatabase;

    public static final String CARID="car_id";
    public static final String IMAGE="image";
    public static final String IMGNO="img_no";
    private static final String TAG="CarImage";
    private static CarImage carImage;
    private static List<MyImage> imageList = new ArrayList<>();


    public static CarImage getInstance(Context context){
        if(carImage==null){
            carImage = new CarImage(context);
        }
        return carImage;
    }

    private CarImage(Context context){
        helper = MyDBHelper.getInstance(context);
        sqLiteDatabase = helper.getWritableDatabase();
        Log.d(TAG,"carimage instance initialized");
    }

    public static synchronized Car setImages(Car car){
        Cursor cursor = sqLiteDatabase.query(MyDBHelper.CAR_IMAGE_TABLE_NAME,null,CarImage.CARID+" = ?",
                new String[]{car.getCarid()},null,null,null);
        imageList.clear();
        try {
            if (cursor == null) {
                car.setImages(imageList);
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        MyImage image = new MyImage();
                        image.setCarid(cursor.getString(cursor.getColumnIndex(CarImage.CARID)));
                        image.setImg_no(cursor.getInt(cursor.getColumnIndex(CarImage.IMGNO)));
                        image.setImage(cursor.getBlob(cursor.getColumnIndex(CarImage.IMAGE)));
                        imageList.add(image);
                    } while (cursor.moveToNext());
                }
                car.setImages(imageList);
            }
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }
        finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return car;
    }

    public static byte[] getSingleImage(MyImage imageObj){
        ContentValues contentValues = getContentValue(imageObj);
        String whereClause = String.format("%s = ? AND %s = ?",CarImage.CARID,CarImage.IMGNO);
        String[] whereArgs = new String[]{imageObj.getCarid(),String.valueOf(imageObj.getImg_no())};
        Cursor cursor = sqLiteDatabase.query(MyDBHelper.CAR_IMAGE_TABLE_NAME,null,
                whereClause,whereArgs,null,null,null);
        if(cursor==null){
            return null;
        }else{
            cursor.moveToFirst();
            return cursor.getBlob(cursor.getColumnIndex(CarImage.IMAGE));
        }
    }

    private static void insertSingleImage(MyImage imageObj){
        ContentValues contentValues = getContentValue(imageObj);
        sqLiteDatabase.insert(MyDBHelper.CAR_IMAGE_TABLE_NAME,null,contentValues);
        Log.d(TAG,"insertImage successful");
    }

    public static synchronized void updateImages(MyImage imageObj){
        ContentValues contentValues = getContentValue(imageObj);
        String whereClause = String.format("%s = ? AND %s = ?");
        String[] whereArgs = new String[]{imageObj.getCarid(),String.valueOf(imageObj.getImg_no())};
        sqLiteDatabase.update(MyDBHelper.CAR_IMAGE_TABLE_NAME,contentValues,whereClause,whereArgs);
    }

    private static ContentValues getContentValue(MyImage imageObj){
        ContentValues c = new ContentValues();
        c.put(CarImage.CARID,imageObj.getCarid());
        c.put(CarImage.IMGNO,imageObj.getImg_no());
        c.put(CarImage.IMAGE,imageObj.getImage());
        return c;
    }

    public static void insertImages(Car car){
        if(car.getImages().size()==0)return;
        for(MyImage myImage:car.getImages()){
            System.out.print(car.getImages().size());
            //insertSingleImage(myImage);
        }
    }
}

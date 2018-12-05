package SQLite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Car;
import SQLite.helper.MyDBHelper;

public final class CarSchema {
    public static final String CARID="car_id";
    public static final String MAKE="make";
    public static final String MODEL="model";
    public static final String YEAR="year";
    public static final String PRICE="price";
    public static final String LOCATION="location";
    public static final String MILEAGE="mileage";
    public static final String OWNER="owner";

    private static MyDBHelper myDBHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static final String TAG ="CarSchema";
    private static final String query = "SELECT * FROM cars_table WHERE make ";
    private static List<Car> carList=new ArrayList<>();
    private static Context mContext;

    public CarSchema(Context context){
        mContext = context;
        myDBHelper = MyDBHelper.getInstance(mContext);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        Log.d(TAG,"carschema instance initialized");
    }

    public void insertCar(Car car){
        ContentValues contentValues = getContentValue(car);
        sqLiteDatabase.insert(MyDBHelper.CAR_TABLE_NAME,null, contentValues);
        CarImage.insertImages(car);
    }

    public synchronized Cursor queryCar(String make,String model,int price,int mileage){
        Log.d(TAG,"queryCar");
        String whereClause = String.format("%s LIKE ? AND %s LIKE ? AND %s < ? AND %s < ?",
                CarSchema.MAKE,CarSchema.MODEL,CarSchema.PRICE,CarSchema.MILEAGE);
        Log.d(TAG,whereClause);
        String[] whereArgs=new String[]{make+"%",model+"%",String.valueOf(price),String.valueOf(mileage)};
        Cursor cursor = sqLiteDatabase.query(MyDBHelper.CAR_TABLE_NAME,null,whereClause,whereArgs,null,null,null);
        return cursor;
    }

    public synchronized List<Car> getCarList(Cursor cursor){
        Log.d(TAG,"list cars");
        carList.clear();
        try {
            if (cursor == null) {
                return carList;
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        Car car = new Car();
                        car.setCarid(cursor.getString(cursor.getColumnIndex(CarSchema.CARID)));
                        car.setMake(cursor.getString(cursor.getColumnIndex(CarSchema.MAKE)));
                        car.setModel(cursor.getString(cursor.getColumnIndex(CarSchema.MODEL)));
                        car.setYear(cursor.getInt(cursor.getColumnIndex(CarSchema.YEAR)));
                        car.setPrice(cursor.getInt(cursor.getColumnIndex(CarSchema.PRICE)));
                        car.setLocation(cursor.getFloat(cursor.getColumnIndex(CarSchema.LOCATION)));
                        car.setMileage(cursor.getInt(cursor.getColumnIndex(CarSchema.MILEAGE)));
                        car.setOwner(cursor.getString(cursor.getColumnIndex(CarSchema.OWNER)));
                        CarImage.getInstance(mContext);
                        CarImage.setImages(car);
                        carList.add(car);
                    } while (cursor.moveToNext());
                }
            }
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return carList;
    }

    public static Car getTestCar(){
        Log.d(TAG,"get test car");

        String whereClause = String.format("make = ? AND model = ? AND car_id = 1");
        String[] whereArgs = new String[]{"Honda","Civic"};

        Cursor cursor = sqLiteDatabase.query(MyDBHelper.CAR_TABLE_NAME,null,whereClause,whereArgs,null,null,null);
        Car car = new Car();
        try{
            cursor.moveToFirst();
            car.setCarid(cursor.getString(cursor.getColumnIndex(CarSchema.CARID)));
            car.setMake(cursor.getString(cursor.getColumnIndex(CarSchema.MAKE)));
            car.setModel(cursor.getString(cursor.getColumnIndex(CarSchema.MODEL)));
            car.setYear(cursor.getInt(cursor.getColumnIndex(CarSchema.YEAR)));
            car.setPrice(cursor.getInt(cursor.getColumnIndex(CarSchema.PRICE)));
            car.setLocation(cursor.getFloat(cursor.getColumnIndex(CarSchema.LOCATION)));
            car.setMileage(cursor.getInt(cursor.getColumnIndex(CarSchema.MILEAGE)));
            car.setOwner(cursor.getString(cursor.getColumnIndex(CarSchema.OWNER)));
            CarImage.getInstance(mContext);
//            CarImage.setImages(car);
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return car;
    }
    private static ContentValues getContentValue(Car car){
        ContentValues c = new ContentValues();
        c.put(CarSchema.CARID,car.getCarid());
        c.put(CarSchema.MAKE,car.getMake());
        c.put(CarSchema.MODEL,car.getModel());
        c.put(CarSchema.YEAR,car.getYear());
        c.put(CarSchema.PRICE,car.getPrice());
        c.put(CarSchema.LOCATION,Float.valueOf(car.getLocation()));
        c.put(CarSchema.MILEAGE,car.getMileage());
        c.put(CarSchema.OWNER,car.getOwner());

        return c;
    }
}

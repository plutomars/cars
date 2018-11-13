package SQLite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public CarSchema(Context context){
        myDBHelper = MyDBHelper.getInstance(context);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        Log.d(TAG,"carschema instance initialized");
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

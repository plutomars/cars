package SQLite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Model.Category;
import SQLite.model.CarImage;
import SQLite.model.CarSchema;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Cars.db";
    private static final String TAG = "MyDBHelper";
    public static final int VERSION = 1;
    private static SQLiteDatabase database;
    private static String DB_PATH = "";
    private final Context mContext;
    private boolean mNeedUpdate = false;
    private static MyDBHelper myDBHelper;
    public static final String CAR_TABLE_NAME="cars_table";
    public static final String CAR_IMAGE_TABLE_NAME="car_image";

    private static List<Category> makeList;

    public static synchronized MyDBHelper getInstance(Context context) {
        if (myDBHelper == null) {
            myDBHelper = new MyDBHelper(context);

        }
        return myDBHelper;
    }

    private MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases";
        }
        mContext = context;

        copyDataBase();
        database = getWritableDatabase();
        createCarsTable(database);
        createCarImageTable(database);
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException ioe) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        try (InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
             OutputStream mOutput = new FileOutputStream(DB_PATH + DATABASE_NAME)) {
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);

            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DATABASE_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }
            copyDataBase();
            mNeedUpdate = false;
        }
    }

    public boolean openDataBase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return database != null;
    }

    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"enter onCreate database");
        createCarsTable(db);
        createCarImageTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            mNeedUpdate = true;
        }
    }

    private void createCarsTable(SQLiteDatabase db){
        String sqlCmd = String.format("CREATE TABLE IF NOT EXISTS %s ( %s TEXT PRIMARY KEY ,"+
        " %s TEXT , %s TEXT , %s TEXT , %s INTEGER , %s REAL , %s INTEGER , %s TEXT ) ",CAR_TABLE_NAME,
                CarSchema.CARID,CarSchema.MAKE,CarSchema.MODEL,CarSchema.YEAR,CarSchema.PRICE,CarSchema.LOCATION,
                CarSchema.MILEAGE,CarSchema.OWNER);
        db.execSQL(sqlCmd);
        Log.d(TAG,"create cars table successful");
    }

    private void createCarImageTable(SQLiteDatabase db){
        String sqlCmd = String.format("CREATE TABLE IF NOT EXISTS %s ( %s TEXT ,"+
                " %s INTEGER , %s BLOB ) ",CAR_IMAGE_TABLE_NAME,CarImage.CARID,CarImage.IMGNO,
                CarImage.IMAGE);
        db.execSQL(sqlCmd);
        Log.d(TAG,"create carimage table successful");
    }
}
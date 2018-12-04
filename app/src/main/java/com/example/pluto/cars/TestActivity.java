package com.example.pluto.cars;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import Model.Car;
import Model.Category;
import Model.MyImage;
import SQLite.helper.MyDBHelper;
import SQLite.model.CarDB;
import SQLite.model.CarImage;
import SQLite.model.CarSchema;


public class TestActivity extends AppCompatActivity {
    private static final String TAG="TestActivity";
    private ImageView testImageView;
    private Button getButton;
    private Button saveButton;
    private InputStream inputStream=null;
    private ByteArrayOutputStream byteArrayOutputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        loadingData();
//
//        testImageView = (ImageView)findViewById(R.id.testImageView);
//        String filename="jing.jpg";
//        AssetManager assetManager = getBaseContext().getAssets();
//        try{
//            inputStream = assetManager.open(filename);
//        }catch (IOException ioe){
//            ioe.printStackTrace();
//        }
//        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        testImageView.setImageBitmap(bitmap);
//
//        getButton = (Button)findViewById(R.id.getImageButton);
//        getButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CarImage.getInstance(getBaseContext());
//                MyImage image = new MyImage();
//                image.setCarid("1");
//                image.setImg_no(2);
//                image.setImage(CarImage.getSingleImage(image));
//                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(image.getImage(),0,image.getImage().length);
//                testImageView.setImageBitmap(compressedBitmap);
//            }
//        });
//        saveButton=(Button)findViewById(R.id.saveImageButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CarImage.getInstance(getBaseContext());
//                Car n_car = new Car();
//                n_car.setCarid("1");
//                n_car = CarImage.setImages(n_car);
//                for(MyImage myImage :n_car.getImages()){
//                    Log.d(TAG,myImage.getCarid()+myImage.getImg_no());
//                }
//            }
//        });

    }
    private void loadingData(){
        //CarDB db = new CarDB(this);
        MyDBHelper myDBHelper = MyDBHelper.getInstance(this);
//        CarSchema carSchema = new CarSchema(this);
//        Log.d(TAG,"loading data");
//        List<Car> carList = carSchema.getCarList(carSchema.queryCar("Honda","Civic",100000,10000));
//        for(Car c:carList){
//            Log.d(TAG,c.getCarid());
//            Log.d(TAG,c.getOwner());
//        }
    }


}

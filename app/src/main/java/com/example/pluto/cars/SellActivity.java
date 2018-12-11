package com.example.pluto.cars;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import Adapter.UploadImageAdapter;
import Model.Car;
import Model.Constant;
import Model.MyImage;
import SQLite.model.CarImage;
import SQLite.model.CarSchema;
import Utils.BitmapUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SellActivity extends AppCompatActivity {

    private List<Bitmap> data = new ArrayList<Bitmap>();
    private String photoPath;
    private UploadImageAdapter adapter;
    private static final String TAG="SellActivity";
    private File cameraFile;
    //Make a choice to get an image by using the camera or from the gallery
    private PhotoChoiceWindow choisePhotoPopup;
    private static final int MULTIPLE_PERMISSIONS=10;
    private static Bitmap defaultImage;
    private final String[] multi_permission = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @BindView(R.id.send_btn) Button mSendButton;
    @BindView(R.id.gridView1) GridView mGridView;

    @BindView(R.id.car_make_entry_title) TextView makeTitle;
    @BindView(R.id.car_make_entry_info) EditText makeInfo;
    @BindView(R.id.car_model_info_entry_title) TextView modelTitle;
    @BindView(R.id.car_model_entry_info) EditText modelInfo;
    @BindView(R.id.car_year_info_entry_title) TextView yearTitle;
    @BindView(R.id.car_year_entry_info) EditText yearInfo;
    @BindView(R.id.car_price_info_entry_title) TextView priceTitle;
    @BindView(R.id.car_price_entry_info) EditText priceInfo;
    @BindView(R.id.car_mileage_info_entry_title) TextView mileageTitle;
    @BindView(R.id.car_mileage_entry_info) EditText mileageInfo;
    @BindView(R.id.car_owner_info_entry_title) TextView ownerTitle;
    @BindView(R.id.car_owner_entry_info) EditText ownerInfo;
    private LinearLayout parentLaoyout;
    private String make="";
    private String model="";

    private void createDefaultImage(){
        InputStream is = null;
        try{
            is= getAssets().open("Noimage.png");
            if(is!=null){
                defaultImage = BitmapFactory.decodeStream(is);
            }
        }catch (IOException ie){
            Log.d(TAG,ie.getMessage());
        }finally {
            try{
                if(is!=null){
                    is.close();
                }
            }catch (IOException ioe){
                Log.d(TAG,ioe.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        createDefaultImage();


        priceTitle.setText("Price");
        makeInfo.setInputType(InputType.TYPE_NULL);
        modelInfo.setInputType(InputType.TYPE_NULL);
        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addpic);
        data.add(bp);
        adapter = new UploadImageAdapter(getApplicationContext(), data, mGridView);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (data.size() == 6) {
                    Toast.makeText(SellActivity.this, "Uploading limit is reached", Toast.LENGTH_SHORT).show();
                }
                if(checkPermissions()){
                    showChoicePhoto();
                }
            }
        });

        mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(position);
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCar();
            }
        });
    }

    private void showChoicePhoto() {
        parentLaoyout = findViewById(R.id.add_main);
        choisePhotoPopup = new PhotoChoiceWindow(SellActivity.this, choicePhotoCilck, parentLaoyout);
        choisePhotoPopup.showPopupWindow(parentLaoyout);
        parentLaoyout.setAlpha((float) 0.3);
    }

    private boolean checkPermissions(){
        int result;
        List<String> listPermissionNeeded = new ArrayList<>();
        for(String s:multi_permission){
            result = ActivityCompat.checkSelfPermission(this,s);
            if(result!=PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(s);
            }
        }
        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void saveCar(){
        CarSchema carSchema = new CarSchema(this);
        Car car = new Car();

        int car_id = carSchema.getCarId();
        Log.d(TAG,"Car_id="+String.valueOf(car_id));
        car.setCarid(String.valueOf(car_id));
        car.setMake(makeInfo.getText().toString());
        car.setModel(modelInfo.getText().toString());
        car.setYear(yearInfo.getText().toString());
        car.setPrice(Integer.parseInt(priceInfo.getText().toString()));
        car.setMileage(Integer.parseInt(priceInfo.getText().toString()));
        car.setOwner(ownerInfo.getText().toString());
        List<MyImage> imageList = new ArrayList<>();

        Log.d(TAG,"data size"+String.valueOf(data.size()));
        if(data.size()==1){
            data.remove(data.size() - 1);
            Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addpic);
            Log.d(TAG,String.valueOf(defaultImage==null));
            data.add(defaultImage);
            data.add(bp);
            //data.remove(0);
            //data.add(0,defaultImage);
        }

        for(int i =0;i<data.size()-1;i++){
            byte[] bytes = BitmapUtils.BitmapToBytes(data.get(i));
            MyImage image = new MyImage(String.valueOf(car_id),i,bytes);
            //Log.d(TAG,String.valueOf(i));
            imageList.add(image);
        }
        Log.d(TAG,"imagelist size"+String.valueOf(imageList.size()));
        car.setImages(imageList);
        //Log.d(TAG,String.valueOf(car_id));
        carSchema.insertCar(car);
        //CarImage.insertImages(car);
        Log.d(TAG,"insert successful");


        Intent goBackIntent = new Intent(SellActivity.this,MainActivity.class);
        startActivity(goBackIntent);
        SellActivity.this.finish();


//        Car car = CarSchema.getTestCar();
//        car.setCarid("99");
//        List<MyImage> imageList = new ArrayList<>();
//        for(int i=0;i<data.size()-1;i++){
//            byte[] bytes = BitmapUtils.BitmapToBytes(data.get(i));
//            MyImage image = new MyImage(car.getCarid(),i+1,bytes);
//            imageList.add(image);
//        }
//
//        car.setImages(imageList);
//        Toast.makeText(SellActivity.this,String.valueOf(imageList.size()),Toast.LENGTH_SHORT).show();
//        Toast.makeText(SellActivity.this,String.valueOf(data.size()),Toast.LENGTH_SHORT).show();
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SellActivity.this);
        builder.setMessage("Are you sure to remove this image？");
        builder.setTitle("Prompt");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        switch (requestCode){
            case Constant.USER_CAMERA:
                if (resultCode == RESULT_OK) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    try {
                        Bitmap newBitMap = BitmapFactory.decodeFile(cameraFile.getCanonicalPath(), options);
                        data.remove(data.size() - 1);
                        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addpic);
                        data.add(newBitMap);
                        data.add(bp);
                        adapter.notifyDataSetChanged();
                        choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode ==RESULT_CANCELED){
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                }
                break;

            case Constant.PICK_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;

                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    photoPath = cursor.getString(column_index);
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                } else if (resultCode == RESULT_CANCELED) {
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                }

                break;
            case Constant.MakeRequestCode:
                make = intent.getStringExtra("make");
                if(!TextUtils.isEmpty(model)){
                    modelInfo.setText("");
                }
                makeInfo.setText(make);
                break;
            case Constant.ModelRequestCode:
                make = intent.getStringExtra("make");
                model = intent.getStringExtra("model");
                if(TextUtils.isEmpty(make)){
                    make="All";
                }
                makeInfo.setText(make);
                modelInfo.setText(model);
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(photoPath)) {
            Bitmap newBp = BitmapUtils.decodeSampledBitmapFromFd(photoPath, 300, 300);
            data.remove(data.size() - 1);
            Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addpic);
            data.add(newBp);
            data.add(bp);
            photoPath = null;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
//            case Constant.PICK_FROM_GALLERY:
//                Log.d(TAG,"ask for read permission");
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
//                    Log.d(TAG,"get read permission failed");
//                    Toast.makeText(SellActivity.this,"read permission is needed",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            case Constant.WRITE_TO_GALLERY:
//                Log.d(TAG,"ask for write permission");
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
//                    Log.d(TAG,"get write permission failed");
//                    Toast.makeText(SellActivity.this,"write permission is needed",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            case Constant.USER_CAMERA:
//                Log.d(TAG,"ask for camera permission");
//                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
//                    Log.d(TAG,"get camera permission failed");
//                    Toast.makeText(SellActivity.this,"camera permission is needed",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
            case MULTIPLE_PERMISSIONS:
                if(grantResults.length>0){
                    //String permissionDenies="";
                    for(String str:permissions){
                        if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                            Toast.makeText(SellActivity.this,str+"permission is needed",Toast.LENGTH_SHORT);
                        }
                    }
                }
                break;
        }
    }

//    private void requestReadPermission(){
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        Constant.PICK_FROM_GALLERY);
//            }
//            //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Constant.PICK_FROM_GALLERY);
//        }
//    }
//
//    private void requestCameraPermission(){
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//
//            }else{
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},Constant.USER_CAMERA);
//            }
//        }
//    }
//
//    private void requestWritePermission(){
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//
//            }else{
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Constant.WRITE_TO_GALLERY);
//            }
//        }
//    }

    private View.OnClickListener choicePhotoCilck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.takePhoto:
                    takePhoto();
                    break;
                case R.id.choosePhoto:
                    choosePhoto();
                    break;
                case R.id.btn_cancel:
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                    break;
            }
        }
    };

    private void takePhoto() {
        cameraFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(SellActivity.this, "com.example.pluto.cars", cameraFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(intent, Constant.USER_CAMERA);
    }

    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(photoPickerIntent, Constant.PICK_FROM_GALLERY);
    }

    @OnClick({R.id.car_make_entry_info,R.id.car_make_entry_title,R.id.car_model_entry_info,R.id.car_model_info_entry_title})
//            R.id.car_year_info, R.id.car_year_info_title,R.id.car_price_info,R.id.car_price_info_title,
//            R.id.car_mileage_info,R.id.car_mileage_info_title,R.id.car_owner_info,R.id.car_owner_info_title})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.car_make_entry_info:
            case R.id.car_make_entry_title:
                Intent makeIntent = new Intent(SellActivity.this,MakeActivity.class);
                makeIntent.putExtra("source",Constant.SellActivity);
                startActivityForResult(makeIntent,Constant.MakeRequestCode);
                break;
            case R.id.car_model_entry_info:
            case R.id.car_model_info_entry_title:
                Intent modelIntent = new Intent(SellActivity.this,ModelActivity.class);
                if(TextUtils.isEmpty(make)){
                    modelIntent.putExtra("make","All");
                }else{
                    modelIntent.putExtra("make",make);
                }
                modelIntent.putExtra("source",Constant.SellActivity);
                startActivityForResult(modelIntent,Constant.ModelRequestCode);
                break;

        }
    }

//    @OnTextChanged(value = {R.id.car_year_entry_info,R.id.car_price_entry_info,R.id.car_mileage_entry_info,R.id.car_owner_entry_info},callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    public void onTextChanged(CharSequence text){
//
//        Log.d(TAG,String.valueOf(text));
//    }

    @OnTextChanged(value = {R.id.car_year_entry_info,R.id.car_price_entry_info,R.id.car_mileage_entry_info},callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void digitOnly(CharSequence text){
        Log.d(TAG,"digit");
        Log.d(TAG,String.valueOf(text));
    }

    @OnTextChanged(value = {R.id.car_owner_entry_info},callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void engOnly(CharSequence text){
        Log.d(TAG,"eng");
        Log.d(TAG,String.valueOf(text));
    }
}

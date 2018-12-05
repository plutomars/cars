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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import Adapter.UploadImageAdapter;
import Model.Car;
import Model.MyImage;
import SQLite.model.CarSchema;
import Utils.BitmapUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SellActivity extends AppCompatActivity {

    private List<Bitmap> data = new ArrayList<Bitmap>();
    private String photoPath;
    private UploadImageAdapter adapter;
    private static final int PICK_FROM_GALLERY=990;
    private static final int WRITE_TO_GALLERY=991;
    private static final int USER_CAMERA=999;
    private static final String TAG="SellActivity";
    private File cameraFile;
    //Make a choice to get an image by using the camera or from the gallery
    private PhotoChoiceWindow choisePhotoPopup;
    @BindView(R.id.send_btn) Button mSendButton;
    @BindView(R.id.gridView1) GridView mGridView;
    private LinearLayout parentLaoyout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        requestReadPermission();
        requestCameraPermission();
        requestWritePermission();

        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addpic);
        data.add(bp);
//        mGridView = (GridView) findViewById(R.id.gridView1);

        adapter = new UploadImageAdapter(getApplicationContext(), data, mGridView);
        mGridView.setAdapter(adapter);

        // setOnClickItemListener
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (data.size() == 6) {
                    Toast.makeText(SellActivity.this, "Uploading limit is reached", Toast.LENGTH_SHORT).show();
                }
// else {
//                    if (position == data.size() - 1) {
//                        Toast.makeText(SellActivity.this, "Add an image", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Intent.ACTION_PICK, null);
//                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                        startActivityForResult(intent, 990);
//                    } else {
//                        Toast.makeText(SellActivity.this, "Click" + (position + 1) + " th image", Toast.LENGTH_SHORT).show();
//                    }
//                }
                showChoicePhoto();
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

//                Toast.makeText(SellActivity.this,String.valueOf(data.size()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showChoicePhoto() {
        parentLaoyout = findViewById(R.id.add_main);
        choisePhotoPopup = new PhotoChoiceWindow(SellActivity.this, choicePhotoCilck, parentLaoyout);
        choisePhotoPopup.showPopupWindow(parentLaoyout);
        parentLaoyout.setAlpha((float) 0.3);
    }


    private void saveCar(){
        CarSchema carSchema = new CarSchema(this);
        Car car = CarSchema.getTestCar();
        car.setCarid("99");
        List<MyImage> imageList = new ArrayList<>();
//        for(int i=0;i<data.size()-1;i++){
//            byte[] bytes = BitmapUtils.BitmapToBytes(data.get(i));
//            MyImage image = new MyImage(car.getCarid(),i+1,bytes);
//            imageList.add(image);
//        }
//
//        car.setImages(imageList);
//        Toast.makeText(SellActivity.this,String.valueOf(imageList.size()),Toast.LENGTH_SHORT).show();
        Toast.makeText(SellActivity.this,String.valueOf(data.size()),Toast.LENGTH_SHORT).show();
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
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case USER_CAMERA:
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

            case PICK_FROM_GALLERY:
                if(resultCode==RESULT_OK){
                    Uri uri = intent.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;

                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    photoPath = cursor.getString(column_index);
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                }else if(resultCode==RESULT_CANCELED){
                    choisePhotoPopup.dismissPopupWindow(parentLaoyout);
                }

                break;
        }
//        if (requestCode == 990 && resultCode == RESULT_OK) {
//            if (data != null) {
//
//                ContentResolver resolver = getContentResolver();
//                try {
//                    Uri uri = data.getData();
//
//                    String[] proj = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = managedQuery(uri, proj, null, null, null);
//
//                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();
//
//                    photoPath = cursor.getString(column_index);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
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
            case PICK_FROM_GALLERY:
                Log.d(TAG,"ask for read permission");
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Log.d(TAG,"get read permission failed");
                    Toast.makeText(SellActivity.this,"read permission is needed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case WRITE_TO_GALLERY:
                Log.d(TAG,"ask for write permission");
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Log.d(TAG,"get write permission failed");
                    Toast.makeText(SellActivity.this,"write permission is needed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case USER_CAMERA:
                Log.d(TAG,"ask for camera permission");
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Log.d(TAG,"get camera permission failed");
                    Toast.makeText(SellActivity.this,"camera permission is needed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void requestReadPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PICK_FROM_GALLERY);
        }
    }

    private void requestCameraPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},USER_CAMERA);
        }
    }

    private void requestWritePermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_TO_GALLERY);
        }
    }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(SellActivity.this, "com.example.pluto.cars", cameraFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(intent, USER_CAMERA);
    }

    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);
    }
}

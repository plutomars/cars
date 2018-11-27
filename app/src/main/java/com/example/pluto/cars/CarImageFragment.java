package com.example.pluto.cars;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

import Model.MyImage;
import SQLite.model.CarImage;

public class CarImageFragment extends Fragment {

    private static final String ARG_CARID = "carid";
    private static final String ARG_IMGNO = "imgno";
    private static final String ARG_IMAGE = "image";
    private MyImage myImage;
    private ImageView imageView;
    private InputStream inputStream;

    public static CarImageFragment newInstance(MyImage myImage){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARID,myImage.getCarid());
        args.putSerializable(ARG_IMGNO,myImage.getImg_no());
        args.putSerializable(ARG_IMAGE,myImage.getImage());
        CarImageFragment carImageFragment = new CarImageFragment();
        carImageFragment.setArguments(args);
        return carImageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String carid = (String)getArguments().getSerializable(ARG_CARID);
        int imgno = (int)getArguments().getSerializable(ARG_IMGNO);
        byte[] image = (byte[])getArguments().getSerializable(ARG_IMAGE);
        myImage = new MyImage(carid,imgno,image);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carimage,container,false);
        imageView = view.findViewById(R.id.fr_carimage);
        imageView.setImageBitmap(getImage(myImage));
        return view;
    }

    private Bitmap getImage(MyImage myImage){
        return BitmapFactory.decodeByteArray(myImage.getImage(),0,myImage.getImage().length);
    }
}

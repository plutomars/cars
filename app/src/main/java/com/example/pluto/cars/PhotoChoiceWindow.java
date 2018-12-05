package com.example.pluto.cars;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import java.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoChoiceWindow extends PopupWindow {
    private View view;
    @BindView(R.id.takePhoto) Button mTakePhoto;
    @BindView(R.id.choosePhoto) Button mChoosePhoto;
    @BindView(R.id.btn_cancel) Button mCancel;
    private static final String TAG="PhotoChoiceWindow";

    public PhotoChoiceWindow(Activity context, View.OnClickListener btnOnClick,final View layout){
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.photo_choice,null);
        this.setContentView(view);
        ButterKnife.bind(this,view);

        mTakePhoto.setOnClickListener(btnOnClick);
        mChoosePhoto.setOnClickListener(btnOnClick);
        mCancel.setOnClickListener(btnOnClick);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        view.setFocusableInTouchMode(true);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                layout.setAlpha(1);
            }
        });
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        this.setBackgroundDrawable(dw);
    }

    public void showPopupWindow(View parent){
        if(!this.isShowing()){
            this.showAtLocation(parent,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        }
    }

    public void dismissPopupWindow(View layout){
        layout.setAlpha(1);
        this.dismiss();
    }
}

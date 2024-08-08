package com.example.digiposapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class CustomDialogClassfoeaddaddv extends Dialog {

    public CustomDialogClassfoeaddaddv(@NonNull Context context) {
        super(context);

    }

    ImageView clear;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout_addfail);

        ImageView claer=findViewById(R.id.claer);
        Button add,noti;

        add=findViewById(R.id.tv3);
        noti=findViewById(R.id.tv39);

        claer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),AddVehicalActivity.class));
                dismiss();
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(),EmergencyActivity.class));
                dismiss();
            }
        });


    }



    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return true;
    }

    @Override
    public void onBackPressed() {

    }
}

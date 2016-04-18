package com.example.tobias.rotationprototype;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Rotation extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        imageView  =(ImageView)findViewById(R.id.imageView);
        Button btn =(Button)findViewById(R.id.rotate);


    }

    public float degree = 0f;

    public void onClick(View v)
    {

        {
            float newdegree = degree+6f;
            imageView.setRotation(newdegree);
            degree = newdegree;
        }
    }
    public void onClick2(View v)
    {
        {
            float newdegree = degree-6f;
            imageView.setRotation(newdegree);
            degree = newdegree;
        }
    }
}




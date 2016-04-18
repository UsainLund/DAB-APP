package com.example.asmod.appgame;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Mark on 11-04-2016.
 */
public class Beer {

    GameObject gameObject;
    long instancetime = System.currentTimeMillis();
    int y;

    public Beer(Bitmap bitmap, int x, int y)
    {
        gameObject.setX(x);gameObject.setY(y);gameObject.setBitmap(bitmap);
    }

    Random rand = new Random();



    public void newBeer(Canvas canvas){

        int x = rand.nextInt(canvas.getWidth());
        new Beer (BitmapFactory.decodeResource(MainGamePanel.GetHackedContext().getResources(), R.drawable.daboel),x,canvas.getHeight());

    }
    public void setGravity(int y)
    {
        this.y = y;
        gameObject.move(new Speed(0,-y));
    }
    public void updateSpeed(){
        long currenttime = System.currentTimeMillis();
        long TimePassed = (currenttime - instancetime)/1000;
        if (TimePassed>30)
        {
            y += 2;
            instancetime = currenttime;
        }
    }

}
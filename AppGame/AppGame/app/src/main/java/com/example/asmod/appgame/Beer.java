package com.example.asmod.appgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Mark on 11-04-2016.
 */
public class Beer {

    GameObject gameObject;

    public Beer(Bitmap bitmap, int x, int y)
    {
        gameObject.setX(x);gameObject.setY(y);gameObject.setBitmap(bitmap);
    }

    Random rand = new Random();


    public void newBeer(){
        new Beer(,rand.nextInt(0,Canvas.getWidth()),Canvas.getHeight(),0);
    }


}

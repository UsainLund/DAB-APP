package com.example.asmod.appgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmod on 10-04-2016.
 */
public class GameObject {
    private boolean isPlayer = false;
    private boolean isActive = true;
    private Bitmap bitmap;
    private int x;
    private int y;
    private boolean touched;
    private Speed speed;
    public GameObject(Bitmap bitmap, int x, int y, boolean isPlayer)
    {
        this.isPlayer = isPlayer;
        this.bitmap = bitmap;
        this.speed = new Speed();
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap()
    {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public Speed getSpeed()
    {
        return this.speed;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void draw(Canvas canvas) {
        if(isActive) {
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        }
    }
    public boolean iscolliding(GameObject other){
        if (this.getX()<other.getX()+other.getBitmap().getWidth()&&
                this.getX()+this.bitmap.getWidth()>other.getX()&&
                this.getY()<other.getY()+other.bitmap.getHeight()&&
                this.bitmap.getHeight()+this.getY()>other.getY())
        {
            return true;
        }
        else {
            return false;
        }
    }
    public void onCollision()
    {
        if(isPlayer) {
            List<GameObject> go =  MainGamePanel.GO;
            for(int i = 0; i< go.size(); i++) {
                if (iscolliding(go.get(i))) {
                   // double score += 0,5;
                    go.remove(i);

                }
            }
        }
        else
        {

            //It's a beer
            //Is beer y-axis > getHeight
            //Remove beer and subtract perhaps some points
        }
    }

    public void update() {
        if (!touched) {
            x += (speed.getXv() * speed.getxDirection());
            y += (speed.getYv() * speed.getyDirection());
        }
        onCollision();
    }

    public void move(Speed speed)
    {
        this.speed = speed;
    }
}
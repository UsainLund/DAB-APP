package com.example.asmod.appgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by asmod on 10-04-2016.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainGameThread mainThread;
    private final static String Identifier = MainGamePanel.class.getSimpleName();
    private GameObject hero;
    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this); //Tilføj MainGamePanel som den klasse der styrer input
        this.setOnTouchListener(new OnSwipeTouchListener(context)
        {
            public void onSwipeTop()
            {
                hero.move(new Speed(0,0));
                hero.setTouched(false);
            }

            public void onSwipeRight()
            {
                hero.move(new Speed(1,0));
            }

            public void onSwipeLeft()
            {
                hero.move(new Speed(-1,0));
            }
        });

        hero = new GameObject(BitmapFactory.decodeResource(getResources(), R.drawable.hero), 50, 100);

        mainThread = new MainGameThread(getHolder(), this);
        setFocusable(true); //Styrer alle events
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry)
        {
            try {
                mainThread.join();
                retry = false;
            }
            catch(InterruptedException e)
            {

            }
        }
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            hero.handleActionDown((int)event.getX(), (int)event.getY());
            if(event.getY() > getHeight() - 50)
            {
                mainThread.setRunning(false);
                ((Activity)getContext()).finish();
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(hero.isTouched())
            {
                hero.move((int)event.getX(), (int)event.getY());
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            if(hero.isTouched())
            {
                hero.setTouched(false);
            }
        }
        return true;
    }
*/
    public void update() {
        if (hero.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && hero.getX() + hero.getBitmap().getWidth() / 2 >= getWidth()) {
            hero.getSpeed().toggleXDirection();
        }
        if (hero.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && hero.getX() - hero.getBitmap().getWidth() / 2 <= 0) {
            hero.getSpeed().toggleXDirection();
        }
        if (hero.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && hero.getY() + hero.getBitmap().getHeight() / 2 >= getHeight()) {
            hero.getSpeed().toggleYDirection();
        }
        if (hero.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && hero.getY() - hero.getBitmap().getHeight() / 2 <= 0) {
            hero.getSpeed().toggleYDirection();
        }
        hero.update();
    }

    protected void render(Canvas canvas)
    {
       canvas.drawColor(Color.BLACK);
        hero.draw(canvas);
    }
}

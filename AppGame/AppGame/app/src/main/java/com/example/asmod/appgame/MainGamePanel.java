package com.example.asmod.appgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmod on 10-04-2016.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private String fps;
    private MainGameThread mainThread;
    private final static String Identifier = MainGamePanel.class.getSimpleName();
    private GameObject spaceship;
    public static List<GameObject> GO = new ArrayList<GameObject>();
    //Hack
    static Context hackedContext;
    public MainGamePanel(Context context) {
        super(context);
        ImageView view = new ImageView(context);

        hackedContext = context;
        getHolder().addCallback(this); //Tilføj MainGamePanel som den klasse der styrer input
        this.setOnTouchListener(new OnSwipeTouchListener(context)
        {
            public void onSwipeTop()
            {
                spaceship.move(new Speed(0,0));
                spaceship.setTouched(false);
            }

            public void onSwipeRight()
            {
                spaceship.move(new Speed(2,0));
            }

            public void onSwipeLeft()
            {
                spaceship.move(new Speed(-2,0));
            }
        });

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_forward);

        spaceship = new GameObject(bMap,getScreenWidth()/2,getScreenHeight()-bMap.getHeight(), true);

        mainThread = new MainGameThread(getHolder(), this);
        setFocusable(true); //Styrer alle events
    }

    public static Context GetHackedContext()
    {
        return hackedContext;
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

    public void update() {
        if (spaceship.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && spaceship.getX() + spaceship.getBitmap().getWidth() / 2 >= getWidth()) {
            spaceship.getSpeed().toggleXDirection();
        }
        if (spaceship.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && spaceship.getX() - spaceship.getBitmap().getWidth() / 2 <= 0) {
            spaceship.getSpeed().toggleXDirection();
        }
        if (spaceship.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && spaceship.getY() + spaceship.getBitmap().getHeight() / 2 >= getHeight()) {
            spaceship.getSpeed().toggleYDirection();
        }
        if (spaceship.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && spaceship.getY() - spaceship.getBitmap().getHeight() / 2 <= 0) {
            spaceship.getSpeed().toggleYDirection();
        }
        spaceship.update();
    }

    protected void render(Canvas canvas)
    {
        // canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_bg), 0, 0, null);
        spaceship.draw(canvas);
        displayFps(canvas);
    }

    public void setFPS(String fps)
    {
        this.fps = fps;
    }
    private void displayFps(Canvas canvas) {
        if (canvas != null && this.fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(this.fps, this.getWidth() - 50, 20, paint);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
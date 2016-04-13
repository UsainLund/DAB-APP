package com.example.asmod.appgame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by asmod on 10-04-2016.
 */
public class MainGameThread extends Thread{


    private final static int 	MAX_FPS = 50;
    private final static int	MAX_FRAME_SKIPS = 5;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private boolean isRunning;
    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    private static final String Identifier = MainGameThread.class.getSimpleName();
    public MainGameThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    public void setRunning(boolean running)
    {
        isRunning = running;
    }

    @Override
    public void run()
    {

        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        Canvas canvas;
        while(isRunning)
        {
            canvas = null;
            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        this.gamePanel.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            }
            finally {
                if(canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

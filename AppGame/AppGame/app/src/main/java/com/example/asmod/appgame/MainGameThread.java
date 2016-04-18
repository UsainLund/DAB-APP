package com.example.asmod.appgame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by asmod on 10-04-2016.
 */
public class MainGameThread extends Thread{
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
        Canvas canvas;
        while(isRunning)
        {
            canvas = null;
            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
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

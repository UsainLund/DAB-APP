package com.example.asmod.appgame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

/**
 * Created by asmod on 10-04-2016.
 */
public class MainGameThread extends Thread{


    private final static int 	MAX_FPS = 50;
    private final static int	MAX_FRAME_SKIPS = 5;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private DecimalFormat df = new DecimalFormat("0.##");
    private final static int STAT_INTERVAL = 1000; //ms
    private final static int fpsCount = 10;
    private long lastStatusStore = 0;
    private long statusIntervalTimer = 0L;
    private long totalFramesSkipped = 0L;
    private long framesSkippedPerStatCycle 	= 0L;
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0L;
    private double 	fpsStore[];
    private long 	statsCount = 0L;
    private double 	averageFps = 0.0;



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
        initFPSCounter();
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        Canvas canvas;
        sleepTime = 0;
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
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    framesSkippedPerStatCycle += framesSkipped;
                    handleFPSCounter();
                }
            }
            catch(Exception e)
            {
                isRunning = false;
            }
            finally {
                if(canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void handleFPSCounter() {
        frameCountPerStatCycle++;
        totalFrameCount++;

        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));


            fpsStore[(int) statsCount % fpsCount] = actualFps;

            statsCount++;

            double totalFps = 0.0;
            for (int i = 0; i < fpsCount; i++) {
                totalFps += fpsStore[i];
            }

            if (statsCount < fpsCount) {
                averageFps = totalFps / statsCount;
            } else {
                averageFps = totalFps / fpsCount;
            }
            totalFramesSkipped += framesSkippedPerStatCycle;
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;

            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
            gamePanel.setFPS("FPS: " + df.format(averageFps));
        }
    }

    private void initFPSCounter() {
        fpsStore = new double[fpsCount];
        for (int i = 0; i < fpsCount; i++) {
            fpsStore[i] = 0.0;
        }

    }
}
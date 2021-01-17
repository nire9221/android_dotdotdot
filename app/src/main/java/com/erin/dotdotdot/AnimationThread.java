package com.erin.dotdotdot;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class AnimationThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private Ball mBall;
    private boolean mThreadRunning;

    public AnimationThread(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        mThreadRunning = true;
        // Create a ball with boundaries determined by canvas's size
        Canvas canvas = mSurfaceHolder.lockCanvas();
        mBall = new Ball(canvas.getWidth(), canvas.getHeight());
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {
        long previousFrameTime = System.currentTimeMillis();
        try {
            while (mThreadRunning) {
                Canvas canvas = mSurfaceHolder.lockCanvas(); // Wipe canvas clean
                long currentTime = System.currentTimeMillis();
                // Determine number of seconds since previous frame
                double elapsedTime = (currentTime - previousFrameTime) / 1000.0;

                canvas.drawRGB(255, 255, 255);
                // Move and draw ball
                mBall.move(elapsedTime);
                mBall.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
        catch (NullPointerException ex) {
            // In case canvas is destroyed while thread is running
        }
    }
    public void stopThread() {
        mThreadRunning = false;
    }
}

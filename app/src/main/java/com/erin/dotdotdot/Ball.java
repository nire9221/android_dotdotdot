package com.erin.dotdotdot;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {
    private final int RADIUS = 100;
    private final int BALL_COLOR = 0xffaaaaff;
    private Paint mPaint;
    private Point mCenter;
    private Point mVelocity;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    public Ball(int surfaceWidth, int surfaceHeight) { mSurfaceWidth = surfaceWidth;
        mSurfaceHeight = surfaceHeight;

        // Set initial position and velocity
        mCenter = new Point(100, 100);
        mVelocity = new Point(10, 10);

        mPaint = new Paint();
        mPaint.setColor(BALL_COLOR);
    }

    public void move(double elapsedTime) {
        // mVelocity around (500, 500) is ideal
        mCenter.x += mVelocity.x * elapsedTime;
        mCenter.y += mVelocity.y * elapsedTime;
        // Add velocity to ball's center
        mCenter.offset(mVelocity.x, mVelocity.y);

        // Check for top and bottom collisions
        if (mCenter.y > mSurfaceHeight - RADIUS) {
            mCenter.y = mSurfaceHeight - RADIUS;
            mVelocity.y *= -1;
        }
        else if (mCenter.y < RADIUS) {
            mCenter.y = RADIUS;
            mVelocity.y *= -1;
        }
        // Check for right and left collisions
        if (mCenter.x > mSurfaceWidth - RADIUS) {
            mCenter.x = mSurfaceWidth - RADIUS;
            mVelocity.x *= -1;
        }
        else if (mCenter.x < RADIUS) {
            mCenter.x = RADIUS;
            mVelocity.x *= -1;
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(mCenter.x, mCenter.y, RADIUS, mPaint);
    }
}

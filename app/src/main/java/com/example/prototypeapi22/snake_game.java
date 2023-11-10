package com.example.prototypeapi22;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// 参考: https://blog.recruit.co.jp/rmp/mobile/remember_canvas1/
// https://ethanfjp.com/articles/blog/programing/20211219android#:~:text=Android%E3%81%AE%E7%94%BB%E9%9D%A2%E3%82%B5%E3%82%A4%E3%82%BA%E3%82%92%E5%8F%96%E5%BE%97%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95%20API29%20%28Android10%29%E3%81%BE%E3%81%A7%E3%81%AE%E6%96%B9%E6%B3%95%20android.graphics.Point%20%E3%82%92%E4%BD%BF%E3%81%84%E3%81%BE%E3%81%99%E3%80%82%20%E2%80%BB%E4%BB%A5%E4%B8%8B%E3%81%AFActivity%E3%81%AE%E4%B8%AD%E3%81%A7%E3%81%AE%E8%A8%98%E8%BF%B0%E3%81%AA%E3%81%AE%E3%81%A7%20this%20%E3%82%92%E4%BD%BF%E7%94%A8%E3%81%97%E3%81%A6%E3%81%84%E3%81%BE%E3%81%99%E3%80%82,point.x%3B%20int%20height%20%3D%20point.y%3B%20%E9%96%A2%E6%95%B0%E3%81%AB%E3%81%99%E3%82%8B%E3%82%88%E3%81%86%E3%81%AA%E5%A0%B4%E5%90%88%E3%81%AF%E5%BC%95%E6%95%B0%E3%81%A8%E3%81%97%E3%81%A6%20Activity%20%E3%82%92%E6%B8%A1%E3%81%97%E3%81%A6%E3%81%8F%E3%81%A0%E3%81%95%E3%81%84%E3%80%82
// http://blog.oukasoft.com/%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%A0/%E3%80%90android%E3%80%91surfaceview%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E3%82%B2%E3%83%BC%E3%83%A0%E3%81%A3%E3%81%BD%E3%81%84%E3%82%A2%E3%83%97%E3%83%AA%E3%82%92%E4%BD%9C%E3%81%A3%E3%81%A6%E3%81%BF/
public class snake_game extends AppCompatActivity {
    SnakeGameSurfaceView gameView = null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        gameView = new SnakeGameSurfaceView(this);
        setContentView(gameView);
    }
}

class SnakeGameHolderCallBack implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder = null;
    private Thread thread = null;
    private boolean isAttacked = true;

    private final float screenWidth;
    private final float screenHeight;

    private final float CANVAS_WIDTH = 600;
    private final float CANVAS_HEIGHT = 600;

    public SnakeGameHolderCallBack(float width, float height) {
        super();
        screenWidth = width;
        screenHeight = height;

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.holder = holder;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isAttacked = false;
        thread = null;
    }

    private void main_loop(Canvas canvas) {
        canvas.drawColor(Color.RED);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(50, 50, 50, paint);
    }

    @Override
    public void run() {
        while (isAttacked) {
            final long timerStart = System.currentTimeMillis();

            final float scaleX = screenWidth / CANVAS_WIDTH;
            final float scaleY = screenHeight / CANVAS_HEIGHT;
            final float scale = scaleX > scaleY ? scaleY : scaleX;

            Canvas canvas = holder.lockCanvas();

            canvas.translate(
                    (screenWidth - CANVAS_WIDTH)/2*scale,
                    (screenHeight - CANVAS_HEIGHT)/2*scale
            );
            canvas.scale(scale, scale);

            main_loop(canvas);

            holder.unlockCanvasAndPost(canvas);
            final long timerEnd = System.currentTimeMillis();
            final long WAIT_TIME = 16;
            if ((timerEnd - timerStart) < WAIT_TIME) {
                try {
                    Thread.sleep(WAIT_TIME - (timerEnd - timerStart));
                } catch (InterruptedException e) {}
            }
        }
    }
}

class SnakeGameSurfaceView extends SurfaceView {
    public SnakeGameSurfaceView(Context context) {
        super(context);
        final float width = getWidth();
        final float height = getHeight();
        SurfaceHolder holder = getHolder();
        SnakeGameHolderCallBack callback = new SnakeGameHolderCallBack(width, height);
        holder.addCallback(callback);
    }
}

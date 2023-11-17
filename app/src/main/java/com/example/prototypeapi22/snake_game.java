package com.example.prototypeapi22;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// 参考: https://blog.recruit.co.jp/rmp/mobile/remember_canvas1/
// https://ethanfjp.com/articles/blog/programing/20211219android#:~:text=Android%E3%81%AE%E7%94%BB%E9%9D%A2%E3%82%B5%E3%82%A4%E3%82%BA%E3%82%92%E5%8F%96%E5%BE%97%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95%20API29%20%28Android10%29%E3%81%BE%E3%81%A7%E3%81%AE%E6%96%B9%E6%B3%95%20android.graphics.Point%20%E3%82%92%E4%BD%BF%E3%81%84%E3%81%BE%E3%81%99%E3%80%82%20%E2%80%BB%E4%BB%A5%E4%B8%8B%E3%81%AFActivity%E3%81%AE%E4%B8%AD%E3%81%A7%E3%81%AE%E8%A8%98%E8%BF%B0%E3%81%AA%E3%81%AE%E3%81%A7%20this%20%E3%82%92%E4%BD%BF%E7%94%A8%E3%81%97%E3%81%A6%E3%81%84%E3%81%BE%E3%81%99%E3%80%82,point.x%3B%20int%20height%20%3D%20point.y%3B%20%E9%96%A2%E6%95%B0%E3%81%AB%E3%81%99%E3%82%8B%E3%82%88%E3%81%86%E3%81%AA%E5%A0%B4%E5%90%88%E3%81%AF%E5%BC%95%E6%95%B0%E3%81%A8%E3%81%97%E3%81%A6%20Activity%20%E3%82%92%E6%B8%A1%E3%81%97%E3%81%A6%E3%81%8F%E3%81%A0%E3%81%95%E3%81%84%E3%80%82
// http://blog.oukasoft.com/%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%A0/%E3%80%90android%E3%80%91surfaceview%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E3%82%B2%E3%83%BC%E3%83%A0%E3%81%A3%E3%81%BD%E3%81%84%E3%82%A2%E3%83%97%E3%83%AA%E3%82%92%E4%BD%9C%E3%81%A3%E3%81%A6%E3%81%BF/
// https://tatoapps.wordpress.com/2013/08/17/surfaceview%E3%81%A8canvas%E3%81%A7%E7%94%BB%E9%9D%A2%E3%82%B5%E3%82%A4%E3%82%BA%E3%81%AB%E5%90%88%E3%82%8F%E3%81%9B%E3%81%A6%E6%8F%8F%E7%94%BB%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95-android/
public class snake_game extends AppCompatActivity {
    SnakeGameSurfaceView gameView = null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        gameView = new SnakeGameSurfaceView(this);
        setContentView(gameView);
    }
}

class SnakeGameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder = null;
    private Thread thread = null;
    private boolean isAttacked = true;

    private Bitmap snake_head_image = BitmapFactory.decodeResource();
    private Bitmap snake_body_image = null;
    private Bitmap snake_tail_image = null;
    private Bitmap feed_image = null;

    private float screenWidth = 0;
    private float screenHeight = 0;

    private float scale = 1;
    private float touchedX = 0;
    private float touchedY = 0;

    private SnakeDiretion snakeDirection = SnakeDiretion.Right;

    private final float CANVAS_WIDTH = 900;
    private final float CANVAS_HEIGHT = 900;

    private final long fps = 30;
    private final long move_fps = 10;
    private final long WAIT_TIME = 1000/fps;
    private int move_timing_count = 0;
    private final int move_timing_count_limit = (int)move_fps;

    private final int snakeGameRows = 20;
    private SnakeGameController snakeGame = null;

    public SnakeGameSurfaceView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        final float width = getWidth();
        final float height = getHeight();
        screenWidth = width;
        screenHeight = height;
        snakeGame = new SnakeGameController(snakeGameRows, (int)CANVAS_WIDTH);
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

    private void main_loop(Canvas canvas, boolean is_moving) {
        canvas.drawColor(Color.YELLOW);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(new RectF(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT), paint);
        paint.setStyle(Paint.Style.FILL);
        snakeGame.draw_snake(canvas, paint);

        if (is_moving && ! snakeGame.move_snake(snakeDirection)) {
            isAttacked = false;
            Log.w("hoge", "fin!");
        }
    }

    @Override
    public void run() {
        final float scaleX = screenWidth / CANVAS_WIDTH;
        final float scaleY = screenHeight / CANVAS_HEIGHT;
        final boolean wIsBigger = scaleX > scaleY;
        scale = Math.min(scaleX, scaleY);

        while (isAttacked) {
            final long timerStart = System.currentTimeMillis();

            Canvas canvas = holder.lockCanvas();

            main_loop(canvas, move_timing_count%move_timing_count_limit == 0);

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);

//            canvas.translate(
//                    wIsBigger
//                            ? (screenWidth - CANVAS_WIDTH)*scale/2
//                            : 0,
//                    ! wIsBigger
//                            ? (screenHeight - CANVAS_HEIGHT)*scale/2
//                            : 0
//            );
            canvas.scale(scale, scale);

            holder.unlockCanvasAndPost(canvas);
            final long timerEnd = System.currentTimeMillis();
            if ((timerEnd - timerStart) < WAIT_TIME) {
                try {
                    Thread.sleep(WAIT_TIME - (timerEnd - timerStart));
                } catch (InterruptedException e) {}
            }

            if (move_timing_count%move_timing_count_limit == 0) {
                move_timing_count = 0;
            }
            move_timing_count++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchedX = event.getX() / scale;
        touchedY = event.getY() / scale;

        Log.w("hoge", "" + touchedX + ", " + touchedY);

        if (touchedX < screenWidth/5 && snakeDirection != SnakeDiretion.Right) {
            snakeDirection = SnakeDiretion.Left;
        } else if (touchedX > screenWidth - screenWidth/5 && snakeDirection != SnakeDiretion.Left) {
            snakeDirection = SnakeDiretion.Right;
        } else if (touchedY < screenHeight/2 && snakeDirection != SnakeDiretion.Down) {
            snakeDirection = SnakeDiretion.Up;
        } else if (touchedY > screenHeight - screenHeight/5 && snakeDirection != SnakeDiretion.Up) {
            snakeDirection = SnakeDiretion.Down;
        }

        return true;
    }
}

enum SnakeDiretion {
    Up,
    Down,
    Right,
    Left,
}

class SnakeGameController {
    // 末尾要素をヘビの顔の座標としたヘビの体がある座標のリスト
    private final ArrayList<int[]> snakeBody;
    private final int rows;
    private final int size;
    private final int defaultSnakeLen = 3;

    public SnakeGameController(int rows, int screenWidth) {
        this.rows = rows;
        this.size = screenWidth/rows;
        snakeBody = new ArrayList<int[]>();
        for (int i = 0; i <= defaultSnakeLen; i++) {
            snakeBody.add(new int[]{ i, rows/2 });
        }
    }

    // ヘビを指定方向に動かす。壁や自分に衝突したときはfalseが返される。
    public boolean move_snake(SnakeDiretion d) {
        int[] new_head = null;
        int[] now_head = snakeBody.get(snakeBody.size()-1);
        int now_head_x = now_head[0];
        int now_head_y = now_head[1];
        switch (d) {
            case Up:
                if (now_head_y <= 0) {
                    return false;
                }
                new_head = new int[]{ now_head_x, now_head_y - 1 };
                break;

            case Down:
                if (now_head_y >= rows) {
                    return false;
                }
                new_head = new int[]{ now_head_x, now_head_y + 1 };
                break;

            case Left:
                if (now_head_x <= 0) {
                    return false;
                }
                new_head = new int[]{ now_head_x - 1, now_head_y };
                break;

            case Right:
                if (now_head_x >= rows) {
                    return false;
                }
                new_head = new int[]{ now_head_x + 1, now_head_y };
                break;
        }

        for (final int[] v : snakeBody) {
            if (Arrays.equals(v, new_head)) {
                return false;
            }
        }

        snakeBody.remove(0);
        snakeBody.add(new_head);

        return true;
    }

    public void draw_snake(Canvas canvas, Paint paint) {
        Rect rect = new Rect();
        int i = 0;
        for (int[] v : snakeBody) {
            rect.set(v[0]*size, v[1]*size, v[0]*size+size, v[1]*size+size);
            if (i == snakeBody.size()-1) {
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.RED);
            }
            canvas.drawRect(rect, paint);

            i++;
        }
    }
}


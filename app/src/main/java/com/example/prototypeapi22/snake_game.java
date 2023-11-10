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
public class snake_game extends AppCompatActivity {
    SnakeGameCanvas gameView = null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        gameView = new SnakeGameCanvas(this);
        setContentView(gameView);
    }
}

class SnakeGameCanvas extends SurfaceView implements SurfaceHolder.Callback {

    public SnakeGameCanvas(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);



        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.LTGRAY);
        canvas.drawCircle(500, 500, 50, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}

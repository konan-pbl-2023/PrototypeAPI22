package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class speed_play extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}

//public abstract class speed_play extends AppCompatActivity implements Runnable {
//    final Handler handler = new Handler();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_speed_play);
//
//        // 2秒後に処理が呼ばれるようにメッセージキューに渡す
//        handler.postDelayed(this, 2000);
//    }
//
//    @Override
//    public void run() {
//        // 2秒後に行う処理をここに記述
//        TextView textView = findViewById(R.id.textView);
//        textView.setText("3");
//    }
//}
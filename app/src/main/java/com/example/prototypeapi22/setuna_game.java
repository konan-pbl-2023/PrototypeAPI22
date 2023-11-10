package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class setuna_game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuna_game);

        //変数宣言
        boolean stopRun = false;//ビックリマーク表示まで
        boolean reactionRun = false;//ビックリマーク表示後
        long timer;//タイマー
        long startTime = System.currentTimeMillis();
        long reactionStartTime;
        ImageView reactionMark = findViewById(R.id.reactionMark);//リアクションマークの取得
        reactionMark.setVisibility(View.GONE);


        Random randomTimer = new Random(0);//乱数固定(仮)
        //long randomStartCount = randomTimer.nextInt(10000);

        //ビックリマーク表示処理
        while(!stopRun) {
            long endTime = System.currentTimeMillis();
            long debugTime = endTime  - startTime;
            if (debugTime > 4000 + randomTimer.nextInt(8000)) {

                reactionRun = true;
                stopRun = true;
            }
        }

        //ビックリマーク後の時間カウント
        while(reactionRun){
            reactionMark.setVisibility(View.VISIBLE);
            reactionStartTime = System.currentTimeMillis();
            long reactionEndTime = System.currentTimeMillis();
            Button A_Button = (findViewById(R.id.A_PashButton));
            A_Button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                }
            });


            Button B_Button = (findViewById(R.id.B_PashButton));
            A_Button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                }
            });
            reactionRun = false;


        }

    }
}
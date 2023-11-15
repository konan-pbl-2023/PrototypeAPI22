package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class setuna_game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuna_game);

        ImageView reactionMark = findViewById(R.id.reactionMark);//リアクションマークの取得
        reactionMark.setVisibility(View.GONE);

        //インターバル10msec
        long interval = 10;
        Random randomTimer = new Random(0);//乱数固定(仮)
        //時間作成
        long visibleReactiomTime = 2000 + randomTimer.nextInt(8000);
        final CountDown visibleCountDown = new CountDown(visibleReactiomTime,interval);

        TextView A_text = (findViewById(R.id.A_text));
        TextView B_text = (findViewById(R.id.B_text));


        Button A_Button = (findViewById(R.id.A_PashButton));
        Button B_Button = (findViewById(R.id.B_PashButton));
        Button ready_Button = (findViewById(R.id.readyButton));
        A_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(inGame == true) {
                    if (reactionRun == true) {
                        A_text.setText("勝ち！");
                        B_text.setText("負け！");
                        inGame = false;
                        visibleCountDown.cancel();
                    } else {
                        A_text.setText("お手付き！負け！");
                        B_text.setText("勝ち！");
                        inGame = false;
                        visibleCountDown.cancel();
                    }
                }
            }
        });


        B_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(reactionRun == true){
                    A_text.setText("負け！");
                    B_text.setText("勝ち！");
                    inGame = false;
                    visibleCountDown.cancel();
                }else{
                    A_text.setText("勝ち！");
                    B_text.setText("お手付き！負け！");
                    inGame = false;
                    visibleCountDown.cancel();
                }

            }
        });


        ready_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                invisivleReadyButton();
                visibleCountDown.start();
            }
        });

    }


    class CountDown extends  CountDownTimer{
        CountDown(long millInFuture,long countDownInterval){
            super(millInFuture,countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            visibleReaction();
        }
    }

    //変数宣言
    boolean stopRun = false;//ビックリマーク表示まで
    boolean reactionRun = false;//ビックリマーク表示
    boolean inGame = true;

    long reactionStartTime;



    private void visibleReaction() {
        //ビックリマーク表示処理
        ImageView reactionMark = findViewById(R.id.reactionMark);//リアクションマークの取得
        reactionMark.setVisibility(View.VISIBLE);
        reactionRun = true;
    }


    private void invisivleReadyButton(){
        Button ready_Button = (findViewById(R.id.readyButton));
        ready_Button.setVisibility(View.GONE);
    }
}
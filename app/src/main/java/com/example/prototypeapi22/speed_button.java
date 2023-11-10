package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class speed_button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_button);

        //プレイ画面への移動
        Button buttonPlay = (findViewById(R.id.button_play));
        buttonPlay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent speedPlay = new Intent(speed_button.this,speed_play.class);
                startActivity(speedPlay);
            }
        });
    }
}
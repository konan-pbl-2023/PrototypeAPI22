package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setuna_title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuna_title);

        //シングルプレイボタン処理
        Button singleButton = (findViewById(R.id.singleButton));
        singleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent setunaSingle = new Intent(setuna_title.this,setuna_game_single.class);
                startActivity(setunaSingle);
            }
        });

        //マルチプレイボタン処理
        Button multiButton = (findViewById(R.id.multiButton));
        multiButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent setunaMulti = new Intent(setuna_title.this,setuna_game.class);
                startActivity(setunaMulti);
            }
        });



    }
}
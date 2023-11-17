package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class snake_title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snake_title);

        Button btn = findViewById(R.id.snake_play_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent snakeTitle = new Intent(snake_title.this, snake_game.class);
                startActivity(snakeTitle);
            }
        });
    }
}

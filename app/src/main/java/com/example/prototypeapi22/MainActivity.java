package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int N = 10;
        final String QUESTION = "犬";
        final String ANSER = "inu";
        boolean answers[] = new boolean[N];

        Button nextButton = (Button) findViewById(R.id.nextButton);
        TextView question = (TextView) findViewById(R.id.question);
        EditText input = (EditText) findViewById(R.id.inputAnswer);

        question.setText(QUESTION);

        nextButton.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            public void onClick(View v) {
                if (Objects.equals(ANSER, input.getText().toString())) {
                    Log.d("正誤", i + 1 + "問目は正解です");
                    answers[i] = true;
                } else {
                    Log.d("正誤", i + 1 + "問目は不正解です");
                    answers[i] = false;
                }
                question.setText(QUESTION);
                i += 1;
                input.getEditableText().clear();
                if (N <= i) {
                    Log.d("終了", "終了しました。");
                    Intent intent = new Intent(getApplication(),ResultActivity.class);
                    intent.putExtra("answers", answers);
                    startActivity(intent);
                }
            }
        });
    }
}

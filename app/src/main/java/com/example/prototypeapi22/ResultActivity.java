package com.example.prototypeapi22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity  extends AppCompatActivity {
    private Intent SubActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView score = (TextView) findViewById(R.id.score);

        Intent intent = getIntent();
        boolean[] answers = intent.getBooleanArrayExtra("answers");

        int trueCount = 0;
        for (boolean answer : answers) {
            if (answer) {
                trueCount++;
            }
        }
        score.setText(String.valueOf(trueCount));
    }
}

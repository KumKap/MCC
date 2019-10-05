package com.example.mcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Faq extends AppCompatActivity {
    TextView query,solution;
    Button answer;
    EditText enterquery;
    String request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        query = findViewById(R.id.query);
        answer = findViewById(R.id.answer);
        solution = findViewById(R.id.solution);
        enterquery = findViewById(R.id.enterquery);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = enterquery.getText().toString();
                if(request.contains("check score")||request.contains("get score")||request.contains("know my score")){
                    solution.setText("Click on 'Check on Score' button on menu page.");
                }
                else if(request.contains("check image")||request.contains("image stored")||request.contains("stored image")){
                    solution.setText("It is stored in folder named 'Kumel' in SD card");
                }
                else{
                    solution.setText("Email your query at 'dubeankit07@gmail.com'");
                }

            }
        });
    }
}

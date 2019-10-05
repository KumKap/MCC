package com.example.mcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Score extends AppCompatActivity {

    TextView score, captcha;
    EditText ans;
    int a, b, sum;
    String eq;
    Button sub;
    String result;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Score.this);
        if (acct != null) {
            Email = acct.getEmail();
            Toast.makeText(getApplicationContext(), Email, Toast.LENGTH_SHORT).show();
        }
        score = findViewById(R.id.score);
        captcha = findViewById(R.id.captcha);
        ans = findViewById(R.id.ans);
        sub = findViewById(R.id.submit);
        a = (int) (Math.random() * ((20 - 10) + 1)) + 10;
        b = (int) (Math.random() * ((20 - 10) + 1)) + 10;
        sum = a + b;
        eq = a + "+" + b + "= ";
        captcha.setText(eq);


        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("email", Email);


        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Score.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    score.setText("Answer Captcha");


                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();

                }

            }
        });
        try {
            result = task2.execute("http://192.168.43.170/mcc/score.php").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        task2.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getApplicationContext(), "Cannot connect to server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "URL Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getApplicationContext(), "Protocol Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Encoding Error!", Toast.LENGTH_SHORT).show();

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Integer.parseInt(ans.getText().toString()) == sum) {

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    score.setText("Score is " + result);

                }
                else{
                    score.setText("Wrong Answer" );

                }
            }
        });


    }
}

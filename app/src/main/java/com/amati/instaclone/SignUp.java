package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {

    private EditText userSign , passSign , userLog , passLog;
    private Button btnSign , btnLog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userLog = findViewById(R.id.LoginUser);
        userSign = findViewById(R.id.signupUsername);
        passLog = findViewById(R.id.PasswordLogin);
        passSign = findViewById(R.id.PasswordSignup);

        btnLog = findViewById(R.id.Login);
        btnSign = findViewById(R.id.Signup);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ParseUser appUser =  new ParseUser();
                appUser.setUsername(userSign.getText().toString());
                appUser.setPassword(passSign.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){

                            FancyToast.makeText(SignUp.this,appUser.getUsername() + "\n Joined!!" ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            startActivity(new Intent(SignUp.this,Home.class));

                        }else {
                            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                        }
                    }
                });

            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(userLog.getText().toString(), passLog.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(SignUp.this,user.getUsername() + "\n Welcome!!" ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                        }else{
                          FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();


                        }
                    }
                });
            }
        });




    }
}

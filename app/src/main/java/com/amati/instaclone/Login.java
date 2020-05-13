package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity {
    private EditText emailLogin, passwordLogin;
    private Button btnLogin, btnSignupPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordlogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignupPage = findViewById(R.id.btnSignUpPage);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Please Wait We Logged you in");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ParseUser.logInInBackground(emailLogin.getText().toString(), passwordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(Login.this,"Thanks for Login: " + ParseUser.getCurrentUser().getUsername() , Toast.LENGTH_LONG,FancyToast.SUCCESS,true ).show();

                            progressDialog.dismiss();


                        }else{

                            FancyToast.makeText(Login.this,e.getMessage(), Toast.LENGTH_LONG,FancyToast.ERROR,true ).show();
                            progressDialog.dismiss();

                        }
                    }
                });
            }
        });


        btnSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }
}

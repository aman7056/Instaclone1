package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
            gotoHome();
        }

        final View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailLogin.getText().toString().equals("") || passwordLogin.getText().toString().equals("")) {
                    FancyToast.makeText(Login.this, "Please Fill All Fields", Toast.LENGTH_LONG, FancyToast.INFO, true).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Please Wait We Logged you in");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ParseUser.logInInBackground(emailLogin.getText().toString(), passwordLogin.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(Login.this, "Thanks for Login: " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                progressDialog.dismiss();
                                gotoHome();

                            } else {

                                FancyToast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                progressDialog.dismiss();

                            }
                        }
                    });

                }
            }

            ;

        };

        btnLogin.setOnClickListener(onClick);

        passwordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER &&  keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                onClick.onClick(btnLogin);
                gotoHome();
                }

                return false;
            }
        });


        btnSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    public void rootTapped (View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void gotoHome()
    {
        startActivity(new Intent(Login.this, Home.class));
    }
}

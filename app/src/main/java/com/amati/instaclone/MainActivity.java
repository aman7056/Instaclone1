package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText username, email, password;
    private Button btnSignUp, btnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);



        btnSignUp = findViewById(R.id.btnSignup);
        btnLoginPage = findViewById(R.id.btnLoginPage);



        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }


        final View.OnClickListener onClickListener = new OnClickListener() {

    @Override
    public void onClick(View view) {

        if (email.getText().toString().equals("") || password.getText().toString().equals("") || username.getText().toString().equals("")) {

            FancyToast.makeText(MainActivity.this, "Please fill all fields first ", Toast.LENGTH_LONG, FancyToast.WARNING, true).show();

        } else {

            final ParseUser amati = new ParseUser();
            amati.setEmail(email.getText().toString());
            amati.setUsername(username.getText().toString());
            amati.setPassword(password.getText().toString());
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait While Signing in");
            progressDialog.setCancelable(false);
            progressDialog.show();
            amati.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        FancyToast.makeText(MainActivity.this, "Thanks for signing: " + amati.getUsername(), Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        progressDialog.dismiss();
                    } else {

                        FancyToast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        progressDialog.dismiss();
                    }
                }
            });


        }

    }
};

       btnSignUp.setOnClickListener(onClickListener);




        btnLoginPage.setOnClickListener(new View.OnClickListener()

           {
               @Override
               public void onClick (View view){
               startActivity(new Intent(MainActivity.this, Login.class));

           }
           });


        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                    onClickListener.onClick(btnSignUp);
                }

                return false;
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
}

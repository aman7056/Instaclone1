package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class Home extends AppCompatActivity {
    private TextView txtWelcome;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtWelcome = findViewById(R.id.Welcome);
        btnLogout = findViewById(R.id.Logout);


        txtWelcome.setText(ParseUser.getCurrentUser() + " " );
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                finish();
            }
        });

    }


}

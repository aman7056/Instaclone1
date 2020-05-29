package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class sendTweet extends AppCompatActivity {
    private EditText tweetText;
    private Button sendtweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        tweetText = findViewById(R.id.tweetText);
        sendtweet = findViewById(R.id.sendTweet);

        sendtweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("Tweet");
                parseObject.put("tweet", tweetText.getText().toString());
                parseObject.put("users", ParseUser.getCurrentUser().getUsername());

                final ProgressDialog progressDialog = new ProgressDialog(sendTweet.this);
                progressDialog.setMessage("Saving Your Tweet");
                progressDialog.setCancelable(false);
                progressDialog.show();

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(sendTweet.this, "Your Tweet is Saved : " + tweetText.getText().toString() + "\n" + ParseUser.getCurrentUser().getUsername(),
                                    Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            startActivity(new Intent(sendTweet.this, TwitterPage.class));
                            finish();
                        }else {
                            FancyToast.makeText(sendTweet.this,e.getMessage() + "",
                                    Toast.LENGTH_SHORT,FancyToast.ERROR,true ).show();
                        }

                        progressDialog.dismiss();
                    }
                });

            }
        });
    }
}

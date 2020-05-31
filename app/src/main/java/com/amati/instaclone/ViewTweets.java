package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTweets extends AppCompatActivity {

    private ListView viewTweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tweets);

        viewTweetList = findViewById(R.id.viewTweetList);

        final ArrayList<HashMap<String, String>> tweetList = new ArrayList<>();
        final SimpleAdapter simpleAdapter = new SimpleAdapter(ViewTweets.this, tweetList, android.R.layout.simple_list_item_2,
                new String[]{"tweetUserName", "tweetvalue"}, new int[]{android.R.id.text1, android.R.id.text2});
        try {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Tweet");
            parseQuery.whereContainedIn("users", ParseUser.getCurrentUser().getList("fanOf"));

            final ProgressDialog progressDialog = new ProgressDialog(ViewTweets.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                   if (objects.size() > 0 && e == null){
                       for (ParseObject tweet : objects){
                           HashMap<String, String> tweets = new HashMap<>();
                           tweets.put("tweetUserName" , tweet.get("users").toString());
                           tweets.put("tweetvalue", tweet.get("tweet").toString());
                           tweetList.add(tweets);
                       }
                       viewTweetList.setAdapter(simpleAdapter);
                       progressDialog.dismiss();

                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();;
        }



    }
}

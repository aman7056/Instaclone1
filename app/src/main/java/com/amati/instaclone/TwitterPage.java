package com.amati.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterPage extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_page);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        setTitle("Twitter");

        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(TwitterPage.this,android.R.layout.simple_list_item_checked, arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
try {
    final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

    final ProgressDialog progressDialog = new ProgressDialog(TwitterPage.this);
    progressDialog.setMessage("Loading");
    progressDialog.setCancelable(false);
    progressDialog.show();

    parseQuery.findInBackground(new FindCallback<ParseUser>() {
        @Override
        public void done(List<ParseUser> objects, ParseException e) {
            if (e == null){
                if (objects.size() >= 0) {

                    for (ParseUser user : objects){
                        arrayList.add(user.getUsername());
                    }

                    listView.setAdapter(arrayAdapter);
                    for (String user : arrayList){
                        if (ParseUser.getCurrentUser().get("fanOf") != null){
                            if (ParseUser.getCurrentUser().getList("fanOf").contains(user)){

                                listView.setItemChecked(arrayList.indexOf(user), true);
                            }
                        }
                    }


                    progressDialog.dismiss();
                }

            }
        }
    });


}catch(Exception e){
    e.getMessage();

}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()){

                    FancyToast.makeText(TwitterPage.this, arrayList.get(position) + "is now Follwed" , Toast.LENGTH_SHORT, FancyToast.INFO, true ).show();
                    ParseUser.getCurrentUser().add("fanOf" , arrayList.get(position));
                }else {
                    FancyToast.makeText(TwitterPage.this, arrayList.get(position) + "is now Unfollwed" , Toast.LENGTH_SHORT, FancyToast.INFO, true ).show();

                    ParseUser.getCurrentUser().getList("fanOf").remove(arrayList.get(position));
                    List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
                    ParseUser.getCurrentUser().getList("fanOf");
                    ParseUser.getCurrentUser().put("fanOf", currentUserFanOfList);
                }
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null ){
                            FancyToast.makeText(TwitterPage.this, "Done",Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        }
                    }
                });

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sendTweet:
                startActivity(new Intent(TwitterPage.this, sendTweet.class));

                break;
            case R.id.viewTweet:
                startActivity(new Intent(TwitterPage.this,ViewTweets.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class chat extends AppCompatActivity {
    private Toolbar mToolbar;
    private String selectedUser;
    private ListView chatListView;
    private ArrayList<String> chatList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        selectedUser = getIntent().getStringExtra("selectedUser");

        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        setTitle(selectedUser);



        chatListView = findViewById(R.id.chatlist);
        chatList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,chatList);
        chatListView.setAdapter(arrayAdapter);

        try {
            ParseQuery<ParseObject> firstUser = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secoundUser = ParseQuery.getQuery("Chat");

            firstUser.whereEqualTo("waSender", ParseUser.getCurrentUser().getUsername());
            firstUser.whereEqualTo("waReciver" , selectedUser);

            secoundUser.whereEqualTo("waSender", selectedUser);
            secoundUser.whereEqualTo("waReciver", ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQuery = new ArrayList<>();
            allQuery.add(firstUser);
            allQuery.add(secoundUser);


            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQuery);
            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null){
                        for (ParseObject chatObject : objects){
                            String waMessage = chatObject.get("waMessage") + "" ;
                            if (chatObject.get("waSender").equals(ParseUser.getCurrentUser().getUsername())){
                                waMessage = ParseUser.getCurrentUser().getUsername() + " : " + waMessage;
                             }if (chatObject.get("waReciver").equals(selectedUser)){
                                waMessage = selectedUser + " : " + waMessage;
                            }

                            chatList.add(waMessage);
                        }
                        arrayAdapter.notifyDataSetChanged();


                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edtmessage = findViewById(R.id.txtMessage);

                ParseObject chat = new ParseObject("Chat");
                chat.put("waSender", ParseUser.getCurrentUser().getUsername());
                chat.put("waReciver", selectedUser );
                chat.put("waMessage" , edtmessage.getText().toString());
                chat.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e ==null){
                            chatList.add(ParseUser.getCurrentUser().getUsername() + " : " + edtmessage.getText().toString());
                            arrayAdapter.notifyDataSetChanged();
                            edtmessage.setText("");

                        }
                    }
                });
            }
        });

    }
}

package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn , btnsign;
    private EditText editText1, editText2, editText3, editText4, editText5;

    private  TextView textView;
    public String allCricketer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        btnsign = findViewById(R.id.btn_signup);

        textView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    final ParseObject cricket = new ParseObject("Cricketer");
                    cricket.put("name", editText1.getText().toString());
                    cricket.put("sixes", Integer.parseInt(editText2.getText().toString()));
                    cricket.put("half_Centuries", Integer.parseInt(editText3.getText().toString()));
                    cricket.put("centuries", Integer.parseInt(editText4.getText().toString()));
                    cricket.put("player_Rank", Integer.parseInt(editText5.getText().toString()));
                    cricket.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this,cricket.get("name") + " is saved" ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            } else {

                                FancyToast.makeText(MainActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                            }
                        }
                    });
                } catch (Exception e){
                    FancyToast.makeText(MainActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCricketer ="";
                final ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Cricketer");
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        for (ParseObject parseObject: objects){
                            allCricketer = allCricketer + parseObject.get("name") + "\n" ;
                        }
                        if (e == null){
                            if ((objects.size())>0){
                                textView.setText(allCricketer +"\n" );
                                FancyToast.makeText(MainActivity.this,allCricketer + "\n" ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            }
                        }
                    }
                });

            }
        });


        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

}

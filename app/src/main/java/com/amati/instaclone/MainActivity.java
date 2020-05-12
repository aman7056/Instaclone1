package com.amati.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private EditText editText1, editText2, editText3, editText4, editText5;

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

    }

}

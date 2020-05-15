package com.amati.instaclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtName, edtHobby, edtPassion, edtSchool, edtDOB, edtBio;
    private Button btnUpdate;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtName = view.findViewById(R.id.edtName);
        edtBio = view.findViewById(R.id.edtBio);
        edtHobby = view.findViewById(R.id.edtHobby);
        edtPassion = view.findViewById(R.id.edtPassion);
        edtSchool = view.findViewById(R.id.edtSchool);
        edtDOB = view.findViewById(R.id.edtDOB);

        btnUpdate = view.findViewById(R.id.btnUpdate);


        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("nickName") == null) {
            edtName.setText("");
        } else {
            edtName.setText(parseUser.get("nickName").toString());
        }

        if (parseUser.get("bio") == null) {
            edtBio.setText("");
        } else {
            edtBio.setText(parseUser.get("bio") + "");
        }

        if (parseUser.get("school") == null) {
            edtSchool.setText("");
        } else {
            edtSchool.setText(parseUser.get("school") + "");
        }

        if (parseUser.get("hobby") == null) {
            edtHobby.setText("");
        } else {
            edtHobby.setText(parseUser.get("hobby") + "");
        }

        if (parseUser.get("passion") == null) {
            edtPassion.setText("");
        } else {
            edtPassion.setText(parseUser.get("passion") + "");
        }

        if (parseUser.get("dob") == null) {
            edtDOB.setText("");
        } else {
            edtDOB.setText(parseUser.get("dob") + "");
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseUser.put("nickName", edtName.getText().toString());
                parseUser.put("bio", edtBio.getText().toString());
                parseUser.put("school", edtSchool.getText().toString());
                parseUser.put("hobby", edtHobby.getText().toString());
                parseUser.put("passion", edtPassion.getText().toString());
                parseUser.put("dob", edtDOB.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Please Wait Saving Your details");
                progressDialog.setCancelable(false);
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), "Info Updated", Toast.LENGTH_LONG, FancyToast.INFO, true).show();
                            progressDialog.dismiss();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            progressDialog.dismiss();
                        }
                    }
                });


            }
        });
   return view;
}
}

package com.amati.instaclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment  {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab, container, false);

        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        Context context;
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait we are downloading data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {
                            arrayList.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        progressDialog.dismiss();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), UserPosts.class);
                intent.putExtra("username", arrayList.get(i));
                startActivity(intent);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                parseQuery.whereEqualTo("username", arrayList.get(i));
                parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null && e == null) {

//                    FancyToast.makeText(getContext(), user.get("profileProfession") + "",
//                            Toast.LENGTH_SHORT, FancyToast.SUCCESS,
//                            true).show();
                            final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                            prettyDialog.setTitle(user.getUsername() + " 's Info")
                                    .setMessage(user.get("nickName") + "\n"
                                            + user.get("bio") + "\n"
                                            + user.get("dob") + "\n"
                                            + user.get("hobby") + "\n"
                                            + user.get("school") + "\n"
                                            + user.get("passion"))
                                    .setIcon(R.drawable.ic_person_black_24dp)
                                    .addButton(
                                            "OK",     // button text
                                            R.color.pdlg_color_white,  // button text color
                                            R.color.pdlg_color_green,  // button background color
                                            new PrettyDialogCallback() {  // button OnClick listener
                                                @Override
                                                public void onClick() {
                                                    // Do what you gotta do
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();


                        }
                    }
                });


                return true;
            }
        });

        return view;
    }


}

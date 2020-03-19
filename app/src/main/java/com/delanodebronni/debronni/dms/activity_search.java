package com.delanodebronni.debronni.dms;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class activity_search extends AppCompatActivity {
    Button btnSearch;
    EditText txtSearch;
    Spinner spinner;
    String searchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner = (Spinner) findViewById(R.id.spinner);
        txtSearch = (EditText)findViewById(R.id.txtsearch);

        final Intent home = new Intent(activity_search.this,contacts.class);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInfo = txtSearch.getText().toString().trim();

                try {
                    if (searchInfo.length() > 0) {
                        databaseHelper db = new databaseHelper(getApplicationContext());
                        db.search(searchInfo);
                        // making input filled text blank
                        txtSearch.setText("");

                        // Hiding the keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);

                        // loading spinner with newly added data
                        loadSpinnerData(searchInfo);

                        db.search(searchInfo);

                        // Loading spinner data from database
                        loadSpinnerData(searchInfo);
                        if (spinner.getCount()==0){
                            Toast.makeText(activity_search.this, ""+searchInfo+" was not found!", Toast.LENGTH_SHORT).show();
                        }
                        TextView txtCount;
                        txtCount = (TextView)findViewById(R.id.txtCount);
                        txtCount.setText(spinner.getCount()+" contact(s) found!");
                        db.close();
                    }else{
                        Toast.makeText(activity_search.this, "Please enter information before continuing!", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception ex){
                    Snackbar.make(view, ""+searchInfo+" was not found!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(home);
                }catch(Exception ex){
                    Snackbar.make(view, "Could not return home!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    private void loadSpinnerData(String name) {
        databaseHelper db = new databaseHelper(getApplicationContext());
        List<String> labels = db.search(name);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        db.close();
    }

}

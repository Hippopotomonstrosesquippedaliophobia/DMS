package com.delanodebronni.debronni.dms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addContact extends AppCompatActivity {
    Button button;
    databaseHelper myDb = new databaseHelper(this);
    EditText fullname,email,hnum ,cnum,type;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Contact");
        setContentView(R.layout.activity_add_contact);

            //Get element ids
            fullname=(EditText)findViewById(R.id.name);
            String NAME = fullname.getText().toString();
            email=(EditText)findViewById(R.id.email);
            String EMAIL = fullname.getText().toString();
            hnum=(EditText)findViewById(R.id.hnum);
            String HNUM = fullname.getText().toString();
            cnum=(EditText)findViewById(R.id.cnum);
            String CNUM = fullname.getText().toString();
            type=(EditText)findViewById(R.id.type);
            String TYPE = fullname.getText().toString();
            button= (Button)findViewById(R.id.button);

           //Checking whether the EditText is empty or not

            AddData();
            //viewAll();
    }

    public  void AddData() {
        final MediaPlayer mp = MediaPlayer.create(addContact.this,R.raw.dms);
        button.setOnClickListener(
                        new View.OnClickListener() {
                    //Intent viewContact = new Intent(addContact.this, viewContact.class);
                    Intent home = new Intent(addContact.this, contacts.class);
                    @Override
                    public void onClick(View v) {
                        if ((fullname.getText().toString().equals("") == true) || (email.getText().toString().equals("") == true) || (hnum.getText().toString().equals("") == true) || (cnum.getText().toString().equals("") == true) ||(type.getText().toString().equals("") == true)) {
                            Toast.makeText(addContact.this, "Please fill all the fields before continuing!", Toast.LENGTH_LONG).show();
                        }else
                        {
                            boolean isInserted = true;
                            mp.start();
                            myDb.insertData(fullname.getText().toString(),
                                    email.getText().toString(),
                                    hnum.getText().toString(),
                                    cnum.getText().toString(),
                                    type.getText().toString(),
                                    "false");
                            if (isInserted == true) {
                               String idcell = myDb.getContactID(fullname.getText().toString(),email.getText().toString());
                                //showMessage("Error","Nothing found");
                                //viewContact.putExtra("IDCELL", idcell);
                                startActivity(home);
                                Toast.makeText(addContact.this, "Contact added!", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(addContact.this, "Could not add contact to the database!", Toast.LENGTH_LONG).show();
                            }
                        }
                        myDb.close();
                    }

                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

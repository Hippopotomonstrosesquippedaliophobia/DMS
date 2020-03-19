package com.delanodebronni.debronni.dms;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class database extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        databaseHelper myDb = new databaseHelper(getApplicationContext());
        Cursor res2 = myDb.getAllData();
        if (res2.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res2.moveToNext()) {
            buffer.append("Id :" + res2.getString(0) + "\n");
            buffer.append("Name :" + res2.getString(1) + "\n");
            buffer.append("Email :" + res2.getString(2) + "\n");
            buffer.append("Home num :" + res2.getString(3) + "\n");
            buffer.append("Cell num :" + res2.getString(4) + "\n");
            buffer.append("type :" + res2.getString(5) + "\n");
            buffer.append("fav :" + res2.getString(6) + "\n\n");
        }
        showMessage("Contact Database", buffer.toString());
        //Close Res
        res2.close();


        TextView business,personal;

        business = (TextView)findViewById(R.id.business);
        personal = (TextView)findViewById(R.id.personal);

        Button btnClear,btnView;
        int b = myDb.getBusiness();
        int p = myDb.getPersonal();

        //Toast.makeText(database.this, ""+b+" "+p, Toast.LENGTH_SHORT).show();
        try{
            String bt = Integer.toString(b);
            String pt = Integer.toString(p);
            business.setText(bt);
            personal.setText(pt);
        }catch(Exception ex){

        }
        btnView = (Button)findViewById(R.id.btnView);

        //Close Db
        myDb.close();

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CLEAR DATABASE
                databaseHelper myDb = new databaseHelper(getApplicationContext());
                Integer deletedRows = myDb.deleteData();
                if (deletedRows > 0) {
                    Toast.makeText(database.this, "Database Deleted!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(database.this, "Database could not be deleted!", Toast.LENGTH_LONG).show();
                }
            }

        });
        btnView = (Button)findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VIEW DATABASE
                databaseHelper myDb = new databaseHelper(getApplicationContext());
                Cursor res2 = myDb.getAllData();
                if (res2.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res2.moveToNext()) {
                    buffer.append("Id :" + res2.getString(0) + "\n");
                    buffer.append("Name :" + res2.getString(1) + "\n");
                    buffer.append("Email :" + res2.getString(2) + "\n");
                    buffer.append("Home num :" + res2.getString(3) + "\n");
                    buffer.append("Cell num :" + res2.getString(4) + "\n");
                    buffer.append("type :" + res2.getString(5) + "\n");
                    buffer.append("fav :" + res2.getString(6) + "\n\n");
                }
                showMessage("Contact Database", buffer.toString());
                res2.close();
                myDb.close();
            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

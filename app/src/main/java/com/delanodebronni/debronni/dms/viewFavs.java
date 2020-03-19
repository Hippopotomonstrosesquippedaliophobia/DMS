package com.delanodebronni.debronni.dms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class viewFavs extends AppCompatActivity {
    databaseHelper myDb = new databaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_viewfavs);

        final Intent favs = new Intent(viewFavs.this,favourites.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabhome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(favs);
                }catch(Exception ex){
                    Snackbar.make(view, "Could not return to home screen!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        String user_name;
        String id;
        //Getting the value stored in the name "NAME"
        try{
            id= bundle.getString("IDCELL1");
            //Toast.makeText(viewFavs.this, ""+id, Toast.LENGTH_SHORT).show();

            FloatingActionButton fabfav = (FloatingActionButton) findViewById(R.id.fabfav);
            fabfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CODE HERE
                TextView name = (TextView)findViewById(R.id.name);
                TextView email = (TextView)findViewById(R.id.email);
                TextView hnum = (TextView)findViewById(R.id.hnum);
                TextView cnum = (TextView)findViewById(R.id.cnum);
                TextView type = (TextView)findViewById(R.id.type);

                String NAME = name.getText().toString();
                String EMAIL = email.getText().toString();
                String CNUM = cnum.getText().toString();
                String HNUM = hnum.getText().toString();
                String TYPE = type.getText().toString();
                String FAV = "true";
                try{
                   String idtemp = myDb.getContactID(NAME,EMAIL);
                    Toast.makeText(viewFavs.this,"ID TEMP: "+idtemp,Toast.LENGTH_LONG).show();


                    int rows = myDb.updateData(idtemp,NAME,EMAIL,HNUM,CNUM,TYPE,FAV);
                    if(rows == 1) {
                        Toast.makeText(viewFavs.this, "Favourited!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(viewFavs.this, "Favourite Removed!", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception ex){
                    Snackbar.make(view, "Could not favourite!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });



            //Toast.makeText(viewContact.this, ""+user_name+"", Toast.LENGTH_SHORT).show();

            if(bundle!=null)
            {
                Cursor res3 = myDb.getFavContact(id);
                if(res3.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }
                    String name = res3.getString(1);
                    //Toast.makeText(viewFavs.this, ""+name, Toast.LENGTH_SHORT).show();

                    TextView email = (TextView) findViewById(R.id.email);
                    TextView hnum = (TextView) findViewById(R.id.hnum);
                    TextView cnum = (TextView) findViewById(R.id.cnum);
                    TextView type = (TextView) findViewById(R.id.type);

                    email.append(res3.getString(2));
                    hnum.append(res3.getString(3));
                    cnum.append(res3.getString(4));
                    type.append(res3.getString(5));

                    // closing connection
                    res3.close();
                    myDb.close();
            }else{
                user_name = null;
                Toast.makeText(viewFavs.this, "SENDING NULL", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){

            }



        /**
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Email :"+ res.getString(2)+"\n");
            buffer.append("Home num :"+ res.getString(3)+"\n");
            buffer.append("Cell num :"+ res.getString(4)+"\n");
            buffer.append("type :"+ res.getString(5)+"\n\n");
        }

        // Show all data
        showMessage("Data",buffer.toString());
         */

        //New code to be added
        //TextView txt1=(TextView)findViewById(R.id.TextViewName);



        //setTitle(""+NAME+"");
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    }

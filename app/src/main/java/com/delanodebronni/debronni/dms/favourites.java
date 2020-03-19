package com.delanodebronni.debronni.dms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class favourites extends AppCompatActivity {
    GridView grid;
    static int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        grid=(GridView)findViewById(R.id.grid);
        final Intent home = new Intent(favourites.this,contacts.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(home);
                }catch(Exception ex){
                    Snackbar.make(view, "Could not open add_contact activity!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        // Loading data from database
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbedcontact2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent Searchpage = new Intent(favourites.this,activity_search.class);
        Intent About = new Intent(favourites.this,About.class);
        Intent contact = new Intent(favourites.this,contacts.class);
        Intent Add = new Intent(favourites.this,addContact.class);
        Intent database = new Intent(favourites.this,database.class);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Search) {
            //Search
            startActivity(Searchpage);
        }
        if (id == R.id.action_Add) {
            //Delete all contacts
            startActivity(Add);
        }
        if ( id == R.id.action_Contact){
            //Count contacts
            startActivity(contact);
        }
        if ( id == R.id.action_About){
            //About
            startActivity(About);
        }
        if ( id == R.id.action_database){
            //About
            startActivity(database);
        }
        else{

        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Function to load the data from SQLite database
     * */
    private void loadData() {
        //
        databaseHelper db = new databaseHelper(getApplicationContext());
        final List<String> contacts = db.getAllFavs();

        ArrayAdapter <String> gridAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
        grid.setAdapter(gridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(favourites.this, "Clicked!"+position, Toast.LENGTH_SHORT).show();
                String pos = Integer.toString(position);
                //Intent viewFavs = new Intent(favourites.this,viewFavs.class);
                //viewFavs.putExtra("IDCELL1", pos);
                //startActivity(viewFavs);

            }

        });
        //Close DB
        db.close();
    }

    public void getInfo(int position){
        return ;
    }

}

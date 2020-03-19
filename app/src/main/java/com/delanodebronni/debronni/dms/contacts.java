package com.delanodebronni.debronni.dms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class contacts extends AppCompatActivity {
    GridView grid1;
    static int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        grid1=(GridView)findViewById(R.id.gridView1);
        final Intent add = new Intent(contacts.this,addContact.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(add);
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
        getMenuInflater().inflate(R.menu.menu_tabbedcontact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent Searchpage = new Intent(contacts.this,activity_search.class);
        Intent About = new Intent(contacts.this,About.class);
        Intent favs = new Intent(contacts.this,favourites.class);
        Intent Add = new Intent(contacts.this,addContact.class);
        Intent database = new Intent(contacts.this,database.class);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Search) {
            //Search
            startActivity(Searchpage);
        }
        if (id == R.id.action_Add) {
            //Delete all contacts
            startActivity(Add);
        }
        if ( id == R.id.action_Fav){
            //Count contacts
            startActivity(favs);
        }
        if ( id == R.id.action_About){
            //About
            startActivity(About);
        }
        if(id== R.id.action_database){
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
        final List<String> contacts = db.getAllLabels();

        ArrayAdapter <String> gridAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
        grid1.setAdapter(gridAdapter);

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(contacts.this, "Clicked!"+position, Toast.LENGTH_SHORT).show();
                String pos = Integer.toString(position);
                Intent viewContact = new Intent(contacts.this,viewContact.class);
                viewContact.putExtra("IDCELL", pos);
                viewContact.putExtra("NAME", "");
                startActivity(viewContact);

            }

        });
        db.close();
    }

    public void getInfo(int position){
        return ;
    }

}

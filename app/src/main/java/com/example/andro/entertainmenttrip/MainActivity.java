package com.example.andro.entertainmenttrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listview;
    ArrayList list = new ArrayList();
    ArrayList<String> listname = new ArrayList();
    DatabaseReference firebase;
    Button delete, insert;
    EditText name, price, palace;
    static String select = "";
    Query query;

    public void read() {

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot g : dataSnapshot.getChildren()) {
                    trip o = g.getValue(trip.class);

                    list.add(o);
                    listname.add(o.getName());
                }
                listview.setAdapter(new myadapter(MainActivity.this, R.layout.item, list));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview = findViewById(R.id.list_view);
        delete = findViewById(R.id.delete);
        insert = findViewById(R.id.ins);
        name = findViewById(R.id.name);
        palace = findViewById(R.id.palace);
        price = findViewById(R.id.price);
        firebase = FirebaseDatabase.getInstance().getReference("runa").child("user");
        read();
        stopService(new Intent(MainActivity.this,FireBaseService.class));
        startService(new Intent(MainActivity.this,FireBaseService.class));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip trip1 = new trip();
                if (!name.getText().toString().matches("") && !palace.getText().toString().matches("") && !price.getText().toString().matches("")) {
                    trip1.setName(name.getText().toString());
                    trip1.setPalace(palace.getText().toString());
                    trip1.setPrice(Double.parseDouble(price.getText().toString()));
                    trip1.setRead(false);
                    firebase.push().setValue(trip1);
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "please complete data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(!select.matches("")){
                query = firebase.orderByChild("name").equalTo(select);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null)
                            dataSnapshot.getChildren().iterator().next().getRef().removeValue();
                        read();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}
                else {
    Toast.makeText(MainActivity.this, "please select item", Toast.LENGTH_SHORT).show();
}
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            findViewById(R.id.x).setVisibility(View.VISIBLE);
            findViewById(R.id.in).setVisibility(View.GONE);
            read();
        } else if (id == R.id.nav_gallery) {
            findViewById(R.id.x).setVisibility(View.GONE);
            findViewById(R.id.in).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
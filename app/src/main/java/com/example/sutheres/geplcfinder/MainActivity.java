package com.example.sutheres.geplcfinder;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sutheres.geplcfinder.data.PLCContract;
import com.example.sutheres.geplcfinder.data.PLCContract.PLCEntry;
import com.example.sutheres.geplcfinder.data.PLCDbHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PLC_LOADER_ID = 1;
    GoogleMap myMap;
    boolean mapReady = false;
    Cursor cursor;
    PLCCursorAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editIntent);
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        myMap = googleMap;
        LatLng cincinnati = new LatLng(39.1031, -84.5120);
        CameraPosition target = CameraPosition.builder().target(cincinnati).zoom(4).build();
        myMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));




        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PLCContract.PLCEntry._ID,
                PLCEntry.COLUMN_IP_ADDRESS,
                PLCEntry.COLUMN_ORGANIZATION,
                PLCEntry.COLUMN_LATITUDE,
                PLCEntry.COLUMN_LONGITUDE,
                PLCEntry.COLUMN_CITY};

        // Perform a query on the pets table
        cursor = getContentResolver().query(PLCEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        //TextView displayView = (TextView) findViewById(R.id.textview);

            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            //displayView.setText("The plc table contains " + cursor.getCount() + " PLCs.\n\n");
            //displayView.append(PLCEntry._ID + " - " +
            //        PLCEntry.COLUMN_IP_ADDRESS + " - " +
            //        PLCEntry.COLUMN_ORGANIZATION + " - " +
            //        PLCEntry.COLUMN_LATITUDE + " - " +
            //        PLCEntry.COLUMN_LONGITUDE + " - " +
            //        PLCEntry.COLUMN_CITY + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PLCEntry._ID);
            int ipAddressColumnIndex = cursor.getColumnIndex(PLCEntry.COLUMN_IP_ADDRESS);
            int organizationColumnIndex = cursor.getColumnIndex(PLCEntry.COLUMN_ORGANIZATION);
            int latitudeColumnIndex = cursor.getColumnIndex(PLCEntry.COLUMN_LATITUDE);
            int longitudeColumnIndex = cursor.getColumnIndex(PLCEntry.COLUMN_LONGITUDE);
            int cityColumnIndex = cursor.getColumnIndex(PLCEntry.COLUMN_CITY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentIPAddress = cursor.getString(ipAddressColumnIndex);
                String currentOrg = cursor.getString(organizationColumnIndex);
                String currentLat = cursor.getString(latitudeColumnIndex);
                String currentLng = cursor.getString(longitudeColumnIndex);
                String currentCity = cursor.getString(cityColumnIndex);
                LatLng currentLatLng = new LatLng(Float.parseFloat(currentLat), Float.parseFloat(currentLng));
                MarkerOptions currentMarkerOptions = new MarkerOptions().position(currentLatLng).title(currentOrg);
                myMap.addMarker(currentMarkerOptions);


                ListView plcListView = (ListView)findViewById(R.id.list);
                mAdapter = new PLCCursorAdapter(this, cursor);

                plcListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent editIntent = new Intent(MainActivity.this, EditorActivity.class);

                        Uri currentPLCUri = ContentUris.withAppendedId(PLCEntry.CONTENT_URI, id);

                        editIntent.setData(currentPLCUri);

                        startActivity(editIntent);
                    }
                });

                plcListView.setAdapter(mAdapter);
                // Display the values from each column of the current row in the cursor in the TextView
                //displayView.append(("\n" + currentID + " - " +
                //        currentIPAddress + " - " +
                //        currentOrg + " - " +
                //        currentLat + " - " +
                //        currentLng + " - " +
                //        currentCity));
            }



    }


}
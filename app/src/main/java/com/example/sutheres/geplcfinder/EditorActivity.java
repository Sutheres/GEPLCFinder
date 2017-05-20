package com.example.sutheres.geplcfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sutheres.geplcfinder.data.PLCContract.PLCEntry;

public class EditorActivity extends AppCompatActivity {

    private Uri currentPLCUri;
    private EditText ipAddressEditText;
    private EditText orgEditText;
    private EditText ispEditText;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private EditText cityEditText;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ipAddressEditText = (EditText) findViewById(R.id.edit_text_ip_address);
        orgEditText = (EditText) findViewById(R.id.edit_text_organization);
        ispEditText = (EditText) findViewById(R.id.edit_text_isp);
        latitudeEditText = (EditText) findViewById(R.id.edit_text_latitude);
        longitudeEditText = (EditText) findViewById(R.id.edit_text_longitude);
        cityEditText = (EditText) findViewById(R.id.edit_text_city);

        Intent intent = getIntent();
        currentPLCUri = intent.getData();

        if (currentPLCUri != null) {
            this.setTitle("Edit a PLC");

            String[] projection = {
                    PLCEntry._ID,
                    PLCEntry.COLUMN_IP_ADDRESS,
                    PLCEntry.COLUMN_ORGANIZATION,
                    PLCEntry.COLUMN_ISP,
                    PLCEntry.COLUMN_LATITUDE,
                    PLCEntry.COLUMN_LONGITUDE,
                    PLCEntry.COLUMN_CITY
            };

            cursor = getContentResolver().query(currentPLCUri, projection, null, null, null);

            if (cursor.moveToFirst()) {
                String ip = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_IP_ADDRESS));
                String org = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_ORGANIZATION));
                String isp = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_ISP));
                String lat = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_LATITUDE));
                String lng = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_LONGITUDE));
                String city = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_CITY));


                ipAddressEditText.setText(ip);
                orgEditText.setText(org);
                ispEditText.setText(isp);
                latitudeEditText.setText(lat);
                longitudeEditText.setText(lng);
                cityEditText.setText(city);

                cursor.close();
            }


        } else {
            this.setTitle("Add a PLC");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu options in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save_plc:
                savePLC();
                finish();
                return true;
            case R.id.action_delete_plc:
                deletePLC();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePLC() {
        if (currentPLCUri != null) {
            int rowsDeleted = getContentResolver().delete(
                    currentPLCUri,
                    null,
                    null
            );

            if (rowsDeleted != 0) {
                Toast.makeText(this, "PLC Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PLC not deleted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void savePLC() {

        String ipAddressString = ipAddressEditText.getText().toString().trim();
        String orgString = orgEditText.getText().toString().trim();
        String ispString = ispEditText.getText().toString().trim();
        String latString = latitudeEditText.getText().toString().trim();
        String lngString = longitudeEditText.getText().toString().trim();
        String cityString = cityEditText.getText().toString().trim();

        Uri mUri;

        /**
         * If there is no URI passed into EditorActivity and we're in
         * "Add a PLC" mode, check to see if all fields are empty.
         * If all fields are empty exit out the method and finish.
         */
        if (currentPLCUri == null &&
                TextUtils.isEmpty(ipAddressString) && TextUtils.isEmpty(orgString)
                && TextUtils.isEmpty(ispString) && TextUtils.isEmpty(latString)
                && TextUtils.isEmpty(lngString) && TextUtils.isEmpty(cityString)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(PLCEntry.COLUMN_IP_ADDRESS, ipAddressString);
        values.put(PLCEntry.COLUMN_ORGANIZATION, orgString);
        values.put(PLCEntry.COLUMN_ISP, ispString);
        values.put(PLCEntry.COLUMN_LATITUDE, latString);
        values.put(PLCEntry.COLUMN_LONGITUDE, lngString);
        values.put(PLCEntry.COLUMN_CITY, cityString);


        if (currentPLCUri == null) {
            mUri = getContentResolver().insert(PLCEntry.CONTENT_URI, values);
            if (mUri == null) {
                // If the new content URI is null, then there was an error with insertion
                Toast.makeText(this, "Error with saving PLC", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PLC saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

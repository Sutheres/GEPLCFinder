package com.example.sutheres.geplcfinder;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sutheres.geplcfinder.data.PLCContract.PLCEntry;

/**
 * Created by Sutheres on 2/4/2017.
 */

public class PLCCursorAdapter extends CursorAdapter {


    public PLCCursorAdapter(Context context, Cursor c) { super(context, c, 0 );}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate the inflated template
        TextView plcIPAddress = (TextView)view.findViewById(R.id.ip);
        TextView plcOrganization = (TextView)view.findViewById(R.id.organization);

        // Extract properties from cursor
        String ipAddress = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_IP_ADDRESS));
        String organization = cursor.getString(cursor.getColumnIndex(PLCEntry.COLUMN_ORGANIZATION));

        // Populate fields with extracted properties
        plcIPAddress.setText(ipAddress);
        plcOrganization.setText(organization);
    }
}

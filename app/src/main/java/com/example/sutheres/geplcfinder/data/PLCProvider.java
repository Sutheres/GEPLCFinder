package com.example.sutheres.geplcfinder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.sutheres.geplcfinder.data.PLCContract.PLCEntry;

/**
 * Created by Sutheres on 2/4/2017.
 */

public class PLCProvider extends ContentProvider {

    private PLCDbHelper mDbHelper;

    public static final String LOG_TAG = PLCProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the plc table */
    private static final int PLCS = 100;

    /** URI matcher code for the content URI for a single PLC*/
    private static final int PLC_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static inializer. This is run the first time anything is called from this class.
    static {
        sUriMatcher.addURI(PLCEntry.CONTENT_AUTHORITY, PLCEntry.PATH_PLCS, PLCS);
        sUriMatcher.addURI(PLCEntry.CONTENT_AUTHORITY, PLCEntry.PATH_PLCS + "/#", PLC_ID);
    }


    /**
     * Initialize the privder and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new PLCDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Get readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PLCS:
                // For the PLCS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                cursor = db.query(PLCEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PLC_ID:
                // For the PLC_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.sutheres.geplcfinder/plcs/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                //Toast.makeText(getContext(), "PLC ID matched", Toast.LENGTH_SHORT).show();
                selection = PLCEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                 cursor = db.query(PLCEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                    break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }

        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLCS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        //Insert a new PLC into the plc database table with the given ContentValues
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(PLCEntry.TABLE_NAME, null, values);

        // If the ID is -1 then the insertion failed. Log an error and return null.
        if(id == -1) {
            Toast.makeText(getContext(), "Failed to insert row for " + uri, Toast.LENGTH_SHORT).show();
            return null;
        }

        //Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PLCS:
                rowsDeleted = db.delete(PLCEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLC_ID:
                selection = PLCEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(PLCEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PLCS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PLC_ID:
                selection = PLCEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Updae is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowsUpdated = db.update(PLCEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}

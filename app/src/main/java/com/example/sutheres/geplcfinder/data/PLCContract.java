package com.example.sutheres.geplcfinder.data;

/**
 * Created by Sutheres on 2/4/2017.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for PLC finder app
 */
public final class PLCContract {

    /**
     * To prevent someone from accidentally instantiating the contract class,
     * give it an empty constructor
     */
    private PLCContract() {}

    /**
     * Inner class that defines contant values for the PLC database table.
     * Each entry in the table represents a PLC.
     */
    public static final class PLCEntry implements BaseColumns {
        /**
         *  The "Content authority" is a name for the entire content provider, similar to the
         *  relationship between a domain name and its website. A convenient string to use for the
         *  content authority is the package name for the app, which is guaranteed to be unique on
         *  the device.
         */
        public static final String CONTENT_AUTHORITY = "com.example.sutheres.geplcfinder";

        /**
         * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
         * the content provider.
         */
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        /**
         * Possible path (appended to BASE_CONTENT_URI for possible URIs)
         * For instance, content://com.example.sutheres.plcfinder/plcs is a valid path for
         * looking at PLC data. content://com.example.sutheres.plcfinder/staff/ will fail,
         * as the ContentProvider hasnt been given any information on what to do with "staff".
         */
        public static final String PATH_PLCS = "plcs";

        /** The Content Uri to access the plc data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLCS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of PLCs
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLCS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single PLC
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLCS;

        /**
         * Name of the database table for PLCs
         */
        public static final String TABLE_NAME = "plcs";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_IP_ADDRESS = "ip_Address";

        public static final String COLUMN_ORGANIZATION = "organization";

        public static final String COLUMN_ISP = "isp";

        public static final String COLUMN_LATITUDE = "lat";

        public static final String COLUMN_LONGITUDE = "lng";

        public static final String COLUMN_CITY = "city";
    }
}

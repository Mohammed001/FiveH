package com.example.asus.fiveh.my_database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class TaskContract {

    // The authority, which is how your code knows which Content Provider to access
    static final String AUTHORITY = "com.example.asus.fiveh";

    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    static final String PATH_ADS = "ads";
    static final String PATH_FLOWERS = "flowers";

    /* AdTable is an inner class that defines the contents of the task table */
    public static final class AdTable implements BaseColumns {

        // AdTable content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ADS).build();

        // Task table and column names
        static final String TABLE_NAME = "ads";

        // Since AdTable implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_AD_TYPE = "adv_type";
        public static final String COLUMN_FILE_PATH = "file_path";
        public static final String COLUMN_AD_TEXT = "adv_text";
        public static final String COLUMN_AD_ID = "adv_id";
        public static final String COLUMN_AD_PAGE = "adv_page";


        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY + "/" + TABLE_NAME;

    }



    /* AdTable is an inner class that defines the contents of the task table */
    public static final class FlowerTable implements BaseColumns {

        // AdTable content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FLOWERS).build();

        // Task table and column names
        static final String TABLE_NAME = "flowers";

        // Since AdTable implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_PRODUCT_ID = "productId";
        public static final String COLUMN_TEXT = "name_and_description";
        public static final String COLUMN_PHOTO_PATH = "photo";


        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY + "/" + TABLE_NAME;


    }
}

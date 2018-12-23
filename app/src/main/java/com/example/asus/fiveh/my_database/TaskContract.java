package com.example.asus.fiveh.my_database;

import android.net.Uri;
import android.provider.BaseColumns;


class TaskContract {

    // The authority, which is how your code knows which Content Provider to access
    static final String AUTHORITY = "com.example.asus.fiveh";

    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    static final String PATH_TASKS = "tasks";

    /* AdTable is an inner class that defines the contents of the task table */
    static final class AdTable implements BaseColumns {

        // AdTable content URI = base content URI + path
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Task table and column names
        static final String TABLE_NAME = "ads";

        // Since AdTable implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        static final String COLUMN_AD_TYPE = "adv_type";
        static final String COLUMN_FILE_PATH = "file_path";
        static final String COLUMN_AD_TEXT = "adv_text";
        static final String COLUMN_AD_ID = "adv_id";
        static final String COLUMN_AD_PAGE = "adv_page";
    }
}

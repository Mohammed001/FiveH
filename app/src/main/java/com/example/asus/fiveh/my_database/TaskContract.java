package com.example.asus.fiveh.my_database;

import android.net.Uri;
import android.provider.BaseColumns;


class TaskContract {

    /* COMPLETED: Add content provider constants to the Contract
     Clients need to know how to access the task data, and it's your job to provide
     these content URI's for the path to that data:
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the tasks directory
        4) Content URI for data in the AdEntry class
      */

    // The authority, which is how your code knows which Content Provider to access
    static final String AUTHORITY = "com.example.asus.fiveh";

    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    static final String PATH_TASKS = "tasks";


    /* AdEntry is an inner class that defines the contents of the task table */
    static final class AdEntry implements BaseColumns {

        // AdEntry content URI = base content URI + path
        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Task table and column names
        static final String TABLE_NAME = "ads";

        // Since AdEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        static final String COLUMN_AD_TYPE = "adv_type";
        static final String COLUMN_FILE_PATH = "file_path";
        static final String COLUMN_AD_TEXT = "adv_text";
        static final String COLUMN_AD_ID = "adv_id";
        static final String COLUMN_AD_PAGE = "adv_page";

        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is generated automatically

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Complete lesson   |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn guitar     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -

         */

    }
}

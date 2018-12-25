package com.example.asus.fiveh.my_database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.example.asus.fiveh.my_database.TaskContract.AdTable;
import static com.example.asus.fiveh.my_database.TaskContract.FlowerTable;


// COMPLETED: Verify that TaskContentProvider extends from ContentProvider and implements required methods
public class TaskContentProvider extends ContentProvider {

    // COMPLETED: Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int ADS_PATH = 100;
    public static final int ADS_PATH_WITH_ID = 101;
    public static final int FLOWERS_PATH = 102;
    public static final int FLOWER_PATH_WITH_ID = 103;

    // COMPLETED: Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String LOG_TAG = TaskContentProvider.class.getSimpleName();

    // COMPLETED: Define a static buildUriMatcher method that associates URI's with their int match

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_ADS, ADS_PATH);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_ADS + "/#", ADS_PATH_WITH_ID);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_FLOWERS, FLOWERS_PATH);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_FLOWERS + "/#", FLOWER_PATH_WITH_ID);

        return uriMatcher;
    }


    // Member variable for a TaskDbHelper that's initialized in the onCreate() method
    private TaskDbHelper mTaskDbHelper;

    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {
        // COMPLETED: Complete onCreate() and initialize a TaskDbhelper on startup
        // [Hint] Declare the DbHelper as a global variable
        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }

    // COMPLETED: Implement insert to handle requests to insert a single new row of data
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        // COMPLETED: 1. Get access to the task database (to write new data to)
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        // COMPLETED: 2. Write URI matching code to identify the match for the tasks directory
        int match = sUriMatcher.match(uri);

        // COMPLETED: 3. Insert new values into the database
        // COMPLETED: 4. Set the value for the returnedUri and write the default case for unknown URI's

        Uri returnUri; // to be returned
        long id;

        switch (match) {
            case ADS_PATH:
                // Inserting values into tasks table
                id = db.insert(AdTable.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(AdTable.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Default case throws an UnsupportedOperationException
            case FLOWERS_PATH:
                // Inserting values into tasks table
                id = db.insert(FlowerTable.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FlowerTable.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED: 5. Notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADS_PATH:
                return do_bulk_insert_into(AdTable.TABLE_NAME, uri, values, db);
            case FLOWERS_PATH:
                return do_bulk_insert_into(FlowerTable.TABLE_NAME, uri, values, db);
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // COMPLETED: Implement query to handle requests for data by URI
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // COMPLETED: 1. Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        // COMPLETED: 2. Write URI match code
        int match = sUriMatcher.match(uri);

        // COMPLETED: 3. Query for the tasks directory and write a default case

        Cursor retCursor;

        switch (match) {
            // Query for the tasks directory
            case ADS_PATH:
                retCursor = db.query(AdTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FLOWERS_PATH:
                retCursor = db.query(FlowerTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED: 4. Set a notification URI on the Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    // COMPLETED: Implement delete to delete a single row of data
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        // COMPLETED: 1. Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        // COMPLETED: 2. Write the code to delete a single row of data
        //[Hint] Use selections to delete an item by its row ID

        // Keep track of the number of deleted tasks
        int tasksDeleted; // init as 0
        String id;

        switch (match) {
            case ADS_PATH:
                tasksDeleted = db.delete(AdTable.TABLE_NAME, null, null);
                break;
            case FLOWERS_PATH:
                tasksDeleted = db.delete(FlowerTable.TABLE_NAME, null, null);
                break;
            // Handle the single item case, recognized by the ID included in the URI path
            case ADS_PATH_WITH_ID:
                // Get the task ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(AdTable.TABLE_NAME, "_id=?", new String[]{id});
                break;
            case FLOWER_PATH_WITH_ID:
                // Get the task ID from the URI path
                id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(FlowerTable.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED: 3. Notify the resolver of a change
        // And don't forget to return the number of items deleted!

        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    // Update won't be used in the final ToDoList app but is implemented here for completeness
    // This updates a single item (by it's ID) in the tasks directory
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // Access the database
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        // match code
        int match = sUriMatcher.match(uri);

        //Keep track of if an update occurs
        int tasksUpdated;
        String id;
        switch (match) {
            case ADS_PATH_WITH_ID:
                //update a single task by getting the id
                id = uri.getPathSegments().get(1);
                //using selections
                tasksUpdated = db.update(AdTable.TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            case FLOWER_PATH_WITH_ID:
                //update a single task by getting the id
                id = uri.getPathSegments().get(1);
                //using selections
                tasksUpdated = db.update(FlowerTable.TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return tasksUpdated;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ADS_PATH: {
                return AdTable.CONTENT_DIR_TYPE;
            }
            case FLOWERS_PATH: {
                return FlowerTable.CONTENT_DIR_TYPE;
            }
            case ADS_PATH_WITH_ID: {
                return AdTable.CONTENT_ITEM_TYPE;
            }
            case FLOWER_PATH_WITH_ID: {
                return FlowerTable.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

// ________________________ ((  helper methods )) ________________________

    private int do_bulk_insert_into(String tableName, @NonNull Uri uri, @NonNull ContentValues[] values, SQLiteDatabase db) {
        // allows for multiple transactions
        db.beginTransaction();
        // keep track of successful inserts
        int numInserted = 0;
        try {
            for (ContentValues value : values) {
                if (value == null) {
                    throw new IllegalArgumentException("Cannot have null content values");
                }
                long _id = -1;
                try {
                    _id = db.insertOrThrow(tableName,
                            null, value);
                } catch (SQLiteConstraintException e) {
                    Log.w(LOG_TAG, "Attempting to insert " +
                            value.getAsString(
                                    AdTable.COLUMN_AD_ID)
                            + " but value is already in database.");
                }
                if (_id != -1) {
                    numInserted++;
                }
            }
            if (numInserted > 0) {
                // If no errors, declare a successful transaction.
                // database will not populate if this is not called
                db.setTransactionSuccessful();
            }
        } finally {
            // all transactions occur at once
            db.endTransaction();
        }
        if (numInserted > 0) {
            // if there was successful insertion, notify the content resolver that there
            // was a change
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;
    }

}

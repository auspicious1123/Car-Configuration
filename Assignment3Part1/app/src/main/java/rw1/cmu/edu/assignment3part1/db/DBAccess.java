package rw1.cmu.edu.assignment3part1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rui on 3/31/16.
 */
public class DBAccess {
    public static final String TABLE_NAME = "Scores";

    public static final String ID = "_id";
    public static final String Q1 = "q1";
    public static final String Q2 = "q2";
    public static final String Q3 = "q3";
    public static final String Q4 = "q4";
    public static final String Q5 = "q5";

    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper

    // public constructor for DatabaseConnector
    public DBAccess(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, TABLE_NAME, null, 1);
    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the mortgage table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named "Mortgage"
            String createQuery = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " TEXT PRIMARY KEY,"
                    + Q1 + " TEXT,"
                    + Q2 + " TEXT,"
                    + Q3 + " TEXT,"
                    + Q4 + " TEXT,"
                    + Q5 + " TEXT)";

            db.execSQL(createQuery); // execute the query
            Log.i("DB", "Create Table:" + createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        }
    }

    public void insertScore(String id, double s1, double s2, double s3, double s4, double s5){
        ContentValues newScore = new ContentValues();
        newScore.put(ID, id);
        newScore.put(Q1, s1);
        newScore.put(Q2, s2);
        newScore.put(Q3, s3);
        newScore.put(Q4, s4);
        newScore.put(Q5, s5);

        open();
        // write entry in database
        try {
            database.insert(TABLE_NAME, null, newScore);
        } catch (Exception e) {
            Log.e(null,"Insert Score Error");
        }
        close();
    }

    public Cursor getAllScore(){
        return database.query(TABLE_NAME, new String[]{ID, Q1, Q2, Q3, Q4, Q5},
                null, null, null, null, null);
    }

    // delete all scores in the database
    public void deleteAll() {
        open();
        database.execSQL("DELETE FROM " + TABLE_NAME + ";");
        close();
    }


}

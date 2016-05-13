package rw1.cmu.edu.mortgagecalculator.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rui on 3/23/16.
 */

public class DBAccess {
    public static final String TABLE_NAME = "MortgageCalculator";

    // set database each column name
    public static final String idNum = "_id";
    public static final String PURCHASED_PRICE = "PurchasedPrice";
//    public static final String DOWN_PAYMENT = "DownPayment";   // down payment, show in mobile but not required.
    public static final String MORTGAGE_TERM = "MortgageTerm";
    public static final String INTEREST_RATE = "InterestRate";
    public static final String TOTAL_MONTHLY_PAYMENT = "TotalMonthlyPayment";
    public static final String TOTAL_PAYMENT = "TotalPayment";
    public static final String FIRST_PAYMENT_DATE = "FirstPaymentDate";
    public static final String PAY_OFF_DATE = "PayOffDate";

    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase database;

    // constructor
    public DBAccess(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context, TABLE_NAME, null, 1);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        // helper constructor
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // creates the mortgage table
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String createQuery = "CREATE TABLE " + TABLE_NAME + " ("
                    + idNum + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PURCHASED_PRICE + " TEXT,"
                    + MORTGAGE_TERM + " TEXT,"
                    + INTEREST_RATE + " TEXT,"
                    + TOTAL_MONTHLY_PAYMENT + " TEXT,"
                    + TOTAL_PAYMENT + " TEXT,"
                    + FIRST_PAYMENT_DATE + " TEXT,"
                    + PAY_OFF_DATE + " TEXT)";

            db.execSQL(createQuery); // execute the query
            Log.i("DB","Create Table:" + createQuery);
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
        }
    }

    // get a Cursor the given id
    public Cursor getMortgage() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    // inserts a new mortgage in the database
    public void insertMortgage(String pp, String mt,
                               String ir, double tmp,double tp, String ftp,String pod) {

        ContentValues mortgage = new ContentValues();
        mortgage.put(PURCHASED_PRICE, pp);
        mortgage.put(MORTGAGE_TERM, mt);
        mortgage.put(INTEREST_RATE, ir);
        mortgage.put(TOTAL_MONTHLY_PAYMENT, tmp);
        mortgage.put(TOTAL_PAYMENT, tp);
        mortgage.put(FIRST_PAYMENT_DATE, ftp);
        mortgage.put(PAY_OFF_DATE, pod);
        open();

        database.insert(TABLE_NAME, null, mortgage);
        close();
    }
}


package rw1.cmu.edu.mortgagecalculator.ui;

/**
 * Created by Rui on 3/23/16.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import rw1.cmu.edu.mortgagecalculator.R;
import rw1.cmu.edu.mortgagecalculator.model.MortgageCalculator;
import rw1.cmu.edu.mortgagecalculator.util.DBAccess;

public class MainActivity extends AppCompatActivity {

    // EditText for Mortgage name, price, term and interest rate
    private EditText editText_price, editText_Termyear,
            editText_rate;
    private Spinner yearSpinner, monthSpinner;
    private int monthPosition;
    private String yearNumber;
    private DBAccess dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 逻辑和视图分离， 布局文件中写界面，在activity里面引用进来
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    // 引用布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbAccess = new DBAccess(MainActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbAccess.open();
                Cursor c = dbAccess.getMortgage();

                StringBuilder sb = new StringBuilder();
                sb.append("Price| Term| Rate| Month pay| Total pay\n");

                int priceIndex = c.getColumnIndex(DBAccess.PURCHASED_PRICE);
                int termIndex = c.getColumnIndex(DBAccess.MORTGAGE_TERM);
                int rateIndex = c.getColumnIndex(DBAccess.INTEREST_RATE);
                int monthPayIndex = c.getColumnIndex(DBAccess.TOTAL_MONTHLY_PAYMENT);
                int totalPayIndex = c.getColumnIndex(DBAccess.TOTAL_PAYMENT);
                c.moveToFirst();

                while (!c.isLast()){

                    sb.append(String.format("%s  |    %s   |    %s  |   %.1f  |  %.1f \n",
                            c.getString(priceIndex), c.getString(termIndex), c.getString(rateIndex) ,
                            Double.valueOf(c.getString(monthPayIndex)),
                            Double.valueOf(c.getString(totalPayIndex))));
                    c.moveToNext();
                }

                sb.append(String.format("%s  |    %s  |    %s |    %.1f  |   %.1f \n",
                        c.getString(priceIndex), c.getString(termIndex), c.getString(rateIndex),
                        Double.valueOf(c.getString(monthPayIndex)),
                        Double.valueOf(c.getString(totalPayIndex))));

                String message = sb.toString();
                dbAccess.close();
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Data in Database").setMessage(message).setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                dialog.show();

            }
        });

        editText_price = (EditText) findViewById(R.id.editText_price);
        editText_Termyear = (EditText) findViewById(R.id.editText_Termyear);
        editText_rate = (EditText) findViewById(R.id.editText_rate);
        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(calculateBtnClicked);

        monthSpinner = (Spinner) findViewById(R.id.month);
        yearSpinner = (Spinner) findViewById(R.id.year);

        monthPosition = 1;
        yearNumber = "2016";

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] year = getResources().getStringArray(R.array.year);
                yearNumber = year[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthPosition = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Dialog dialog = new AlertDialog.Builder(this).setTitle("MortgageCalculator").setMessage("Version 0.1 by Rui Wang").setPositiveButton("Okay", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                }
            }).create();
            // show dialog
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // responds to event generated when user clicks the Save Button
    View.OnClickListener calculateBtnClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText_rate.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editText_price.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editText_Termyear.getWindowToken(), 0);

            String price =  editText_price.getText().toString();
            String term = editText_Termyear.getText().toString();
            String rate = editText_rate.getText().toString();

            MortgageCalculator mc = new MortgageCalculator(price, term, rate, Integer.valueOf(yearNumber), monthPosition);

            if (mc.validInput())
            {
                mc.calculate();
                String message = String.format("Month payment:  %.1f\n Total payment:   %.1f\nPayoff day: %s" ,
                        mc.getMonthlyPayment(),
                        mc.getTotalPayment(),
                        mc.getPayOffDate());
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Result:").setMessage(message).setPositiveButton("Okay", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                dialog.show();

                addToDataBase(mc);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(MainActivity.this, "Data saved into Database", duration);
                toast.show();

            }
            else
            {
                // set dialog
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Error").setMessage("Please input first").setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                dialog.show();
            }
        }
    };

    // add data into database
    private void addToDataBase(MortgageCalculator mc) {
        dbAccess.insertMortgage(mc.priceString,
                mc.termString, mc.rateString, mc.getMonthlyPayment(), mc.getTotalPayment(),
                mc.getFirstPaymentDate(), mc.getPayOffDate());
    }
}


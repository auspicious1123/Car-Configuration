package rw1.cmu.edu.assignment3part1.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import rw1.cmu.edu.assignment3part1.R;
import rw1.cmu.edu.assignment3part1.db.DBAccess;
import rw1.cmu.edu.assignment3part1.model.CalQuizScore;
import rw1.cmu.edu.assignment3part1.model.Student;

public class MainActivity extends AppCompatActivity {

    //    private DBConnector dbConnector;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Button addStu2DB;
    private Button loadDB;
    private Button calQuiz;
    private EditText infoEditText[] = new EditText[6];
    private DBAccess dbAccess = new DBAccess(MainActivity.this);
    private int size = 40;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbAccess.deleteAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        addStu2DB = (Button)findViewById(R.id.addStu2DB);
        addStu2DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoEditText[0] = (EditText)findViewById(R.id.et_stu_id);
                infoEditText[1] = (EditText)findViewById(R.id.et_scores_q1);
                infoEditText[2] = (EditText)findViewById(R.id.et_scores_q2);
                infoEditText[3] = (EditText)findViewById(R.id.et_scores_q3);
                infoEditText[4] = (EditText)findViewById(R.id.et_scores_q4);
                infoEditText[5] = (EditText)findViewById(R.id.et_scores_q5);
                int id = Integer.parseInt(infoEditText[0].getText().toString());
                double scores[] = new double[5];
                for (int i = 0; i < 5; i++) {
                    scores[i] = Double.parseDouble(infoEditText[i+1].getText().toString());
                }
                Student student = new Student(id, scores);
                if(studentList.size() < size) {
                    studentList.add(student);
                    dbAccess.insertScore(id + "", scores[0], scores[1], scores[2], scores[3], scores[4]);

                    // show the add result to user.
                    Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Save data into database").setMessage(
                            "StuID   Q1   Q2   Q3   Q4   Q5 \n" + student.toString()).setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();
                } else {
                    // show the add result to user.
                    Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Student number bigger than maximum").setMessage("Over number").setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();
                }


            }
        });

        loadDB = (Button)findViewById(R.id.loadDB);
        loadDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAccess.open();
                Cursor c = dbAccess.getAllScore();

                StringBuilder sb = new StringBuilder();
                sb.append("Stu ID | Q1 | Q2 | Q3 | Q4 | Q5 \n");

                // get each columnIndex
                int stuID = c.getColumnIndex(DBAccess.ID);
                int q1 = c.getColumnIndex(DBAccess.Q1);
                int q2 = c.getColumnIndex(DBAccess.Q2);
                int q3 = c.getColumnIndex(DBAccess.Q3);
                int q4 = c.getColumnIndex(DBAccess.Q4);
                int q5 = c.getColumnIndex(DBAccess.Q5);
                c.moveToFirst();

                while (!c.isLast()){
                    sb.append(String.format("%d   %.1f   %.1f   %.1f   %.1f  %.1f\n",
                            c.getInt(stuID), c.getDouble(q1), c.getDouble(q2), c.getDouble(q3),
                            c.getDouble(q4), c.getDouble(q5)));
                    c.moveToNext();
                }

                sb.append(String.format("%d   %.1f   %.1f   %.1f   %.1f  %.1f\n",
                        c.getInt(stuID), c.getDouble(q1), c.getDouble(q2), c.getDouble(q3),
                        c.getDouble(q4), c.getDouble(q5)));


                String message = sb.toString();
                dbAccess.close();
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Student quiz scores.").setMessage(message).setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });

        calQuiz = (Button)findViewById(R.id.calQuiz);
        calQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalQuizScore calQuizScore = new CalQuizScore(studentList);
                calQuizScore.calculate();

                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Student quiz scores.").setMessage(calQuizScore.resulString()).setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

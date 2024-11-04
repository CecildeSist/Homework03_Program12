package com.example.homework03_program12;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FilterStudents extends AppCompatActivity {

    EditText filter_uname, filter_fname, filter_lname, filter_gpaLow, filter_gpaHigh;
    Button filter_filter, filter_back;
    ListView filter_listView;
    Spinner filter_j_spinner;

    ArrayList<String> foundStudents;
    ArrayAdapter adapter;
    static ArrayList<String> filterArrayList = new ArrayList<>();
    FilterBaseAdapter filterAdapter;
    static ArrayList<Student> filterStudentList = new ArrayList<>();
    TextView filter_error;
    ArrayAdapter<String> filterSpinnerPopulace;

    DatabaseHelper dbHelper;

    String filterStudentMajor = Student.studentMajor.getStudentMajorAt(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_students);
        filter_uname = findViewById(R.id.et_filter_u);
        filter_fname = findViewById(R.id.et_filter_f);
        filter_lname = findViewById(R.id.et_filter_l);
        filter_j_spinner = findViewById(R.id.spn_filter);
        filter_gpaLow = findViewById(R.id.et_filter_gpaLow);
        filter_gpaHigh = findViewById(R.id.et_filter_gpaUpper);
        filter_filter = findViewById(R.id.btn_filter_filter);
        filter_back = findViewById(R.id.btn_filter_back);

        filter_listView = findViewById(R.id.lv_filter);

        filter_listView.setAdapter(filterAdapter);

        filter_error = findViewById(R.id.txt_filter_error);
        filter_error.setVisibility(View.INVISIBLE);

        dbHelper = new DatabaseHelper(this);
        //Populate the spinner
        filterSpinnerPopulace = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dbHelper.populateSpinner());
        filter_j_spinner.setAdapter(filterSpinnerPopulace);

        foundStudents = new ArrayList<String>();
        //filterArrayList = new ArrayList<>(); (REMOVED ACCIDENTAL SECOND DECLARATION)

        filterSpinnerListener();
        filterFilterListener();
        filterBackListener();
        filterFillList(filterArrayList);
    }

    private void filterSpinnerListener() {
        filter_j_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterStudentMajor = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void filterFilterListener() {
        filter_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Code called?", "YES");
                String uname = "";
                String fname = "";
                String lname = "";
                //Major is grabbed with other code
                //0 is lowest natural GPA and 6 is highest, so people shouldn't be searching for GPAs outside of this range anyway
                Float gpaLow = 0F;
                Float gpaHigh = 6F;

                //Grab data from edit text (DONE)
                //"!" means "is not"
                if (!filter_uname.getText().toString().isEmpty()){
                    uname = filter_uname.getText().toString();
                }
                if (!filter_fname.getText().toString().isEmpty()) {
                    fname = filter_fname.getText().toString();
                }
                if (!filter_lname.getText().toString().isEmpty()) {
                    lname = filter_lname.getText().toString();
                }
                if (!filter_gpaLow.getText().toString().isEmpty()) {
                    gpaLow = Float.parseFloat(filter_gpaLow.getText().toString());
                }
                if (!filter_gpaHigh.getText().toString().isEmpty()) {
                    gpaHigh = Float.parseFloat(filter_gpaHigh.getText().toString());
                }
                Log.d("Strings assigned?", "YES");

                if (gpaLow < 0 || gpaHigh > 6 || gpaLow > 6 || gpaHigh < 0) {
                    filter_error.setVisibility(View.VISIBLE);
                }
                else {
                    filter_error.setVisibility(View.INVISIBLE);
                    filterArrayList = dbHelper.findStudentGivenCritera(uname, fname, lname, filterStudentMajor, gpaLow, gpaHigh);

                    //dbHelper.sendFilteredStudents(filterArrayList);

                    filterFillList(filterArrayList);
                }
            }
        });
    }

    private void filterBackListener() {
        filter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterToMain = new Intent(FilterStudents.this, MainActivity.class);
                startActivity(filterToMain);
            }
        });
    }

    private void filterFillList(ArrayList<String> sentStrings) {
        filterStudentList = dbHelper.sendFilteredStudents(sentStrings);

        filterAdapter = new FilterBaseAdapter(this, filterStudentList);
        filter_listView.setAdapter(filterAdapter);

    }
}
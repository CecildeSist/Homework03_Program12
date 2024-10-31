package com.example.homework03_program12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FilterStudents extends AppCompatActivity {

    EditText filter_uname, filter_fname, filter_lname, filter_major, filter_gpaLow, filter_gpaHigh;
    Button filter_filter, filter_back;
    ListView filter_listView;

    ArrayList<String> foundStudents;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_students);
        filter_uname = findViewById(R.id.et_filter_u);
        filter_fname = findViewById(R.id.et_filter_f);
        filter_lname = findViewById(R.id.et_filter_l);
        filter_major = findViewById(R.id.et_filter_major);
        filter_gpaLow = findViewById(R.id.et_filter_gpaLow);
        filter_gpaHigh = findViewById(R.id.et_filter_gpaUpper);
        filter_filter = findViewById(R.id.btn_filter_filter);
        filter_back = findViewById(R.id.btn_filter_back);

        dbHelper = new DatabaseHelper(this);
        foundStudents = new ArrayList<String>();

        filterFilterListener();
        filterBackListener();
    }

    private void filterFilterListener() {
        filter_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = "";
                String fname = "";
                String lname = "";
                String major = "";
                Float gpaLow, gpaHigh;

                //Grab data from edit text (DONE)
                uname = filter_uname.getText().toString();
                fname = filter_fname.getText().toString();
                lname = filter_lname.getText().toString();
                major = filter_major.getText().toString();
                gpaLow = Float.parseFloat(filter_gpaLow.getText().toString());
                gpaHigh = Float.parseFloat(filter_gpaHigh.getText().toString());

                foundStudents = dbHelper.findStudentGivenCritera(uname, fname, lname, major, gpaLow, gpaHigh);

                for (int i = 0; i < foundStudents.size(); i++) {
                    Log.d("uname: ",  foundStudents.get(i));
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
}
package com.example.homework03_program12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class UpdateStudent extends AppCompatActivity {

    //NOTE TO SELF also doubles as detail-viewing page SAY THAT IN YOUR VIDEO

    EditText et_update_fname, et_update_lname, et_update_email, et_update_age, et_update_gpa;
    Spinner spn_update_major;
    Button btn_update_student, btn_update_back;
    DatabaseHelper dbHelper;
    TextView updateError;
    TextView titleAndUname;

    ArrayAdapter<String> majorsOnUpdatePage;

    String updateStudentMajorName = Student.studentMajor.getStudentMajorAt(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_student);

        et_update_fname = findViewById(R.id.et_v_update_fname);
        et_update_lname = findViewById(R.id.et_v_update_lname);
        et_update_email = findViewById(R.id.et_v_update_email);
        et_update_age = findViewById(R.id.et_v_update_age);
        et_update_gpa = findViewById(R.id.et_v_update_GPA);
        spn_update_major = findViewById(R.id.spn_v_update);
        btn_update_student = findViewById(R.id.btn_update);
        btn_update_back = findViewById(R.id.btn_update_back);
        titleAndUname = findViewById(R.id.txt_update_titleAndU);

        dbHelper = new DatabaseHelper(this);

        updateError = findViewById(R.id.txt_update_error);
        updateError.setText("Error: please fill out all boxes");

        //Next step: populate spinner (NOT TESTED)
        majorsOnUpdatePage = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dbHelper.populateSpinner());
        spn_update_major.setAdapter(majorsOnUpdatePage);

        Intent cameFrom = getIntent();
        if (cameFrom.getSerializableExtra("studentToUpdate") != null) {
            Student stuToUpdate = (Student) cameFrom.getSerializableExtra("studentToUpdate");
            et_update_fname.setText(stuToUpdate.getfName());
            et_update_lname.setText(stuToUpdate.getlName());
            et_update_email.setText(stuToUpdate.geteMail());
            et_update_age.setText(stuToUpdate.getAge().toString());
            et_update_gpa.setText(stuToUpdate.getGPA().toString());
            updateError.setText("Current Major: " + stuToUpdate.getMajor().toString());
            updateError.setVisibility(View.VISIBLE);
            titleAndUname.setText("Update Student: " + stuToUpdate.getuName().toString());
        }

        updateBackListener();
        updateSpinnerListener();
        updateStudentListener();
    }

    private void updateBackListener() {
        btn_update_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_updateBack = new Intent(UpdateStudent.this, MainActivity.class);
                startActivity(intent_updateBack);
            }
        });
    }

    private void updateSpinnerListener() {
        spn_update_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateStudentMajorName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateStudentListener() {
        btn_update_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameFrom = getIntent();
                String newF, newL, newE;
                Integer newAge;
                Float newGPA;

                //Step 1: retrieve new values (NOTE TO SELF must test)

                newF = et_update_fname.getText().toString();
                newL = et_update_lname.getText().toString();
                newE = et_update_email.getText().toString();
                newAge = Integer.parseInt(et_update_age.getText().toString());
                newGPA = Float.parseFloat(et_update_gpa.getText().toString());
                //NOTE TO SELF new major is declared above in the updateSpinnerListener function, stored under a string named updateStudentMajorName
                Log.d("new values created?", "YES");

                if (cameFrom.getSerializableExtra("studentToUpdate") != null) {
                    Log.d("if statement triggered?", "YES");
                    Student stuToUpdate = (Student) cameFrom.getSerializableExtra("studentToUpdate");
                    if (et_update_fname.getText().toString().isEmpty() || et_update_lname.getText().toString().isEmpty() || et_update_email.getText().toString().isEmpty() || et_update_age.getText().toString().isEmpty() || et_update_gpa.getText().toString().isEmpty()) {
                        updateError.setText("Error: please fill out all boxes");
                        updateError.setVisibility(View.VISIBLE);
                    } else if (newGPA < 0 || newGPA > 6) {
                        updateError.setText("Error: invalid GPA value (must be between 0 and 6)");
                        updateError.setVisibility(View.VISIBLE);
                    }
                    else {
                        Log.d("if else chain returns second else?", "YES");
                        updateError.setText("Current Major: ");
                        updateError.setVisibility(View.INVISIBLE);
                        titleAndUname.setText("Update Student:");


                        //Step 2: replace student in arraylist with new student (NOTE TO SELF must test)

                        String oldStudentUsername = stuToUpdate.getuName();
                        Student updatedStudent = new Student();

                        updatedStudent.setuName(oldStudentUsername);
                        updatedStudent.setfName(newF);
                        updatedStudent.setlName(newL);
                        updatedStudent.seteMail(newE);
                        updatedStudent.setAge(newAge);
                        updatedStudent.setGPA(newGPA);
                        updatedStudent.setMajor(updateStudentMajorName);

                        Log.d("indexNumber", MainActivity.listOfStudents.indexOf(stuToUpdate) + 1 + "");

                        MainActivity.listOfStudents.set(MainActivity.listOfStudents.indexOf(stuToUpdate) + 1, updatedStudent);
                        Log.d("Update statement run?", "YES (note: not guaranteed to have worked");

                        //Step 3: replace student in database. NOTE TO SELF must pass Student stuToUpdate, Strings (newF, newL, newE), Integer newAge,
                        //Float newGPA, and String updateStudentMajorName (NOTE TO SELF must test)
                        dbHelper.replaceStudentTakeThree(stuToUpdate, newF, newL, newE, newAge, newGPA, updateStudentMajorName);
                        Log.d("Update successful?", "YES");

                        Intent goFromUpdateToMain = new Intent(UpdateStudent.this, MainActivity.class);
                        startActivity(goFromUpdateToMain);
                    }
                }
            }
        });
    }
}
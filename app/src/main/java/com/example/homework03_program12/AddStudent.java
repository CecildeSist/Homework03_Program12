package com.example.homework03_program12;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class AddStudent extends AppCompatActivity {

    //Create used GUI elements
    EditText et_j_student_fName;
    EditText et_j_student_lName;
    EditText et_j_student_uName;

    TextView txt_j_student_errorUName;

    EditText et_j_student_eMail;
    EditText et_j_student_age;
    EditText et_j_student_GPA;

    Spinner spn_j_student_majors;

    Button btn_j_student_addMajor;
    Button btn_j_student_add;
    Button btn_j_student_back;

    ArrayAdapter<String> addStudentAdapter;
    String majorName = Student.studentMajor.getStudentMajorAt(0);

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        //Connect GUI elements
        et_j_student_fName = findViewById(R.id.et_v_student_fName);
        et_j_student_lName = findViewById(R.id.et_v_student_lName);
        et_j_student_uName = findViewById(R.id.et_v_student_uName);

        txt_j_student_errorUName = findViewById(R.id.txt_v_student_errorUName);
        txt_j_student_errorUName.setText("Error: Username already in use");
        //Set this to invisible by default
        txt_j_student_errorUName.setVisibility(View.INVISIBLE);

        //Connect rest of GUI elements
        et_j_student_eMail = findViewById(R.id.et_v_student_eMail);
        et_j_student_age = findViewById(R.id.et_v_student_age);
        et_j_student_GPA = findViewById(R.id.et_v_student_GPA);

        spn_j_student_majors = findViewById(R.id.spn_v_student_majors);

        btn_j_student_addMajor = findViewById(R.id.btn_v_student_addMajor);
        btn_j_student_add = findViewById(R.id.btn_v_student_add);
        btn_j_student_back = findViewById(R.id.btn_v_student_back);

        //Next step: populate spinner (NOT DONE)
        addStudentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Student.studentMajor.getAllStudentMajors());
        spn_j_student_majors.setAdapter(addStudentAdapter);

        dbHelper = new DatabaseHelper(this);

        Intent cameFrom = getIntent();
        Bundle majorPassedToMe = cameFrom.getExtras();
        if (majorPassedToMe != null) {
            String newMajor = (String) cameFrom.getSerializableExtra("majorPassed");
            addStudentAdapter.notifyDataSetChanged();
        }

        studentMajorButtonListener();
        addStudentButton();
        studentBackListener();
        addStudentSpinnerListener();
    }

    private void studentMajorButtonListener() {
        btn_j_student_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentMajor = new Intent(AddStudent.this, AddMajor.class);
                startActivity(studentMajor);
            }
        });
    }

    private void addStudentSpinnerListener() {
        spn_j_student_majors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                majorName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addStudentButton() {
        btn_j_student_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentMain = new Intent(AddStudent.this, MainActivity.class);

                //Step 1: declare objects added to student's data (DONE)
                String uname, fname, lname, email;
                int age;
                Float gpa;

                uname = et_j_student_uName.getText().toString();
                fname = et_j_student_fName.getText().toString();
                lname = et_j_student_lName.getText().toString();
                email = et_j_student_eMail.getText().toString();
                age = Integer.parseInt(et_j_student_age.getText().toString());
                gpa = Float.parseFloat(et_j_student_GPA.getText().toString());

                //Step 2: see if any edittext empty or if username already in the student table (DONE)
                if (uname.isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (fname.isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (lname.isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (email.isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (et_j_student_age.getText().toString().isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (et_j_student_GPA.getText().toString().isEmpty()) {
                    txt_j_student_errorUName.setText("Error: please fill out all boxes");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else if (dbHelper.usernameExists(uname)) {
                    txt_j_student_errorUName.setText("Error: Username already in use");
                    txt_j_student_errorUName.setVisibility(View.VISIBLE);
                }
                else {
                    //Step 3: create student object (DONE)
                    Student newStudent = new Student();
                    newStudent.setuName(uname);
                    newStudent.setfName(fname);
                    newStudent.setlName(lname);
                    newStudent.seteMail(email);
                    newStudent.setAge(age);
                    newStudent.setGPA(gpa);
                    newStudent.setMajor(majorName);

                    //Step 4: pass newStudent to main activity (DONE)
                    studentMain.putExtra("studentData", newStudent);

                    //Step 5: add newStudent to table of students (DONE)
                    dbHelper.addNewStudent(uname, fname, lname, email, age, gpa, majorName);

                    startActivity(studentMain);
                }
            }
        });
    }

    private void studentBackListener() {
        btn_j_student_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentMain = new Intent(AddStudent.this, MainActivity.class);
                startActivity(studentMain);
            }
        });
    }
}
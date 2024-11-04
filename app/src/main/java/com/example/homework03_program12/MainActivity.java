//===
//Author: Cecil
//Date: October 27th, 2024
//Description: User may add a student to the array list. Doing so also adds the student to a table in a database. User may add a major to an array list thereof. Doing so adds it to a table in a
//             database. User may view a student's information alone by clicking on their entry in the array list. User may delete a student from the student table by long-clicking their entry in the
//              array list.
//===

//I tried to take on too much at once. Hopefully starting from the ground up will help me keep
//things clear in my head.
package com.example.homework03_program12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Creating used GUI elements
    //Creating interacted GUI elements
    Button btn_j_main_addStudent;
    Button btn_j_main_find;
    ListView lv_j_main_students;

    //Other used objects
    DatabaseHelper dbHelper;
    static ArrayList<Student> listOfStudents = new ArrayList<>();
    StudentListAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Connecting interacted GUI elements
        btn_j_main_addStudent = findViewById(R.id.btn_v_main_addStudent);
        btn_j_main_find = findViewById(R.id.btn_v_main_find);
        lv_j_main_students = findViewById(R.id.lv_v_main_students);

        //Next step: add first three functions back to DatabaseHelper (DONE)
        dbHelper = new DatabaseHelper(this);

        //Next step: add next six functions back to DatabaseHelper (DONE)
        dbHelper.initAllTables();

        //For testing (DONE)
        checkAllTableCounts();

        Intent cameFrom = getIntent();

        if (cameFrom.getSerializableExtra("studentData") != null) {
            //If something was passed from AddStudent, add it to the list of students
            Student studentData = (Student) cameFrom.getSerializableExtra("studentData");
            listOfStudents.add(studentData);
            Log.d("INFO FROM ADD STUDENT", listOfStudents.get(listOfStudents.size() - 1).getuName());
        }

        //button listeners (DONE)
        mainAddClick();
        mainFindClick();

        //fill list view (DONE)
        fillListView();

        listViewUpdateStudent();

        Log.d("size of student array list:", listOfStudents.size() + "");
    }

    private void checkAllTableCounts() {
        Log.d("MAJORS COUNT: ", dbHelper.countRecordsFromTable(dbHelper.getMajors_table_name()) + "");
        Log.d("STUDENTS COUNT: ", dbHelper.countRecordsFromTable(dbHelper.getStudents_table_name()) + "");
    }

    private void mainAddClick(){
        btn_j_main_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddStudent.class));
            }
        });
    }

    private void mainFindClick(){
        btn_j_main_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterStudents.class));
            }
        });
    }

    private void addDummyDataToArrayList() {
        Student newStudent = new Student("CdeSist", "Cecil", "deSist", "CdeSist@tumblr.com", 23, 2.8F, Student.studentMajor.getStudentMajorAt(0));
        listOfStudents.add(newStudent);
        newStudent = new Student("STired", "Sally", "Tired", "STired@yahoo.com", 25, 3.0F, Student.studentMajor.getStudentMajorAt(1));
        listOfStudents.add(newStudent);
        newStudent = new Student("QAsque", "Quentin", "Asque", "QAsque@yahoo.com", 27, 1.3F, Student.studentMajor.getStudentMajorAt(1));
        listOfStudents.add(newStudent);
        newStudent = new Student("FdeSist", "Fanny", "deSist", "FdeSist@yahoo.com", 26, 3.1F, Student.studentMajor.getStudentMajorAt(0));
        listOfStudents.add(newStudent);
        newStudent = new Student("STop", "Samuel", "Top", "STop@yahoo.com", 22, 4.0F, Student.studentMajor.getStudentMajorAt(0));
        listOfStudents.add(newStudent);
        newStudent = new Student("YMom", "Yvonne", "Mom", "YMom@yahoo.com", 29, 3.7F, Student.studentMajor.getStudentMajorAt(2));
        listOfStudents.add(newStudent);
        newStudent = new Student("BOlogy", "Beth", "Ology", "BOlogy@yahoo.com", 29, 3.7F, Student.studentMajor.getStudentMajorAt(3));
        listOfStudents.add(newStudent);
        newStudent = new Student("GPhic", "Ginny", "Phic", "GPhic@yahoo.com", 24, 1.5F, Student.studentMajor.getStudentMajorAt(4));
        listOfStudents.add(newStudent);
        newStudent = new Student("EGlish", "Ebony", "Glish", "EGlish@yahoo.com", 28, 2.0F, Student.studentMajor.getStudentMajorAt(5));
        listOfStudents.add(newStudent);
    }

    public void fillListView() {
        mainAdapter = new StudentListAdapter(this, dbHelper.mainActivityStudents());
        lv_j_main_students.setAdapter(mainAdapter);
    }

    public void listViewUpdateStudent() {
        lv_j_main_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Successfully clicked?", "YES");
                //Pass all contents of listOfStudents to UpdateStudent
                Intent mainToUpdate = new Intent(MainActivity.this, UpdateStudent.class);
                //Send clicked Student to UpdateStudent
                Student toUpdate = (Student) adapterView.getItemAtPosition(i);
                mainToUpdate.putExtra("studentToUpdate", toUpdate);
                mainToUpdate.putExtra("key", listOfStudents);
                startActivity(mainToUpdate);
            }
        });
    }

    public void listViewDeleteStudent() {
        lv_j_main_students.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
    }
}
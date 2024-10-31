package com.example.homework03_program12;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String database_name = "College.db";
    public static final String majors_table_name = "Majors";
    public static final String students_table_name = "Students";

    public DatabaseHelper(Context c) {
        super(c, database_name, null, 13);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + majors_table_name + " (majorID integer primary key autoincrement not null, majorName varchar(50), majorPrefix varchar(50));");
        db.execSQL("CREATE TABLE " + students_table_name + " (username varchar(50) primary key not null, fname varchar(50), lname varchar(50), email varchar(50), age integer, GPA float, major varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + majors_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + students_table_name + ";");
        onCreate(db);
    }

    public String getMajors_table_name() {
        return majors_table_name;
    }

    public String getStudents_table_name() {
        return students_table_name;
    }

    public void initAllTables() {
        initMajors();
        initStudents();
    }

    private void initMajors() {
        if (countRecordsFromTable(majors_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            //Add dummy data
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (1, 'App Development', 'CIS')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (2, 'Psychology', 'PSYCH')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (3, 'Chemistry', 'CHEM')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (4, 'Biology', 'BIOL')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (5, 'Graphic Design', 'ART')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorID, majorName, majorPrefix) VALUES (6, 'English', 'ENGL')");

            //close the database
            db.close();
        }
    }

    public void initStudents() {
        if (countRecordsFromTable(students_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            //Add dummy data
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('CdeSist', 'Cecil', 'deSist', 'CdeSist@tumblr.com', 23, 2.8, 'App Development');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('STired', 'Sally', 'Tored', 'STired@yahoo.com', 25, 3.0, 'Psychology');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('QAsque', 'Quentin', 'Asque', 'QAsque@yahoo.com', 27, 1.3, 'Psychology');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('FdeSist', 'Fanny', 'deSist', 'FdeSist@yahoo.com', 26, 3.1, 'App Development');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('STop', 'Samuel', 'Top', 'STop@yahoo.com', 22, 4.0, 'App Development');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('YMom', 'Yvonne', 'Mom', 'YMom@yahoo.com', 29, 3.7, 'Chemistry');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('BOlogy', 'Beth', 'Ology', 'BOlogy@yahoo.com', 29, 3.7, 'Biology');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('GPhic', 'Ginny', 'Phic', 'GPhic@yahoo.com', 24, 1.5, 'Graphic Design');");
            db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('EGlish', 'Ebony', 'Glish', 'EGlish@yahoo.com', 28, 2.0, 'English');");

            //close database
            db.close();
        }
    }

    public int countRecordsFromTable(String tableName) {
        //get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //Count num entries
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        //close db
        db.close();

        return numRows;
    }

    //Check if a given username is already in the students table
    public boolean usernameExists(String newUsername) {
        //Step 1: get readable database (DONE)
        SQLiteDatabase db = this.getReadableDatabase();
        //Step 2: create SQL statement to execute (DONE)
        String checkUsername = "SELECT count(username) FROM " + students_table_name + " WHERE username = '" + newUsername + "';";
        //Step 3: run the query (DONE)
        Cursor cursor = db.rawQuery(checkUsername, null);
        //Step 4: move cursor to first (DONE)
        cursor.moveToFirst();
        //Step 5: get count (DONE)
        int count = cursor.getInt(0);
        //Step 6: close database (DONE)
        db.close();
        if (count != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //Add new student to student table
    //Code below DIDN'T WORK
    public void addNewStudent(String newU, String newF, String newL, String newE, Integer newA, Float newGPA, String newM) {
        Student newStudent = new Student();

        SQLiteDatabase db = this.getWritableDatabase();

        newStudent.setuName(newU);
        newStudent.setfName(newF);
        newStudent.setlName(newL);
        newStudent.seteMail(newE);
        newStudent.setAge(newA);
        newStudent.setGPA(newGPA);
        newStudent.setMajor(newM);

        /*db.execSQL("INSERT INTO " + students_table_name + "VALUES (newU, newF, newL, newE, newA, newGPA, newM) + "'';"); DIDN'T WORK */

        /*db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES (newU, newF, newL, newE, newA, newGPA, newM);"); DIDN'T WORK */

        db.execSQL("INSERT INTO " + students_table_name + " (username, fname, lname, email, age, GPA, major) VALUES ('" + newStudent.getuName() + "','" + newStudent.getfName() + "','" + newStudent.getlName() + "','" + newStudent.geteMail() + "','" + newStudent.getAge() + "','" + newStudent.getGPA() + "','" + newStudent.getMajor() + "');");

        db.close();
    }

    //Check if a given major name is already in the students table
    public boolean majorExists(String newMajor) {
        //Step 1: get readable database (DONE)
        SQLiteDatabase db = this.getReadableDatabase();
        //Step 2: create SQL statement to execute (DONE)
        String checkMajorName = "SELECT count(majorName) FROM " + majors_table_name + " WHERE majorName = '" + newMajor + "';";
        //Step 3: run the query (DONE)
        Cursor cursor = db.rawQuery(checkMajorName, null);
        //Step 4: move cursor to first (DONE)
        cursor.moveToFirst();
        //Step 5: get count (DONE)
        int count = cursor.getInt(0);
        //Step 6: close database (DONE)
        db.close();
        if (count != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    //add new major to major table
    public void addNewMajor(String majorN, String majorP) {
        Major newMajor = new Major();
        int numMajorRecords = this.countRecordsFromTable(this.getMajors_table_name());
        int newNumMajors = numMajorRecords + 1;
        SQLiteDatabase db = this.getWritableDatabase();

        newMajor.setId(newNumMajors);
        newMajor.setmName(majorN);
        newMajor.setmPrefix(majorP);

        db.execSQL("INSERT INTO " + majors_table_name + " (majorID, majorName, majorPrefix) VALUES ('" + newMajor.getId() + "','" + newMajor.getmName() + "','" + newMajor.getmPrefix() + "');");

        db.close();

    }

    @SuppressLint("Range")
    //attempt at filtering by criteria. DIDN'T WORK
    public ArrayList<String> findStudentGivenCritera(String u, String f, String l, String m, Float gpaLower, Float gpaUpper) {
        Log.d("passed data ", u + " " + f + " " + l + " " + m + " " + gpaLower.toString() + " " + gpaUpper.toString());
        ArrayList<String> listStudents = new ArrayList<String>();
        String selectStatement = "Select * from " + students_table_name + " Where ";
        if (u.isEmpty()) {
            selectStatement += "username is not null ";
        }
        else {
            selectStatement += "username = '" + u + "' ";
        }
        selectStatement += " and ";
        if (f.isEmpty()) {
            selectStatement += "fname is not null ";
        }
        else {
            selectStatement += "fname = '" + f + "' ";
        }
        selectStatement += " and ";
        if (l.isEmpty()) {
            selectStatement += "lname is not null ";
        }
        else {
            selectStatement += "lname = '" + f + "' ";
        }
        selectStatement += " and ";
        if (m.isEmpty()) {
            selectStatement += "major is not null ";
        }
        else {
            selectStatement += "major = '" + m + "' ";
        }
        selectStatement += " and ";
        if (gpaLower != null) {
            selectStatement += "GPA > " + gpaLower + "' ";
        }
        else {
            selectStatement += "GPA is not null ";
        }
        selectStatement += " and ";
        if (gpaUpper != null) {
            selectStatement += "GPA > " + gpaUpper + "' ";
        }
        else {
            selectStatement += "GPA is not null ";
        }
        selectStatement += ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectStatement, null);
        String uname, fname, lname, email;
        Integer age;
        Float gpa;
        String major;

        if (cursor.moveToFirst()) {
            do {
                uname = cursor.getString(cursor.getColumnIndex("username"));
                fname = cursor.getString(cursor.getColumnIndex("fname"));
                lname = cursor.getString(cursor.getColumnIndex("lname"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                age = cursor.getInt(cursor.getColumnIndex("age"));
                gpa = cursor.getFloat(cursor.getColumnIndex("GPA"));
                major = cursor.getString(cursor.getColumnIndex("major"));

                String info = uname + " " + fname + " " + lname + " " + email + " " + age + " " + gpa + " " + major;

                listStudents.add(info);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return listStudents;
    }
}

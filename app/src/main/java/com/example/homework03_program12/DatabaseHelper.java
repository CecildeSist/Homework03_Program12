package com.example.homework03_program12;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String database_name = "College.db";
    public static final String majors_table_name = "Majors";
    public static final String students_table_name = "Students";

    public DatabaseHelper(Context c) {
        super(c, database_name, null, 40);
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
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('App Development', 'CIS')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('Psychology', 'PSYCH')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('Chemistry', 'CHEM')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('Biology', 'BIOL')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('Graphic Design', 'ART')");
            db.execSQL("INSERT INTO " + majors_table_name + "(majorName, majorPrefix) VALUES ('English', 'ENGL')");

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

        db.execSQL("INSERT INTO " + majors_table_name + " (majorName, majorPrefix) VALUES ('" + newMajor.getmName() + "','" + newMajor.getmPrefix() + "');");

        db.close();

    }

    @SuppressLint("Range")
    public ArrayList<String> findStudentGivenCritera(String u, String f, String l, String m, Float gpaLower, Float gpaUpper) {
        Log.d("passed data ", u + " " + f + " " + l + " " + m + " " + gpaLower.toString() + " " + gpaUpper.toString());
        ArrayList<String> listStudents = new ArrayList<String>();
        //NOTE TO SELF DO NOT TOUCH THE SELECTSTATEMENT FORMATION FUNCTIONS, I FINALLY GOT THEM WORKING
        String selectStatement = "Select * from " + students_table_name + " Where ";
        if (u.isEmpty()) {
            selectStatement += "username is not null ";
        }
        else {
            selectStatement += "username = '" + u + "' ";
        }
        selectStatement += "and ";
        if (f.isEmpty()) {
            selectStatement += "fname is not null ";
        }
        else {
            selectStatement += "fname = '" + f + "' ";
        }
        selectStatement += "and ";
        if (l.isEmpty()) {
            selectStatement += "lname is not null ";
        }
        else {
            selectStatement += "lname = '" + l + "' ";
        }
        selectStatement += "and ";
        if (m.isEmpty()) {
            selectStatement += "major is not null ";
        }
        else {
            selectStatement += "major = '" + m + "' ";
        }
        if (gpaLower != null) {
            selectStatement += "and GPA > '" + gpaLower + "' ";
        }
        else {
            selectStatement += "and GPA is not null ";
        }
        if (gpaUpper != null) {
            selectStatement += "and GPA < '" + gpaUpper + "' ";
        }
        else {
            selectStatement += "and GPA is not null ";
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

    //NOTE TO SELF this function didn't work
    @SuppressLint("Range")
    public ArrayList<Student> filterStudents(String u, String f, String l, String m, Float gpaLower, Float gpaUpper) {
        Log.d("passed data ", u + " " + f + " " + l + " " + m + " " + gpaLower.toString() + " " + gpaUpper.toString());
        Student fStudent = null;
        ArrayList<Student> listFoundStudents = new ArrayList<Student>();
        String selectStatement = "Select * from " + students_table_name + " Where ";
        if (u.isEmpty()) {
            selectStatement += "username is not null ";
        }
        else {
            selectStatement += "username = '" + u + "' ";
        }
        selectStatement += "and ";
        if (f.isEmpty()) {
            selectStatement += "fname is not null ";
        }
        else {
            selectStatement += "fname = '" + f + "' ";
        }
        selectStatement += "and ";
        if (l.isEmpty()) {
            selectStatement += "lname is not null ";
        }
        else {
            selectStatement += "lname = '" + l + "' ";
        }
        selectStatement += "and ";
        if (m.isEmpty()) {
            selectStatement += "major is not null ";
        }
        else {
            selectStatement += "major = '" + m + "' ";
        }
        if (gpaLower != null) {
            selectStatement += "and GPA >= '" + gpaLower + "' ";
        }
        else {
            selectStatement += "and GPA is not null ";
        }
        if (gpaUpper != null) {
            selectStatement += "and GPA <= '" + gpaUpper + "' ";
        }
        else {
            selectStatement += "and GPA is not null ";
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
                Student searchedStudent = new Student(uname, fname, lname, email, age, gpa, major);
                listFoundStudents.add(searchedStudent);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return listFoundStudents;
    }

    @SuppressLint("Range")
    public ArrayList<Student> mainActivityStudents() {
        Log.d("filterStudents function executed?", "YES");
        ArrayList<Student> returnStudents = new ArrayList<>();
        String selectStatement = "SELECT * FROM " + students_table_name + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        //Run the query
        Cursor cursor = db.rawQuery(selectStatement, null);
        String uname, fname, lname, email;
        Integer age;
        Float gpa;
        String major;

        //Add students to array list
        if (cursor.moveToFirst()) {
            do {
                uname = cursor.getString(cursor.getColumnIndex("username"));
                fname = cursor.getString(cursor.getColumnIndex("fname"));
                lname = cursor.getString(cursor.getColumnIndex("lname"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                age = cursor.getInt(cursor.getColumnIndex("age"));
                gpa = cursor.getFloat(cursor.getColumnIndex("GPA"));
                major = cursor.getString(cursor.getColumnIndex("major"));

                Student studentAdded = new Student(uname, fname, lname, email, age, gpa, major);

                returnStudents.add(studentAdded);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return returnStudents;
    }

    @SuppressLint("Range")
    public ArrayList<String> populateSpinner() {
        //Step 1 get readable database
        SQLiteDatabase db = this.getWritableDatabase();

        String major;
        ArrayList<String> persistentMajors = new ArrayList<>();

        String selectStatement = "SELECT majorName FROM " + majors_table_name + " WHERE majorName IS NOT NULL;";
        Cursor cursor = db.rawQuery(selectStatement, null);
        if (cursor.moveToFirst()) {
            do {
                major = cursor.getString(cursor.getColumnIndex("majorName"));
                persistentMajors.add(major);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return persistentMajors;
    }

    //Attempt 3 at updating a student
    @SuppressLint("Range")
    public void replaceStudentTakeThree(Student oldStudentData, String newF, String newL, String newE, Integer newAge, Float newGPA, String newMajor) {
        //Step 1: get writeable instance of database
        SQLiteDatabase db = this.getWritableDatabase();

        String oldUsername = oldStudentData.getuName();
        String oldFName = oldStudentData.getfName();
        String oldLName = oldStudentData.getlName();
        String oldEmail = oldStudentData.geteMail();
        Integer oldAge = oldStudentData.getAge();
        Float oldGPA = oldStudentData.getGPA();
        String oldMajor = oldStudentData.getMajor();

        //Step 2: Create SQLite statement for query. NOTE TO SELF check if you need a space after the value for oldUsername
        String queryStatement = "SELECT fname, lname, email, age, GPA, major FROM " + students_table_name + " WHERE username = " + oldUsername + ";";
        Cursor cursor = db.rawQuery(queryStatement, null);

        //Run if queryCursor returns a result
        if (cursor.moveToFirst()) {
            //Replace fname
            String updateStatement = "UPDATE " + students_table_name + " SET fname =  replace (fname, " + oldFName + ", " + newF + ");";
            cursor = db.rawQuery(updateStatement, null);
            //Replace lname
            updateStatement = "UPDATE " + students_table_name + " SET lname =  replace (lname, " + oldLName + ", " + newL + ");";
            cursor = db.rawQuery(updateStatement, null);
            //Replace email
            updateStatement = "UPDATE " + students_table_name + " SET email =  replace (email, " + oldEmail + ", " + newE + ");";
            cursor = db.rawQuery(updateStatement, null);
            //Replace age
            updateStatement = "UPDATE " + students_table_name + " SET age =  replace (age, " + oldAge + ", " + newAge + ");";
            cursor = db.rawQuery(updateStatement, null);
            //Replace GPA
            updateStatement = "UPDATE " + students_table_name + " SET GPA =  replace (GPA, " + oldGPA + ", " + newGPA + ");";
            cursor = db.rawQuery(updateStatement, null);
            //Replace major name
            updateStatement = "UPDATE " + students_table_name + " SET major =  replace (major, " + oldMajor + ", " + newMajor + ");";
            cursor = db.rawQuery(updateStatement, null);

            //Log.d statement to ensure everything works
            String uname, fname, lname, email, major;
            Integer age;
            Float gpa;
            uname = cursor.getString(cursor.getColumnIndex("username"));
            fname = cursor.getString(cursor.getColumnIndex("fname"));
            lname = cursor.getString(cursor.getColumnIndex("lname"));
            email = cursor.getString(cursor.getColumnIndex("email"));
            age = cursor.getInt(cursor.getColumnIndex("age"));
            gpa = cursor.getFloat(cursor.getColumnIndex("GPA"));
            major = cursor.getString(cursor.getColumnIndex("major"));

            String info = uname + " " + fname + " " + lname + " " + email + " " + age + " " + gpa + " " + major;
            Log.d("New Data", info);
        }
    }

    //Functions for filtering
    @SuppressLint("Range")
    public ArrayList<Student> sendFilteredStudents (ArrayList<String> filterArrayList) {
        //Step 1 get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        Student filteredStu;

        ArrayList<Student> arrayListStu = new ArrayList<>();

        String uname, fname, lname, email;
        Integer age;
        Float gpa;
        String major;

        if (!filterArrayList.isEmpty()) {
            for (int i = 0; i < filterArrayList.size(); i++) {
                //get username by cutting string off after first space therein
                String uNameFromStringArray = filterArrayList.get(i).replaceAll(" .*", "");
                String selectStatement = "SELECT * FROM " + students_table_name + " WHERE username = '" + uNameFromStringArray + "';";
                Cursor cursor = db.rawQuery(selectStatement, null);
                if (cursor.moveToFirst()) {
                    do {
                        uname = cursor.getString(cursor.getColumnIndex("username"));
                        fname = cursor.getString(cursor.getColumnIndex("fname"));
                        lname = cursor.getString(cursor.getColumnIndex("lname"));
                        email = cursor.getString(cursor.getColumnIndex("email"));
                        age = cursor.getInt(cursor.getColumnIndex("age"));
                        gpa = cursor.getFloat(cursor.getColumnIndex("GPA"));
                        major = cursor.getString(cursor.getColumnIndex("major"));

                        filteredStu = new Student(uname, fname, lname, email, age, gpa, major);

                        arrayListStu.add(filteredStu);
                    }
                    while (cursor.moveToNext());
                }
            }
        }

        db.close();
        return arrayListStu;
    }
}

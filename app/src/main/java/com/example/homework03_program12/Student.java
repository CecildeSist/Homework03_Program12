package com.example.homework03_program12;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Student implements Serializable {
    String uName, fName, lName, eMail;
    Integer age;
    Float GPA;
    String major;

    public Student(){

    }

    public Student(String u, String f, String l, String e, Integer a, Float gpa, String m) {
        uName = u;
        fName = f;
        lName = l;
        eMail = e;
        age = a;
        GPA = gpa;
        major = m;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getGPA() {
        return GPA;
    }

    public void setGPA(Float GPA) {
        this.GPA = GPA;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    static class studentMajor {
        static ArrayList<String> majors = new ArrayList<>(Arrays.asList("App Development", "Psychology", "Chemistry", "Biology", "Graphic Design", "English"));

        public static ArrayList<String> getAllStudentMajors() {
            return majors;
        }

        public static void addStudentMajor(String newMajorInStudent) {
            majors.add(newMajorInStudent);
        }

        public static String getStudentMajorAt(int i) {
            return majors.get(i);
        }
    }
}

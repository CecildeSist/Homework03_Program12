package com.example.homework03_program12;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Major implements Serializable {
    private int id;
    private String mName;
    private String mPrefix;

    public Major(){

    }

    public Major(int i, String n, String p) {
        id = i;
        mName = n;
        mPrefix = p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    static class majorNameStrings {
        static ArrayList<String> majorStrings = new ArrayList<>(Arrays.asList("App Development", "Psychology", "Chemistry", "Biology", "Graphic Design", "English"));
        public static ArrayList<String> getAllMajorStrings() {
            return majorStrings;
        }
        public static void addMajorString(String newMajorString){
            majorStrings.add(newMajorString);
        }

        public static String getMajorStringAt(int i) {
            return majorStrings.get(i);
        }
    }

    public String getmPrefix() {
        return mPrefix;
    }

    public void setmPrefix(String mPrefix) {
        this.mPrefix = mPrefix;
    }
}

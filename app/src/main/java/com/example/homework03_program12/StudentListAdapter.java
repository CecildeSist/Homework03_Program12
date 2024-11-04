package com.example.homework03_program12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> listOfStudents;

    public StudentListAdapter() {
        
    }

    public StudentListAdapter(Context c, ArrayList<Student> ls) {
        context = c;
        listOfStudents = ls;
    }
    @Override
    public int getCount() {
        return listOfStudents.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfStudents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.listview_cell, null);
        }

        //Find GUI elements in custom cell
        TextView studentUName = view.findViewById(R.id.tv_cell_u);
        TextView studentFName = view.findViewById(R.id.tv_cell_f);
        TextView studentLName = view.findViewById(R.id.tv_cell_l);
        TextView studentEMail = view.findViewById(R.id.tv_cell_e);
        TextView studentAge = view.findViewById(R.id.tv_cell_age);
        TextView studentGPA = view.findViewById(R.id.tv_cell_GPA);
        TextView studentMajorName = view.findViewById(R.id.tv_cell_majorName);

        //Retrieve the student
        Student student = listOfStudents.get(i);

        //Set GUI
        studentUName.setText(student.getuName());
        studentFName.setText(student.getfName());
        studentLName.setText(student.getlName());
        studentEMail.setText(student.geteMail());
        studentAge.setText(student.getAge().toString());
        studentGPA.setText(student.getGPA().toString());
        studentMajorName.setText(student.getMajor().toString());

        return view;
    }
}

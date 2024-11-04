package com.example.homework03_program12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilterBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> filteredStudents;

    public FilterBaseAdapter(Context c, ArrayList<Student> ls) {
        context = c;
        filteredStudents = ls;
    }

    @Override
    public int getCount() {
        return filteredStudents.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredStudents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(FilterStudents.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.listview_cell, null);
        }

        //Assign values to custom cell's GUI elements (NOT DONE YET)
        //Step 1. Call GUI TextViews (DONE)
        TextView filter_u = view.findViewById(R.id.tv_cell_u);
        TextView filter_f = view.findViewById(R.id.tv_cell_f);
        TextView filter_l = view.findViewById(R.id.tv_cell_l);
        TextView filter_e = view.findViewById(R.id.tv_cell_e);
        TextView filter_a = view.findViewById(R.id.tv_cell_age);
        TextView filter_GPA = view.findViewById(R.id.tv_cell_GPA);
        TextView filter_m = view.findViewById(R.id.tv_cell_majorName);

        //Step 2: retrieve what to set each text box to (NOTE TO SELF MUST TEST)
        final int position = i;

        filter_u.setText(filteredStudents.get(i).getuName());
        filter_f.setText(filteredStudents.get(i).getfName());
        filter_l.setText(filteredStudents.get(i).getlName());
        filter_e.setText(filteredStudents.get(i).geteMail());
        filter_a.setText(filteredStudents.get(i).getAge().toString());
        filter_GPA.setText(filteredStudents.get(i).getGPA().toString());
        filter_m.setText(filteredStudents.get(i).getMajor());

        return view;
    }
}

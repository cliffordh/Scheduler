package com.example.chelsel.scheduler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chelsel.scheduler.entity.Course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    private Context mContext;
    private List<Course> courseList = new ArrayList<>();

    public CourseAdapter(@NonNull Context context, ArrayList<Course> list) {
        super(context,0,list);
        mContext = context;
        courseList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.course_row,parent,false);

        Course currentCourse = courseList.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");

        TextView title = (TextView) listItem.findViewById(R.id.textView_title);
        title.setText(currentCourse.title);

        TextView startDate = (TextView) listItem.findViewById(R.id.textView_startdate);
        String formattedStartDate = dateFormat.format(currentCourse.startDate);
        startDate.setText(formattedStartDate);

        TextView endDate = (TextView) listItem.findViewById(R.id.textView_enddate);
        String formattedEndDate = dateFormat.format(currentCourse.endDate);
        endDate.setText(formattedEndDate);

        return listItem;
    }
}

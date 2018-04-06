package com.example.chelsel.scheduler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.chelsel.scheduler.entity.Mentor;

import java.util.List;
import java.util.ArrayList;

public class MentorAdapter extends ArrayAdapter<Mentor> {

    private Context mContext;
    private List<Mentor> mentorList = new ArrayList<>();

    public MentorAdapter(@NonNull Context context, ArrayList<Mentor> list) {
        super(context,0,list);
        mContext = context;
        mentorList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.mentor_row,parent,false);

        Mentor currentMentor = mentorList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentMentor.name);

        TextView email = (TextView) listItem.findViewById(R.id.textView_email);
        email.setText(currentMentor.email);

        TextView phone = (TextView) listItem.findViewById(R.id.textView_phone);
        phone.setText(currentMentor.phone);

        return listItem;
    }
}

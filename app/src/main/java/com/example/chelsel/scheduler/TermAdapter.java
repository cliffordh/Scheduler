package com.example.chelsel.scheduler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chelsel.scheduler.entity.Term;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends ArrayAdapter<Term> {

    private Context mContext;
    private List<Term> termList = new ArrayList<>();

    public TermAdapter(@NonNull Context context, ArrayList<Term> list) {
        super(context,0,list);
        mContext = context;
        termList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.term_row,parent,false);

        Term currentTerm = termList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_title);
        name.setText(currentTerm.title);

//        TextView email = (TextView) listItem.findViewById(R.id.textView_startdate);
//        email.setText(currentTerm.startDate);

//        TextView phone = (TextView) listItem.findViewById(R.id.textView_enddate);
//        phone.setText(currentTerm.endDate);

        return listItem;
    }
}

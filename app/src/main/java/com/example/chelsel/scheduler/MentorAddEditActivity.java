package com.example.chelsel.scheduler;

import android.os.Bundle;
import android.app.Activity;

import com.example.chelsel.scheduler.entity.Mentor;

public class MentorAddEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // To retrieve object in second Activity
        Mentor m =(Mentor) getIntent().getSerializableExtra("mentor");
        if(m==null)
        {
            setTitle("Add Mentor");
        } else
        {
            setTitle("Edit Mentor");
        }
    }

}

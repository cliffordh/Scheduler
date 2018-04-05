package com.example.chelsel.scheduler;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.chelsel.scheduler.entity.Mentor;

public class MentorAddEditActivity extends Activity {

    private Mentor m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // To retrieve object in second Activity
        m =(Mentor) getIntent().getSerializableExtra("mentor");
        if(m==null)
        {
            setTitle("Add Mentor");
        } else
        {
            setTitle("Edit Mentor");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mentor_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                return true;
            case R.id.action_delete:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

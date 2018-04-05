package com.example.chelsel.scheduler;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chelsel.scheduler.entity.Mentor;

public class MentorAddEditActivity extends Activity {

    private Mentor m;
    private Button saveButton;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add_edit);

        final AppDataBase database = AppDataBase.getAppDatabase(this);
        saveButton = (Button) findViewById(R.id.savebutton);
        nameEdit = (EditText) findViewById(R.id.editname);
        phoneEdit = (EditText) findViewById(R.id.editphone);
        emailEdit = (EditText) findViewById(R.id.editemail);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mentor newMentor=new Mentor();
                newMentor.name=nameEdit.getText().toString().trim();
                newMentor.phone=phoneEdit.getText().toString().trim();
                newMentor.email=emailEdit.getText().toString().trim();
                database.mentorDao().insert(newMentor);
                Mentor[] mentors=database.mentorDao().loadAll();
                System.out.println(mentors);
                Toast.makeText(getApplicationContext(), "New mentor saved!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        m =(Mentor) getIntent().getSerializableExtra("mentor");
        if(m==null)
        {
            setTitle("Add Mentor");
            saveButton.setVisibility(View.VISIBLE);
        } else
        {
            setTitle("Mentor");
            saveButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(m!=null) { // View, enable Edit & Delete
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mentor_add_edit, menu);
        }
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

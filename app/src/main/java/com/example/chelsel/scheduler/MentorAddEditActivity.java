package com.example.chelsel.scheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chelsel.scheduler.entity.Mentor;

public class MentorAddEditActivity extends AppCompatActivity {

    private Mentor m;
    private Button saveButton;
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_add_edit);

        saveButton = (Button) findViewById(R.id.savebutton);
        nameEdit = (EditText) findViewById(R.id.editname);
        phoneEdit = (EditText) findViewById(R.id.editphone);
        emailEdit = (EditText) findViewById(R.id.editemail);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=true;
                if(m==null) {
                   m=new Mentor();
                   isUpdate=false;
                }
                m.name=nameEdit.getText().toString().trim();
                m.phone=phoneEdit.getText().toString().trim();
                m.email=emailEdit.getText().toString().trim();
                if(isUpdate)
                    database.mentorDao().update(m);
                else
                    database.mentorDao().insert(m);
                Toast.makeText(getApplicationContext(), "Mentor saved!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        m =(Mentor) getIntent().getSerializableExtra("mentor");
        if(m==null)
        {
            setTitle("Add Mentor");
        } else
        {
            setTitle("View/Edit Mentor");
            nameEdit.setText(m.name);
            emailEdit.setText(m.email);
            phoneEdit.setText(m.phone);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(m!=null) { // View, enable Edit & Delete
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete:
                database.mentorDao().delete(m);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

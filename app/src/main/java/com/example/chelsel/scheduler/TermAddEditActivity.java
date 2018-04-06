package com.example.chelsel.scheduler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chelsel.scheduler.entity.Term;

public class TermAddEditActivity extends Activity {

    private Term m;
    private Button saveButton;
    private EditText titleEdit;
    private EditText startdateEdit;
    private EditText enddateEdit;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add_edit);

        saveButton = (Button) findViewById(R.id.savebutton);
        titleEdit = (EditText) findViewById(R.id.edittitle);
        startdateEdit = (EditText) findViewById(R.id.editstartdate);
        enddateEdit = (EditText) findViewById(R.id.editenddate);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=true;
                if(m==null) {
                   m=new Term();
                   isUpdate=false;
                }
                m.title=titleEdit.getText().toString().trim();
//                m.startDate=startdateEdit.getText().toString().trim();
//                m.endDate=enddateEdit.getText().toString().trim();
                if(isUpdate)
                    database.termDao().update(m);
                else
                    database.termDao().insert(m);
                Toast.makeText(getApplicationContext(), "Term saved!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        m =(Term) getIntent().getSerializableExtra("term");
        if(m==null)
        {
            setTitle("Add Term");
        } else
        {
            setTitle("View/Edit Term");
            titleEdit.setText(m.title);
//            startdateEdit.setText(m.startDate);
//            enddateEdit.setText(m.endDate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(m!=null) { // View, enable Edit & Delete
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.delete, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete:
                database.termDao().delete(m);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
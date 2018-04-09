package com.example.chelsel.scheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Term;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TermAddEditActivity extends AppCompatActivity {

    private Term m;
    private Button saveButton;
    private EditText titleEdit;
    private EditText startdateEdit;
    private EditText enddateEdit;
    private ListView listView;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private EditableCourseAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private EditableCourseAdapter fetchList() {

        AppDataBase database = AppDataBase.getAppDatabase(this);
        Course[] courseArray = database.courseDao().loadAll();
        ArrayList<Course> list = new ArrayList<>(Arrays.asList(courseArray));
        return new EditableCourseAdapter(this,list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add_edit);

        saveButton = (Button) findViewById(R.id.savebutton);
        titleEdit = (EditText) findViewById(R.id.edittitle);
        startdateEdit = (EditText) findViewById(R.id.editstartdate);
        enddateEdit = (EditText) findViewById(R.id.editenddate);

        listView =(ListView)findViewById(R.id.contentlist);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CourseListActivity.this, CourseAddEditActivity.class);
                Course course = (Course) parent.getItemAtPosition(position);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=true;
                if(m==null) {
                   m=new Term();
                   isUpdate=false;
                }
                m.title=titleEdit.getText().toString().trim();
                m.startDate=formatter.parse(startdateEdit.getText().toString().trim(),new ParsePosition(0));
                m.endDate=formatter.parse(enddateEdit.getText().toString().trim(),new ParsePosition(0));
                if(isUpdate)
                    database.termDao().update(m);
                else
                    database.termDao().insert(m);
                Toast.makeText(getApplicationContext(), "Term saved!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        m =(Term) getIntent().getSerializableExtra("term");
        if(m==null)
        {
            setTitle("Add Term");
        } else
        {
            setTitle("View/Edit Term");
            titleEdit.setText(m.title);
            startdateEdit.setText(formatter.format(m.startDate));
            enddateEdit.setText(formatter.format(m.endDate));
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

private class EditableCourseAdapter extends ArrayAdapter<Course> {

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

        return listItem;
    }
}
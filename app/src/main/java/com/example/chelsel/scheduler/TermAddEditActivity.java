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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelsel.scheduler.dao.TermDao;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Term;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TermAddEditActivity extends AppCompatActivity {

    private Term m;
    private EditText titleEdit;
    private EditText startdateEdit;
    private EditText enddateEdit;
    private ListView listView;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//    private EditableCourseAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private EmbeddedCourseAdapter fetchList() {
        ArrayList<Course> list;
        list = new ArrayList<>(Arrays.asList(database.courseDao().loadAvailableCoursesForTerm(m!=null?m.termid:0)));
        return new EmbeddedCourseAdapter(this,list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add_edit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        titleEdit = findViewById(R.id.edittitle);
        startdateEdit = findViewById(R.id.editstartdate);
        enddateEdit = findViewById(R.id.editenddate);

        listView = findViewById(R.id.contentlist);
/*        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CourseListActivity.this, CourseAddEditActivity.class);
                Course course = (Course) parent.getItemAtPosition(position);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        });
            */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Integer i=(Integer) getIntent().getSerializableExtra("termid");
        if(i==null)
        {
            setTitle("Add Term");
        } else
        {
            m = database.termDao().getTermWithCourses(i.intValue());
            setTitle("View/Edit Term");
            titleEdit.setText(m.title);
            startdateEdit.setText(formatter.format(m.startDate));
            enddateEdit.setText(formatter.format(m.endDate));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(m!=null) { // View, enable Edit & Delete
            inflater.inflate(R.menu.edit, menu);
        } else {
            inflater.inflate(R.menu.save, menu);
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
            case R.id.action_save:
                boolean isUpdate=true;
                if(m==null) {
                   m=new Term();
                   m.termid= TermDao.getNextTermId(getApplicationContext());
                   isUpdate=false;
                }
                m.title=titleEdit.getText().toString().trim();
                m.startDate=formatter.parse(startdateEdit.getText().toString().trim(),new ParsePosition(0));
                m.endDate=formatter.parse(enddateEdit.getText().toString().trim(),new ParsePosition(0));
                if(isUpdate)
                    database.termDao().update(m);
                else
                    database.termDao().insert(m);
                Toast.makeText(getApplicationContext(), "Term saved!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
             default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class EmbeddedCourseAdapter extends ArrayAdapter<Course> {

    private Context mContext;
    private List<Course> courseList = new ArrayList<>();

    public EmbeddedCourseAdapter(@NonNull Context context, ArrayList<Course> list) {
        super(context,0,list);
        mContext = context;
        courseList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.embedded_course_row,parent,false);

        Course currentCourse = courseList.get(position);

        TextView title = listItem.findViewById(R.id.textView_title);
        title.setText(currentCourse.title);

        CheckBox cb = listItem.findViewById(R.id.checkbox_selected);
        if(currentCourse.termid!=0) {
            cb.setChecked(true);
        }

        return listItem;
    }
}

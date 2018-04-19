package com.example.chelsel.scheduler;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class TermAddEditActivity extends AppCompatActivity {

    private Term term;
    private EditText titleEdit;
    private EditText startdateEdit;
    private EditText enddateEdit;
    private ListView listView;

    private boolean isNew;

    private ArrayList<Course> list;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//    private EditableCourseAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private EmbeddedCourseAdapter fetchList() {
        list = new ArrayList<>(Arrays.asList(database.courseDao().loadAvailableCoursesForTerm(term !=null? term.termid:0)));
        return new EmbeddedCourseAdapter(this,list, term);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Integer i=(Integer) getIntent().getSerializableExtra("termid");
        if(i==null)
        {
            setTitle("Add Term");
            isNew = true;
            term =new Term();
            term.termid= TermDao.getNextTermId(getApplicationContext());
        } else
        {
            isNew = false;
            term = database.termDao().getTerm(i.intValue());
            setTitle("View/Edit Term");
            titleEdit.setText(term.title);
            startdateEdit.setText(formatter.format(term.startDate));
            enddateEdit.setText(formatter.format(term.endDate));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(!isNew) { // View, enable Edit & Delete
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
                database.termDao().delete(term);
                finish();
                return true;
            case R.id.action_save:
                term.title=titleEdit.getText().toString().trim();
                term.startDate=formatter.parse(startdateEdit.getText().toString().trim(),new ParsePosition(0));
                term.endDate=formatter.parse(enddateEdit.getText().toString().trim(),new ParsePosition(0));
                // attach a Course array to the Term with the selected courses
                term.setCourseList(list);
                if(isNew)
                    database.termDao().insertTermWithCourses(term);
                else
                    database.termDao().updateTermWithCourses(term);
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
    private Term term;

    public EmbeddedCourseAdapter(@NonNull Context context, ArrayList<Course> list, Term term) {
        super(context,0,list);
        mContext = context;
        courseList = list;
        this.term = term;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.embedded_course_row,parent,false);

        final Course currentCourse = courseList.get(position);

        TextView title = listItem.findViewById(R.id.textView_title);
        title.setText(currentCourse.title);

        CheckBox cb = listItem.findViewById(R.id.checkbox_selected);
        if(currentCourse.termid!=0) {
            cb.setChecked(true);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentCourse.termid = isChecked?term.termid:0;
            }
        });

        // add listener to the checkbox to change the termid in the associated course

        return listItem;
    }
}

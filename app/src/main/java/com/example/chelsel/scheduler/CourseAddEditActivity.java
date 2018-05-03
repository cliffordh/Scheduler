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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelsel.scheduler.dao.CourseDao;
import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Term;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseAddEditActivity extends AppCompatActivity {

    private Course course;
    private EditText titleEdit;
    private EditText startdateEdit;
    private EditText enddateEdit;
    private ListView listView;

    private boolean isNew;

    private ArrayList<Assessment> list;

    final AppDataBase database = AppDataBase.getAppDatabase(this);

    final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private EmbeddedAssessmentAdapter fetchList() {
        list = new ArrayList<>(Arrays.asList(database.assessmentDao().loadAvailableAssessmentsForCourse(course !=null? course.courseid:0)));
        return new EmbeddedAssessmentAdapter(this,list, course);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add_edit);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        titleEdit = (EditText) findViewById(R.id.edittitle);
        startdateEdit = (EditText) findViewById(R.id.editstartdate);
        enddateEdit = (EditText) findViewById(R.id.editenddate);

        listView = findViewById(R.id.contentlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Integer i=(Integer) getIntent().getSerializableExtra("courseid");
        if(i==null)
        {
            setTitle("Add Course");
            isNew = true;
            course =new Course();
            course.courseid= CourseDao.getNextCourseId(getApplicationContext());
        } else
        {
            isNew = false;
            course = database.courseDao().getCourse(i.intValue());
            setTitle("View/Edit Course");
            titleEdit.setText(course.title);
            startdateEdit.setText(formatter.format(course.startDate));
            enddateEdit.setText(formatter.format(course.endDate));
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
                database.courseDao().delete(course);
                finish();
                return true;
            case R.id.action_save:
                course.title=titleEdit.getText().toString().trim();
                course.startDate=formatter.parse(startdateEdit.getText().toString().trim(),new ParsePosition(0));
                course.endDate=formatter.parse(enddateEdit.getText().toString().trim(),new ParsePosition(0));

                ArrayList<Assessment> newList=new ArrayList<>();
                for (Assessment assessment:list) {
                    if(assessment.courseid!=0)
                        newList.add(assessment);
                }
                course.setAssessmentList(newList);

                ArrayList<Mentor> mentorList=new ArrayList<>();
                for (Mentor mentor:mentorList) {
                    if(mentor.courseid!=0)
                        mentorList.add(mentor);
                }
                course.setMentorList(mentorList);

                if(isNew)
                    database.courseDao().insertCourseWithLists(course);
                else
                    database.courseDao().updateCourseWithLists(course);

                Toast.makeText(getApplicationContext(), "Course saved!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class EmbeddedAssessmentAdapter extends ArrayAdapter<Assessment> {

    private Context mContext;
    private List<Assessment> assessmentList = new ArrayList<>();
    private Course course;

    public EmbeddedAssessmentAdapter(@NonNull Context context, ArrayList<Assessment> list, Course course) {
        super(context,0,list);
        mContext = context;
        assessmentList = list;
        this.course = course;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.embedded_row,parent,false);

        final Assessment currentAssessment = assessmentList.get(position);

        TextView title = listItem.findViewById(R.id.textView_title);
        title.setText(currentAssessment.title);

        CheckBox cb = listItem.findViewById(R.id.checkbox_selected);
        cb.setOnCheckedChangeListener(null);
        if(currentAssessment.courseid==course.courseid) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentAssessment.courseid = isChecked?course.courseid:0;
            }
        });

        return listItem;
    }
}
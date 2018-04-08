package com.example.chelsel.scheduler;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chelsel.scheduler.entity.Course;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseListActivity extends AppCompatActivity {

    private ListView listView;
    private CourseAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private CourseAdapter fetchList() {

        AppDataBase database = AppDataBase.getAppDatabase(this);
        Course[] courseArray = database.courseDao().loadAll();
        ArrayList<Course> list = new ArrayList<>(Arrays.asList(courseArray));
        return new CourseAdapter(this,list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, CourseAddEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

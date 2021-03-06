package com.example.chelsel.scheduler;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.chelsel.scheduler.entity.Mentor;

public class MentorListActivity extends AppCompatActivity {

    private ListView listView;
    private MentorAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private MentorAdapter fetchList() {

        AppDataBase database = AppDataBase.getAppDatabase(this);
        Mentor[] mentorArray = database.mentorDao().loadAll();
        ArrayList<Mentor> list = new ArrayList<>(Arrays.asList(mentorArray));
        return new MentorAdapter(this,list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);

        listView =(ListView)findViewById(R.id.contentlist);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MentorListActivity.this, MentorAddEditActivity.class);
                Mentor mentor = (Mentor) parent.getItemAtPosition(position);
                intent.putExtra("mentor", mentor);
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
                Intent intent = new Intent(this, MentorAddEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

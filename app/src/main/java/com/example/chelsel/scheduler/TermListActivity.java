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

import com.example.chelsel.scheduler.entity.Term;

import java.util.ArrayList;
import java.util.Arrays;

public class TermListActivity extends AppCompatActivity {

    private ListView listView;
    private TermAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(fetchList());
    }

    private TermAdapter fetchList() {

        AppDataBase database = AppDataBase.getAppDatabase(this);
        Term[] termArray = database.termDao().loadAll();
        ArrayList<Term> list = new ArrayList<>(Arrays.asList(termArray));
        return new TermAdapter(this,list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        listView =(ListView)findViewById(R.id.contentlist);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(TermListActivity.this, TermAddEditActivity.class);
                Term term = (Term) parent.getItemAtPosition(position);
                intent.putExtra("termid", new Integer(term.termid));
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
                Intent intent = new Intent(this, TermAddEditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

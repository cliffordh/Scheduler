package com.example.chelsel.scheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import com.example.chelsel.scheduler.utilities.*;

//TODO: https://www.youtube.com/watch?v=0s6x3Sn4eYo Implement sliding animations

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDataBase database = AppDataBase.getAppDatabase(this);

        DataGenerator.with(database).reset(); // clear out existing tables

        DataGenerator.with(database).generateMentors();
        DataGenerator.with(database).generateAssessments();
        DataGenerator.with(database).generateTerms();
        DataGenerator.with(database).generateCourses();
    }

    /** Called when the user taps the Mentor button */
    public void mentorPressed(View view) {
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Courses button */
    public void coursesPressed(View view) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Terms button */
    public void termsPressed(View view) {
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }
    /** Called when the user taps the Settings button */
    public void settingsPressed(View view) {
//        Intent intent = new Intent(this, MentorListActivity.class);
//        startActivity(intent);
    }
}

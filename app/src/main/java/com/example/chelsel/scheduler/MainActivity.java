package com.example.chelsel.scheduler;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import com.example.chelsel.scheduler.utilities.*;

//TODO: https://www.youtube.com/watch?v=0s6x3Sn4eYo Implement sliding animations

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDataBase database = AppDataBase.getAppDatabase(this);

        DataGenerator.with(database).reset(); // clear out existing tables

        DataGenerator.with(database).generateMentors();
        DataGenerator.with(database).generateAssessments();

        Logger.displayMentorsInLog(database.mentorDao().loadAll());
        Logger.displayAssessmentsInLog(database.assessmentDao().loadAll());
    }

    /** Called when the user taps the Mentor button */
    public void mentorPressed(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /** Called when the user taps the Courses button */
    public void coursesPressed(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Terms button */
    public void termsPressed(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent);
    }
    /** Called when the user taps the Settings button */
    public void settingsPressed(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent);
    }
}

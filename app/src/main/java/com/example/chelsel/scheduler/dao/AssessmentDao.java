package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Course;

@Dao
public abstract class AssessmentDao {

    @Insert
    public abstract void insert(Assessment... assessments);

    @Update
    public abstract void update(Assessment... assessments);

    @Delete
    public abstract void delete(Assessment... assessments);

    @Query ("DELETE FROM Assessment")
    public abstract void truncateAssessments();

    @Transaction
    @Query("Select * FROM Assessment")
    public abstract Assessment[] loadAll();

    @Query("SELECT * FROM Assessment where courseid = 0 OR courseid =:courseid")
    public abstract Assessment[] loadAvailableAssessmentsForCourse(int courseid);

    public static int getNextAssessmentId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        int assessmentIdKey=sharedPref.getInt("assessmentidkey",1);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("assessmentidkey",assessmentIdKey+1);
        editor.commit();
        return assessmentIdKey;
    }

}
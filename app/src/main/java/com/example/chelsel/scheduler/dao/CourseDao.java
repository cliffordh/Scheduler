package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.chelsel.scheduler.entity.Course;

import java.util.ArrayList;

@Dao
public abstract class CourseDao {

    @Insert
    public abstract void insert(Course... course);

    @Update
    public abstract void update(Course... course);

    @Delete
    public abstract void delete(Course... course);

    @Query ("DELETE FROM Course")
    public abstract void truncateCourses();

    @Transaction
    @Query("Select * FROM Course")
    public abstract Course[] loadAll();

    @Query("SELECT * FROM Course where termid = 0 OR termid =:termid")
    public abstract Course[] loadAvailableCoursesForTerm(int termid);

    public static int getNextCourseId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        int courseIdKey=sharedPref.getInt("courseidkey",1);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("courseidkey",courseIdKey+1);
        editor.commit();
        return courseIdKey;
    }

}

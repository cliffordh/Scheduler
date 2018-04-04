package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.pojo.CoursePojo;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(Course... course);

    @Update
    void update(Course... course);

    @Delete
    void delete(Course... course);

    @Transaction
    @Query("Select * FROM Course")
    List<CoursePojo> loadCourses();
}

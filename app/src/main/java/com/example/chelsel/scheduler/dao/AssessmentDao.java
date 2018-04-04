package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.chelsel.scheduler.entity.Assessment;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(Assessment... assessments);

    @Update
    void update(Assessment... assessments);

    @Delete
    void delete(Assessment... assessments);

    @Query ("DELETE FROM Assessment")
    void truncateMentors();

    @Query("Select * FROM Assessment")
    Assessment[] loadAll();
}
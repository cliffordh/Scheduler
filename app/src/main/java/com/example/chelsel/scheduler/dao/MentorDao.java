package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.chelsel.scheduler.entity.Mentor;

@Dao
public interface MentorDao {

    @Insert
    void insert(Mentor... mentors);

    @Update
    void update(Mentor... mentors);

    @Delete
    void delete(Mentor... mentors);

    @Query ("DELETE FROM Mentor")
    void truncateMentors();

    @Query("Select * FROM Mentor")
    Mentor[] loadAll();
}
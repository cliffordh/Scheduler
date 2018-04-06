package com.example.chelsel.scheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.chelsel.scheduler.entity.Term;
import com.example.chelsel.scheduler.pojo.TermPojo;
import java.util.List;

@Dao
public interface TermDao {

    @Insert
    void insert(Term... terms);

    @Update
    void update(Term... terms);

    @Delete
    void delete(Term... terms);

    @Transaction
    @Query("Select * FROM Term")
    Term[] loadAll();
//    List<TermPojo> loadTerms();

    @Query ("DELETE FROM Term")
    void truncateTerms();

}

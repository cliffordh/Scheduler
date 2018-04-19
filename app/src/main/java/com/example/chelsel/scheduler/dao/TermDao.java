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
import com.example.chelsel.scheduler.entity.Term;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

@Dao
public abstract class TermDao {

    @Insert
    public abstract void insert(Term... terms);

    @Insert
    public abstract void insertCourseList(List<Course> courses);

    @Update
    public abstract void updateCourseList(List<Course> courses);

    @Query("SELECT * FROM Term where termid = :termid")
    public abstract Term getTerm(int termid);

    @Query("SELECT * FROM Course WHERE termid = :termid")
    public abstract List<Course> getCourseList(int termid);

    public void insertTermWithCourses(Term term) {
        List<Course> courses = term.getCourseList();
        for (int i=0;i<courses.size();i++) {
            courses.get(i).termid=term.termid;
        }
        updateCourseList(courses);
        insert(term);
    }

    /* Disassociate any courses from the term. */
    @Query("UPDATE Course SET termid=0 WHERE termid = :termid")
    public abstract void resetCoursesForTerm(int termid);

    /* This function accepts a Term with embedded courses, removes all course associations for that term,
    then reassociates based on the new course list. This handles deletions of courses in the term editor.
     */
    public void updateTermWithCourses(Term term) {
        List<Course> courses = term.getCourseList();
        resetCoursesForTerm(term.termid);
        for (int i=0;i<courses.size();i++) {
            courses.get(i).termid=term.termid;
        }
        updateCourseList(courses);
    }

    public Term getTermWithCourses(int termid) {
        Term term = getTerm(termid);
        List<Course> courses = getCourseList(termid);
        term.setCourseList(courses);
        return term;
    }

    @Update
    public abstract void update(Term... terms);

    @Delete
    public abstract void delete(Term... terms);

    @Transaction
    @Query("Select * FROM Term")
    public abstract Term[] loadAll();

    @Query ("DELETE FROM Term")
    public abstract void truncateTerms();

    public static int getNextTermId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        int termIdKey=sharedPref.getInt("termidkey",1);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("termidkey",termIdKey+1);
        editor.commit();
        return termIdKey;
    }


}

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
import com.example.chelsel.scheduler.entity.Mentor;

import java.util.ArrayList;
import java.util.List;

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

    @Query("SELECT * FROM Course where courseid = :courseid")
    public abstract Course getCourse(int courseid);

    public void insertCourseWithLists(Course course) {
        List<Assessment> assessments = course.getAssessmentList();
        for (int i=0;i<assessments.size();i++) {
            assessments.get(i).courseid=course.courseid;
        }

        List<Mentor> mentors = course.getMentorList();
        for (int i=0;i<mentors.size();i++) {
            mentors.get(i).courseid=course.courseid;
        }

        updateAssessmentList(assessments);
        updateMentorList(mentors);
        insert(course);
        System.out.println("Inserted Course ID: "+course.courseid);
    }

    /* Disassociate any assessments from the course. */
    @Query("UPDATE Assessment SET courseid=0 WHERE courseid = :courseid")
    public abstract void resetAssessmentsForCourse(int courseid);

    /* Disassociate any mentors from the course. */
    @Query("UPDATE Mentor SET courseid=0 WHERE courseid = :courseid")
    public abstract void resetMentorsForCourse(int courseid);

    /* This function accepts a Course with embedded assessments and mentors, removes all
    associations for that course, then reassociates.
     */
    public void updateCourseWithLists(Course course) {
        List<Assessment> assessments = course.getAssessmentList();
        resetAssessmentsForCourse(course.courseid);
        for (int i=0;i<assessments.size();i++) {
            assessments.get(i).courseid=course.courseid;
        }

        List<Mentor> mentors = course.getMentorList();
        resetMentorsForCourse(course.courseid);
        for (int i=0;i<mentors.size();i++) {
            mentors.get(i).courseid=course.courseid;
        }

        updateAssessmentList(assessments);
        updateMentorList(mentors);
    }

    @Update
    public abstract void updateAssessmentList(List<Assessment> assessments);

    @Update
    public abstract void updateMentorList(List<Mentor> mentors);

}

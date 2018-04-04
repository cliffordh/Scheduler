package com.example.chelsel.scheduler.utilities;

import android.util.Log;

import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Term;

public class Logger {
    
    
    private static final String TAG = Logger.class.getName();
    
    public static void displayMentorsInLog(Mentor[] mentors) {
        
        if (mentors == null)
            return;
        
        for (Mentor mentor : mentors) {
            Log.d(TAG, "Mentor ID: " + mentor.mentorid + "Mentor name: " + mentor.name + ", mentor email: " + mentor.email + ", mentor phone: " + mentor.phone);
        }
    }

    public static void displayAssessmentsInLog(Assessment[] assessments) {

        if (assessments == null)
            return;

        for (Assessment assessment : assessments) {
            Log.d(TAG, "Assessment ID: " + assessment.assessmentid + "title: " + assessment.title + ", start: " + assessment.startDate + ", end: " + assessment.endDate);
        }
    }

/*
    public static void displayTermsInLog(Term[] terms) {

        if (terms == null)
            return;

        for (Term term : terms) {
            Log.d(TAG, "Term id: " + term.id + ", term name: " + term.firstName + " " + term.lastName);
        }
    }


    public static void displayCoursesInLog(Course[] courses) {

        if (courses == null)
            return;

        for (Course course : courses) {
            Log.d(TAG, "Course id: " + course.id + ", course name: " + course.name + ", course age: " + course.age + ", course owner: " + course.hoomanId);
        }
    }
    */
}

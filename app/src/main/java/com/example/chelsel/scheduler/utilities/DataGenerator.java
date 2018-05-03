package com.example.chelsel.scheduler.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.example.chelsel.scheduler.AppDataBase;
import com.example.chelsel.scheduler.dao.CourseDao;
import com.example.chelsel.scheduler.dao.TermDao;
import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DataGenerator {

    private static DataGenerator instance;
    private static AppDataBase dataBase;

    private Mentor[] mentors;
    private Course[] courses;
    private Assessment[] assessments;

    public static DataGenerator with(AppDataBase appDataBase) {

        if (dataBase == null)
            dataBase = appDataBase;

        if (instance == null)
            instance = new DataGenerator();

        return instance;
    }

    public void reset(Context context) {
        if (dataBase == null)
            return;
        // reset shared preferences
        SharedPreferences sharedPref = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();

        // reset tables
        dataBase.mentorDao().truncateMentors();
        dataBase.assessmentDao().truncateAssessments();
        dataBase.termDao().truncateTerms();
        dataBase.courseDao().truncateCourses();
    }

    public void generateMentors() {
        if (dataBase == null)
            return;

        mentors = new Mentor[2];
        mentors[0] = mentorInstance("Cliff","cliffordhelsel@me.com","5615031550");
        mentors[1] = mentorInstance("Lisa","lisamarie61283@gmail.com","5617067761");

        dataBase.mentorDao().insert(mentors);
    }

   public void generateTerms(Context context) {
        if (dataBase == null)
            return;

        Term[] terms = new Term[2];
        terms[0] = termInstance(context,"Term 1",new Date(),new Date());
        terms[0].setCourseList(Arrays.asList(courses[0],courses[1]));
        dataBase.termDao().insertTermWithCourses(terms[0]);
        terms[1] = termInstance(context,"Term 2",new Date(),new Date());
        terms[1].setCourseList(Arrays.asList(courses[2],courses[3]));
        dataBase.termDao().insertTermWithCourses(terms[1]);
    }

    public void generateCourses(Context context) {
        if (dataBase == null)
            return;

        courses = new Course[6];
        courses[0] = courseInstance(context,"Mobile App Development",new Date(),new Date());
        courses[1] = courseInstance(context,"Physics",new Date(),new Date());
        courses[2] = courseInstance(context,"Chemistry",new Date(),new Date());
        courses[3] = courseInstance(context,"Geography",new Date(),new Date());
        courses[4] = courseInstance(context,"Project Management",new Date(),new Date());
        courses[5] = courseInstance(context,"English I",new Date(),new Date());

        dataBase.courseDao().insert(courses);
    }

    public void generateAssessments() {
        if (dataBase == null)
            return;

        assessments = new Assessment[2];
        assessments[0] = assessmentInstance("Objective",new Date(), new Date());
        assessments[1] = assessmentInstance("Performance",new Date(), new Date());

        dataBase.assessmentDao().insert(assessments);
    }

    private Mentor mentorInstance(String name, String email, String phone) {
        Mentor mentor = new Mentor();

        mentor.name = name;
        mentor.email = email;
        mentor.phone = phone;

        return mentor;
    }

    private Term termInstance(Context context, String title, Date startDate, Date endDate) {
        Term term = new Term();

        term.title = title;
        term.startDate = startDate;
        term.endDate = endDate;

        term.termid = TermDao.getNextTermId(context);
        return term;
    }

    private Course courseInstance(Context context, String title, Date startDate, Date endDate) {
        Course course = new Course();

        course.title = title;
        course.startDate = startDate;
        course.endDate = endDate;
        course.courseid = CourseDao.getNextCourseId(context);

        return course;
    }

    private Assessment assessmentInstance(String title, Date startDate, Date endDate) {
        Assessment assessment = new Assessment();

        assessment.title = title;
        assessment.startDate = startDate;
        assessment.endDate = endDate;
        //assessment.endAlert = endAlert;
        //assessment.startAlert = startAlert;
        //assessment.goalDate = goalDate;

        return assessment;
    }

}

package com.example.chelsel.scheduler.utilities;

import com.example.chelsel.scheduler.AppDataBase;
import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Term;
import java.util.Date;

public class DataGenerator {

    private static DataGenerator instance;
    private static AppDataBase dataBase;


    public static DataGenerator with(AppDataBase appDataBase) {

        if (dataBase == null)
            dataBase = appDataBase;

        if (instance == null)
            instance = new DataGenerator();

        return instance;
    }

    public void reset() {
        if (dataBase == null)
            return;
        dataBase.mentorDao().truncateMentors();
        dataBase.assessmentDao().truncateAssessments();
    }

    public void generateMentors() {
        if (dataBase == null)
            return;

        Mentor[] mentors = new Mentor[2];
        mentors[0] = mentorInstance("Cliff","cliffordhelsel@me.com","5615031550");
        mentors[1] = mentorInstance("Lisa","lisamarie61283@gmail.com","5617067761");

        dataBase.mentorDao().insert(mentors);
    }

    public void generateAssessments() {
        if (dataBase == null)
            return;

        Assessment[] assessments = new Assessment[2];
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

/*
    public void generateCats() {

        if (dataBase == null)
            return;

        Course[] courses = new Course[5];
        courses[0] = catInstance("Tony", 3, 1);
        courses[1] = catInstance("Tiger", 1, 1);
        courses[2] = catInstance("Misty", 2, 2);
        courses[3] = catInstance("Oscar", 5, 3);
        courses[4] = catInstance("Puss", 4, 4);

        dataBase.courseDao().insert(courses);
    }


    private Course catInstance(String name, int age, int owner) {
        Course course = new Course();

        course.name = name;
        course.age = age;
        course.hoomanId = owner;

        return course;
    }
    */
}

package com.example.chelsel.scheduler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.chelsel.scheduler.dao.CourseDao;
import com.example.chelsel.scheduler.dao.MentorDao;
import com.example.chelsel.scheduler.dao.AssessmentDao;
import com.example.chelsel.scheduler.dao.TermDao;
import com.example.chelsel.scheduler.entity.Assessment;
import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Term;
import com.example.chelsel.scheduler.utilities.DateTypeConverter;

@Database(entities = {Term.class,
        Mentor.class,
        Course.class,
        Assessment.class},
        version = 1,
        exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract CourseDao courseDao();
    public abstract TermDao termDao();
    public abstract MentorDao mentorDao();
    public abstract AssessmentDao assessmentDao();

    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "scheduler-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    @Override
    public void clearAllTables() {
        instance.mentorDao().truncateMentors();
    }
}

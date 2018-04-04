package com.example.chelsel.scheduler.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.chelsel.scheduler.entity.Course;
import com.example.chelsel.scheduler.entity.Mentor;
import com.example.chelsel.scheduler.entity.Assessment;

import java.util.List;

public class CoursePojo {
    @Embedded
    public Course course;

    @Relation(parentColumn = "courseid",entityColumn = "courseid", entity = Mentor.class)
    public List<Mentor> mentors;

    @Relation(parentColumn = "courseid",entityColumn = "courseid", entity = Assessment.class)
    public List<Assessment> assessments;
}

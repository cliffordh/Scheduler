package com.example.chelsel.scheduler.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.example.chelsel.scheduler.entity.Term;
import com.example.chelsel.scheduler.entity.Course;
import java.util.List;

public class TermPojo {
    @Embedded
    public Term term;

    @Relation(parentColumn = "termid",entityColumn = "termid", entity = Course.class)
    public List<Course> courses;
}

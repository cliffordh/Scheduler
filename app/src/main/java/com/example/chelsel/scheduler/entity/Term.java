package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Term implements Serializable {

    @PrimaryKey (autoGenerate = true)
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Ignore
    private List<Course> courseList;
}
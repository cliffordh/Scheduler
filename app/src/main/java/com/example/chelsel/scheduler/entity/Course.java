package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int courseid;

    // foreign key, used by @Relation in pojo
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;
    public String status;
    public String notes;

    @Ignore
    private List<Mentor> mentorList;

    @Ignore
    private List<Assessment> assessmentList;
}

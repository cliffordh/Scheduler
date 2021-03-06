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
    // using Shared Preferences to maintain next primary key value to satisfy rubric
    @PrimaryKey
    public int courseid;

    // foreign key, used by @Relation in pojo
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;
    public String status;
    public String notes;

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public List<Mentor> getMentorList() {
        return mentorList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public void setMentorList(List<Mentor> mentorList) {
        this.mentorList = mentorList;
    }

    @Ignore
    private List<Mentor> mentorList;

    @Ignore
    private List<Assessment> assessmentList;
}

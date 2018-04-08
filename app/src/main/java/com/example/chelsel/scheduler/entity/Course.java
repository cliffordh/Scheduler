package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int courseid;

    // foreign key, used by @Relation in pojo
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;
    public int age;
    public String status;
    public String notes;
}

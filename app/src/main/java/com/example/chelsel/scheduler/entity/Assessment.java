package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Assessment {
    @PrimaryKey (autoGenerate = true)
    public int assessmentid;

    // foreign key defined by @relation in pojo
    public int courseid;

    public String title;
    public Date startDate;
    public Date endDate;
    public String status;
    public Date goalDate;
    public Boolean startAlert;
    public Boolean endAlert;
    public Boolean goalAlert;

}

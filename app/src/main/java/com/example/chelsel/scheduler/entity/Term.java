package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;

@Entity
public class Term {

    @PrimaryKey (autoGenerate = true)
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;

}
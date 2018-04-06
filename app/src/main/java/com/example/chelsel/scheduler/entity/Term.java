package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Term implements Serializable {

    @PrimaryKey (autoGenerate = true)
    public int termid;

    public String title;
    public Date startDate;
    public Date endDate;

}
package com.example.chelsel.scheduler.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Mentor implements Serializable {

    @PrimaryKey (autoGenerate = true)
    public int mentorid;

    // foreign key defined in Relation pojo
    public int courseid;

    public String name;
    public String phone;
    public String email;

}

package com.example.chelsel.scheduler.utilities;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * Created by chelsel on 4/1/18.
 *
 * Based on code from https://android.jlelse.eu/room-persistence-library-typeconverters-and-database-migration-3a7d68837d6c
 */

public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}

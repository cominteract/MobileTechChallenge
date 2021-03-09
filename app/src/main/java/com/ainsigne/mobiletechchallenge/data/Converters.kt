package com.ainsigne.mobiletechchallenge.data

import androidx.room.TypeConverter
import com.ainsigne.mobiletechchallenge.model.Search
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * [Converters] Type converters to allow Room to reference complex data types.
 */
class Converters {
    /**
     * converts calendar to datestamp
     */
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    /**
     * converts datestamp to calendar
     */
    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }


    /**
     * converts search list to liststring
     */
    @TypeConverter fun searchToList(value: List<Search>?) = Gson().toJson(value)

    /**
     * converts liststring to search list
     */
    @TypeConverter fun listToSearch(value: String) : List<Search> {

        val listType: Type = object : TypeToken<List<Search>>() {}.type
        return Gson().fromJson(value, listType)
    }

}
package com.slaytertv.firegym.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.slaytertv.firegym.data.model.BarDataItem

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromBarDataItemList(value: String): List<BarDataItem> {
        val listType = object : TypeToken<List<BarDataItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toBarDataItemList(list: List<BarDataItem>): String {
        return Gson().toJson(list)
    }
}
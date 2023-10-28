package com.slaytertv.firegym.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.slaytertv.firegym.data.model.BarDataItem
import com.slaytertv.firegym.data.model.DiaEntrenamiento

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromBarDataItemList(value: String?): List<BarDataItem>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<BarDataItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toBarDataItemList(list: List<BarDataItem>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }



    @TypeConverter
    fun fromDiaEntrenamientoList(value: String?): List<DiaEntrenamiento>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<DiaEntrenamiento>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toDiaEntrenamientoList(list: List<DiaEntrenamiento>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }

}
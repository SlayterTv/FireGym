package com.slaytertv.firegym.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.util.Converters

@Database(entities = [ExerciseEntity::class, CalendarioEntrenamientoEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun calendarioEntrenamientoDao(): CalendarioEntrenamientoDao
}


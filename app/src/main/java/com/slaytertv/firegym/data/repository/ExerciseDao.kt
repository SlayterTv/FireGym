package com.slaytertv.firegym.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.util.UiState

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: ExerciseEntity)


    @Query("SELECT * FROM exercise_table")
    fun getAllExercises(): LiveData<List<ExerciseEntity>>

    @Query("SELECT * FROM exercise_table WHERE category = :category")
    fun getExercisesByCategory(category: String):List<ExerciseEntity>


    @Query("SELECT * FROM exercise_table WHERE category = :category  AND isSelected = 1")
    fun getExercisesByCategoryisSelected(category: String):List<ExerciseEntity>


    @Update
    fun updateExercise(exercise: ExerciseEntity)
}
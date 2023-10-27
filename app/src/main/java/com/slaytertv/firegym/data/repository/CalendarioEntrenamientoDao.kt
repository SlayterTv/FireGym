package com.slaytertv.firegym.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity

@Dao
interface CalendarioEntrenamientoDao {
    @Insert
    fun insertCalendario(calendario: CalendarioEntrenamientoEntity)
    @Query("SELECT * FROM calendario_entrenamiento")
    fun getAllCalendarios(): List<CalendarioEntrenamientoEntity>

}

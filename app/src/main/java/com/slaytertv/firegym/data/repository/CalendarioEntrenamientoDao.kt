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
    @Query("SELECT MAX(id) FROM calendario_entrenamiento")
    fun getLastCalendarioId(): Long?

    @Query("SELECT * FROM calendario_entrenamiento")
    fun getAllCalendarios(): List<CalendarioEntrenamientoEntity>

    @Query("DELETE FROM calendario_entrenamiento WHERE id = :calendarioId")
    fun deleteCalendarioById(calendarioId: Long)

    @Query("UPDATE calendario_entrenamiento SET estado = :nuevoEstado WHERE id = :calendarioId")
    fun updateCalendarioEstadoById(calendarioId: Long, nuevoEstado: String)

    @Query("SELECT COUNT(*) FROM calendario_entrenamiento WHERE estado = 'actualmente'")
    fun countCalendariosConEstadoActual(): Int

    @Query("UPDATE calendario_entrenamiento SET estado = 'pendiente' WHERE estado = 'actualmente'")
    fun actualizarEstadosPendientes()
}

package com.slaytertv.firegym.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CalendarioEntrenamientoItem(
    val calendarioEntrenamiento: List<DiaEntrenamiento>
)

@Entity(tableName = "calendario_entrenamiento")
data class CalendarioEntrenamientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nomrutina: String,
    val cantsemana: String,
    val dias: List<String>?,
    val partesDelCuerpo: List<String>?,
    val calendarioEntrenamiento: List<DiaEntrenamiento>
)

data class DiaEntrenamiento(
    val dia: String,
    val fecha: String,
    val partesCuerpo: List<ParteCuerpo>
)

data class ParteCuerpo(
    val nombre: String,
    val ejercicios: List<Ejercicio>? = null
)

data class Ejercicio(
    val nombre: String,
    val series: Int,
    val repeticiones: Int,
    val peso: String,
    val progresion: String,
    val datosDiarios: List<DatosDiarios>? = null
)

data class DatosDiarios(
    val fecha: String,
    val series: Int,
    val repeticiones: Int,
    val peso: String
)

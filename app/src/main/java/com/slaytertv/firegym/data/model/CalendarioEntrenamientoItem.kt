package com.slaytertv.firegym.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class CalendarioEntrenamientoItem(
    val calendarioEntrenamiento: List<DiaEntrenamiento>
)

@Parcelize
@Entity(tableName = "calendario_entrenamiento")
data class CalendarioEntrenamientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nomrutina: String,
    val cantsemana: String,
    val dias: List<String>?,
    val partesDelCuerpo: List<String>?,
    val calendarioEntrenamiento: List<DiaEntrenamiento>
):Parcelable

@Parcelize
data class DiaEntrenamiento(
    val dia: String,
    val fecha: String,
    val partesCuerpo: List<ParteCuerpo>
):Parcelable

@Parcelize
data class ParteCuerpo(
    val nombre: String,
    val ejercicios: List<Ejercicio>? = null
):Parcelable

@Parcelize
data class Ejercicio(
    val nombre: String,
    val series: Int,
    val imagen:String,
    val repeticiones: Int,
    val peso: String,
    val progresion: String,
    val datosDiarios: List<DatosDiarios>? = null
):Parcelable
@Parcelize
data class DatosDiarios(
    val fecha: String,
    val series: Int,
    val repeticiones: Int,
    val peso: String
):Parcelable

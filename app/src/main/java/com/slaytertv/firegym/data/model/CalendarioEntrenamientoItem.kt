package com.slaytertv.firegym.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class CalendarioEntrenamientoItem(
    val calendarioEntrenamiento: List<DiaEntrenamiento>
)
//estado = (pendiente, finalizado,actualmente)
@Parcelize
@Entity(tableName = "calendario_entrenamiento")
data class CalendarioEntrenamientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nomrutina: String,
    val cantsemana: String,
    val dias: List<String>?,
    val partesDelCuerpo: List<String>?,
    val estado: String,
    val calendarioEntrenamiento: List<DiaEntrenamiento>
):Parcelable

@Parcelize
data class DiaEntrenamiento(
    val dia: String,
    var diaN:Int,
    val estado: String,
    val partesCuerpo: List<ParteCuerpo>
):Parcelable

@Parcelize
data class ParteCuerpo(
    val nombre: String,
    val estado: String ,
    var ejercicios: List<Ejercicio>? = null
):Parcelable

@Parcelize
data class Ejercicio(
    val id: String,
    val imageneje:String,
    val nombre:String,
    val tipo:String,
    val series: Int,
    val repeticiones: Int,
    val peso: String,
    val estado:String,
    val progresion: String
    ,    val datosDiarios: List<DatosDiarios>? = null
):Parcelable
@Parcelize
data class DatosDiarios(
    val series: Int,
    val repeticiones: Int,
    val peso: String,
    val estado:String,
    val timeacabado:String
):Parcelable


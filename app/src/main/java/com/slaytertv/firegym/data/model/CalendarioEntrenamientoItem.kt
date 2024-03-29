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
    var estado: String,
    val calendarioEntrenamiento: List<DiaEntrenamiento>
):Parcelable

@Parcelize
data class DiaEntrenamiento(
    val dia: String,
    var diaN:Int,
    var estado: String,
    val partesCuerpo: List<ParteCuerpo>
):Parcelable

@Parcelize
data class ParteCuerpo(
    val nombre: String,
    var estado: String ,
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
    var estado:String,
    val progresion: String
    ,    var datosDiarios: List<DatosDiarios>? = null
):Parcelable
@Parcelize
data class DatosDiarios(
    var series: Int,
    var repeticiones: Int,
    var peso: String,
    var estado:String,
    var timeacabado:String
):Parcelable


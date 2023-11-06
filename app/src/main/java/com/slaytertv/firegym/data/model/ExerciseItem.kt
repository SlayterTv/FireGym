package com.slaytertv.firegym.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseItem(
    val id: Int = 0,
    val category: String="",
    val link: String="",
    val foto: String="",
    val foto2: String="",
    val muscle_group: List<String>? = null,
    val spec_group: List<String>? = null,
    val bar_data: List<BarDataItem>? = null,
    var name: String =""
): Parcelable

@Parcelize
data class BarDataItem(
    val small_text: String="",
    val percentage_value: String=""
): Parcelable

@Parcelize
data class ExerciseListItem(
    val small_text: String="",
    val image: Int =0,
    val tag:String=""
): Parcelable


@Parcelize
@Entity(tableName = "exercise_table")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val link: String,
    val foto: String,
    val foto2: String,
    val muscle_group: List<String>? = null,
    val spec_group: List<String>? = null,
    val bar_data: List<BarDataItem>? = null,
    var name: String,
    var isSelected: Boolean = false // Nuevo campo para rastrear la selección
): Parcelable
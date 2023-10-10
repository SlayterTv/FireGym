package com.slaytertv.firegym.data.model

import android.os.Parcelable
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
    val image: String="",
    val tag:String=""
): Parcelable
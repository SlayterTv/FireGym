package com.slaytertv.firegym.data.model


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
)

data class BarDataItem(
    val small_text: String="",
    val percentage_value: String=""
)

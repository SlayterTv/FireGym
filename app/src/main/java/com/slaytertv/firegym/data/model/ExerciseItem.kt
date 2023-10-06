package com.slaytertv.firegym.data.model


data class ExerciseItem(
    val id: Int,
    val category: String,
    val link: String,
    val foto: String,
    val foto2: String,
    val muscle_group: List<String>,
    val spec_group: List<String>,
    val bar_data: List<BarDataItem>
)

data class BarDataItem(
    val small_text: String,
    val percentage_value: String
)

package com.slaytertv.firegym.data.repository

import com.slaytertv.firegym.data.model.ExerciseItem
import com.slaytertv.firegym.util.UiState

interface ExerciseRepository {
    fun getCateExcersice(Idcatego: String,result: (UiState<List<ExerciseItem>>) -> Unit)
    fun getAllExcersice(result: (UiState<List<ExerciseItem>>) -> Unit)
}
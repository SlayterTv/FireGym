package com.slaytertv.firegym.ui.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.data.repository.ExerciseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ViewModel() {
    val allExercises: LiveData<List<ExerciseEntity>> = exerciseDao.getAllExercises()

    fun insertExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            insertExerciseAsync(exercise)
        }
    }

    private suspend fun insertExerciseAsync(exercise: ExerciseEntity) {
        withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(exercise)
        }
    }
}

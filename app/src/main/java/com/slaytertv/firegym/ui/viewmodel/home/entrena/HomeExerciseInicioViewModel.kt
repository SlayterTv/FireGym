package com.slaytertv.firegym.ui.viewmodel.home.entrena

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.data.repository.ExerciseDao
import com.slaytertv.firegym.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeExerciseInicioViewModel@Inject constructor(
    //4 crear variable para tener a noterepository
    val repository: ExerciseRepository,
    private val exerciseDao: ExerciseDao
): ViewModel()  {

    private val _exerciselistroom = MutableLiveData<ExerciseEntity>()
    val exerciselistroom: LiveData<ExerciseEntity>
        get() = _exerciselistroom
    fun getExercisesById(ids:Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exercises = exerciseDao.getExercisesById(ids)
                withContext(Dispatchers.Main){
                    _exerciselistroom.value = exercises
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){

                }
            }
        }
    }



}
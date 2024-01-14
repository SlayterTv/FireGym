package com.slaytertv.firegym.ui.viewmodel.ownworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class OwnWorkoutViewModel@Inject constructor(
    private val exerciseDao:ExerciseDao
):ViewModel() {
    ///////////
    private val _exerciselistroom = MutableLiveData<List<ExerciseEntity>>()
    val exerciselistroom: LiveData<List<ExerciseEntity>>
        get() = _exerciselistroom
    fun getExercisesByCategoryisselec(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            val exercises = exerciseDao.getExercisesByCategoryisSelected(category)
            withContext(Dispatchers.Main){
                _exerciselistroom.value = exercises
            }
        }
    }


    //////////////

    private val _exercisealllistroom = MutableLiveData<List<ExerciseEntity>>()
    val exercisealllistroom: LiveData<List<ExerciseEntity>>
        get() = _exercisealllistroom
    fun getAllExercisesByCategoryisselec(){
        viewModelScope.launch(Dispatchers.IO) {
            val exercises = exerciseDao.getAllExercisesByCategoryisSelected()
            withContext(Dispatchers.Main){
                _exercisealllistroom.value = exercises
            }
        }
    }
    ////////////////////

}
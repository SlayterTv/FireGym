package com.slaytertv.firegym.ui.viewmodel.exerciselist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.data.model.ExerciseItem
import com.slaytertv.firegym.data.repository.ExerciseDao
import com.slaytertv.firegym.data.repository.ExerciseRepository
import com.slaytertv.firegym.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel@Inject constructor(
    //4 crear variable para tener a noterepository
    val repository: ExerciseRepository,
    private val exerciseDao: ExerciseDao
): ViewModel() {



    //obtener todos
    private val _exercisealllist = MutableLiveData<UiState<List<ExerciseItem>>>()
    val exercisealllist: LiveData<UiState<List<ExerciseItem>>>
        get() = _exercisealllist
    fun getAllExerciseList(){
        _exercisealllist.value = UiState.Loading
        repository.getAllExcersice { _exercisealllist.value = it }
    }

    //room obtener por categoria
    private val _exerciselistroom = MutableLiveData<List<ExerciseEntity>>()
    val exerciselistroom: LiveData<List<ExerciseEntity>>
        get() = _exerciselistroom
    fun getExercisesByCategory(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            val exercises = exerciseDao.getExercisesByCategory(category)
            withContext(Dispatchers.Main){
                _exerciselistroom.value = exercises
            }
        }
    }


}
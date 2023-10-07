package com.slaytertv.firegym.ui.viewmodel.exerciselist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slaytertv.firegym.data.model.ExerciseItem
import com.slaytertv.firegym.data.repository.ExerciseRepository
import com.slaytertv.firegym.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel@Inject constructor(
    //4 crear variable para tener a noterepository
    val repository: ExerciseRepository
): ViewModel() {
    //creamos una variable privada para la lista mutable en vivo con los items que llegan por noteitem
    private val _exerciselist = MutableLiveData<UiState<List<ExerciseItem>>>()
    //variable note con los items de noteitem
    val exerciselist: LiveData<UiState<List<ExerciseItem>>>
        get() = _exerciselist
    private val _updateExerciselist = MutableLiveData<UiState<String>>()
    val updateExerciselist: LiveData<UiState<String>>
        get() = _updateExerciselist
    ///////////////////////////////////////////////////////////////
//crear funcion con los items que se devolveran a getnote solo si es de nuestro usuario
    fun getExerciselist(category:String) {
        _exerciselist.value = UiState.Loading
        //a obtener notes le mandamkos nuestro usuario
        repository.getCateExcersice(category) { _exerciselist.value = it }
    }
    //funcion para catualiizar


}
package com.slaytertv.firegym.ui.viewmodel.ownworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.repository.CalendarioEntrenamientoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyWorkoutViewModel@Inject constructor(
    private val calendarioEntrenamientoDao: CalendarioEntrenamientoDao
): ViewModel() {


    fun insertCalendario(calendario: CalendarioEntrenamientoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            calendarioEntrenamientoDao.insertCalendario(calendario)
        }
    }


    //room obtener por categoria
    private val _myworkoutalllistroom = MutableLiveData<List<CalendarioEntrenamientoEntity>>()
    val myworkoutalllistroom: LiveData<List<CalendarioEntrenamientoEntity>>
        get() = _myworkoutalllistroom
    fun getCalendarioWorkout(){
        viewModelScope.launch(Dispatchers.IO) {
            val calendario = calendarioEntrenamientoDao.getAllCalendarios()
            withContext(Dispatchers.Main){
                _myworkoutalllistroom.value = calendario
            }
        }
    }


}
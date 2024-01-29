package com.slaytertv.firegym.ui.viewmodel.ownworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.data.repository.CalendarioEntrenamientoDao
import com.slaytertv.firegym.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyWorkoutViewModel@Inject constructor(
    private val calendarioEntrenamientoDao: CalendarioEntrenamientoDao
): ViewModel() {

    //sacar ultimo id
    private val _insertWorkout = MutableLiveData<UiState<CalendarioEntrenamientoEntity>>()
    val insertWorkout: LiveData<UiState<CalendarioEntrenamientoEntity>>
        get() = _insertWorkout

    fun insertCalendario(calendario: CalendarioEntrenamientoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lastId = calendarioEntrenamientoDao.getLastCalendarioId()
                val newId = lastId?.plus(1) ?: 0
                val calendarioConNuevoId = calendario.copy(id = newId)
                calendarioEntrenamientoDao.insertCalendario(calendarioConNuevoId)
                _insertWorkout.postValue(UiState.Sucess(calendarioConNuevoId))
            } catch (e: Exception) {
                _insertWorkout.postValue(UiState.Failure("Error al insertar: ${e.message}"))
            }
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

    //delete by iud
    private val _deleteWorkout = MutableLiveData<UiState<Unit>>()
    val deleteWorkout: LiveData<UiState<Unit>>
        get() = _deleteWorkout

    fun deletetCalendario(id:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                calendarioEntrenamientoDao.deleteCalendarioById(id)
                _deleteWorkout.postValue(UiState.Sucess(Unit))
            } catch (e: Exception) {
                _deleteWorkout.postValue(UiState.Failure("Error al eliminar: ${e.message}"))
            }
        }
    }

    //update estado by id
    private val _updateWorkout = MutableLiveData<UiState<Unit>>()
    val updateWorkout: LiveData<UiState<Unit>>
        get() = _updateWorkout

    fun updateCalendarioEstadoById(calendarioId: Long, nuevoEstado: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                calendarioEntrenamientoDao.updateCalendarioEstadoById(calendarioId, nuevoEstado)
                _updateWorkout.postValue(UiState.Sucess(Unit))
            } catch (e: Exception) {
                _updateWorkout.postValue(UiState.Failure("Error al actualizar estado: ${e.message}"))
            }
        }
    }


    //estado actual
    private val _hayCalendarioActual = MutableLiveData<Boolean>()
    val hayCalendarioActual: LiveData<Boolean>
        get() = _hayCalendarioActual

    fun verificarCalendarioActual() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = calendarioEntrenamientoDao.countCalendariosConEstadoActual()
            _hayCalendarioActual.postValue(count > 0)
        }
    }

    //actualizar esrtado actual
    private val _actualizarEstados = MutableLiveData<UiState<Unit>>()
    val actualizarEstados: LiveData<UiState<Unit>>
        get() = _actualizarEstados

    fun actualizarEstadosPendientes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                calendarioEntrenamientoDao.actualizarEstadosPendientes()
                _actualizarEstados.postValue(UiState.Sucess(Unit))
            } catch (e: Exception) {
                _actualizarEstados.postValue(UiState.Failure("Error al actualizar estados: ${e.message}"))
            }
        }
    }


}
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


    //////////


    private val _reiniciarDatosDiarios = MutableLiveData<UiState<Boolean>>()
    val reiniciarDatosDiarios: LiveData<UiState<Boolean>>
        get() = _reiniciarDatosDiarios

    fun reiniciarDatosDiarios(calendarioId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = calendarioEntrenamientoDao.obtenerCalendarioPorId(calendarioId)

                calendario?.let {
                    it.estado = "pendiente"
                    // Itera sobre cada día de entrenamiento en el calendario
                    val calendarioActualizado = it.copy(
                        calendarioEntrenamiento = it.calendarioEntrenamiento.map { dia ->
                            dia.copy(
                                estado = "pendiente"
                            )
                            // Itera sobre cada parte del cuerpo en el día de entrenamiento
                            val partesCuerpoActualizadas = dia.partesCuerpo.map { parteCuerpo ->
                                parteCuerpo.copy(
                                    estado = "pendiente"
                                )
                                // Itera sobre cada ejercicio en la parte del cuerpo
                                val ejerciciosActualizados = parteCuerpo.ejercicios?.map { ejercicio ->
                                    ejercicio.copy(
                                        estado = "pendiente"
                                    )
                                    // Reinicia los datos diarios del ejercicio
                                    val datosDiariosReiniciados = ejercicio.datosDiarios?.map { datos ->
                                        datos.copy(
                                            series = 0,
                                            repeticiones = 0,
                                            peso = "0",
                                            estado = "pendiente",
                                            timeacabado = "0.0"
                                        )
                                    } ?: emptyList()

                                    // Actualiza el ejercicio con los datos diarios reiniciados
                                    ejercicio.copy(datosDiarios = datosDiariosReiniciados)
                                } ?: emptyList()

                                // Actualiza la parte del cuerpo con los ejercicios actualizados
                                parteCuerpo.copy(ejercicios = ejerciciosActualizados)
                            }

                            // Actualiza el día de entrenamiento con las partes del cuerpo actualizadas
                            dia.copy(partesCuerpo = partesCuerpoActualizadas)
                        }
                    )

                    // Actualiza el calendario en la base de datos con los datos diarios reiniciados
                    calendarioEntrenamientoDao.actualizarCalendario(calendarioActualizado)
                    calendarioEntrenamientoDao.actualizarEstadosPendientes()
                    calendarioEntrenamientoDao.updateCalendarioEstadoById(calendarioId,"actualmente")

                    // Notifica éxito
                    withContext(Dispatchers.Main) {
                        _reiniciarDatosDiarios.value = UiState.Sucess(true)
                    }
                } ?: run {
                    // Manejo de error si no se encuentra el calendario
                    withContext(Dispatchers.Main) {
                        _reiniciarDatosDiarios.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _reiniciarDatosDiarios.value = UiState.Failure("Error al reiniciar los DatosDiarios: ${e.message}")
                }
            }
        }
    }


}
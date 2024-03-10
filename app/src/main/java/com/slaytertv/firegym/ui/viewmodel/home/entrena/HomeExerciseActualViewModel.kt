package com.slaytertv.firegym.ui.viewmodel.home.entrena

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slaytertv.firegym.data.model.DatosDiarios
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.repository.CalendarioEntrenamientoDao
import com.slaytertv.firegym.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeExerciseActualViewModel@Inject constructor(
    private val exerciseDao: CalendarioEntrenamientoDao
): ViewModel() {


    //actualizar recyclerview

    private val _datosDiariosUpdater = MutableLiveData<UiState<List<DatosDiarios>>>()
    val datosDiariosUpdater: LiveData<UiState<List<DatosDiarios>>>
        get() = _datosDiariosUpdater

    fun actualizarDatosDiariosDeEjercicio(calendarioId: Long, ejercicioId: String, indice: Int, nuevoDatoDiario: DatosDiarios, diaN: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = exerciseDao.obtenerCalendarioPorId(calendarioId)

                calendario.let {
                    // Encuentra el DiaEntrenamiento específico por diaN
                    val diaEntrenamientoEncontrado = it.calendarioEntrenamiento.find { diaEntrenamiento ->
                        diaEntrenamiento.diaN == diaN
                    }

                    diaEntrenamientoEncontrado?.let { diaEncontrado ->
                        // Busca el ejercicio específico por ID dentro de ese DiaEntrenamiento
                        val ejercicioActualizado = diaEncontrado.partesCuerpo
                            .flatMap { parteCuerpo -> parteCuerpo.ejercicios ?: emptyList() }
                            .find { ejercicio -> ejercicio.id == ejercicioId }

                        ejercicioActualizado?.let { ejercicioEncontrado ->
                            // Verifica si el índice es válido
                            if (indice >= 0 && indice < ejercicioEncontrado.datosDiarios!!.size) {
                                // Actualiza el dato específico en la lista
                                val datosDiariosActualizados = ejercicioEncontrado.datosDiarios!!.toMutableList()
                                datosDiariosActualizados[indice] = nuevoDatoDiario

                                // Actualiza el ejercicio dentro de la estructura anidada
                                val partesCuerpoActualizadas = diaEncontrado.partesCuerpo.map { parteCuerpo ->
                                    val ejerciciosActualizados = parteCuerpo.ejercicios?.map { ejercicio ->
                                        if (ejercicio.id == ejercicioId) ejercicio.copy(datosDiarios = datosDiariosActualizados)
                                        else ejercicio
                                    } ?: emptyList()

                                    parteCuerpo.copy(ejercicios = ejerciciosActualizados)
                                }

                                // Actualiza el DiaEntrenamiento con la estructura anidada modificada
                                val diaEntrenamientoActualizado = diaEncontrado.copy(partesCuerpo = partesCuerpoActualizadas)
                                val diasEntrenamientoActualizados = it.calendarioEntrenamiento.map { dia ->
                                    if (dia.diaN == diaN) diaEntrenamientoActualizado else dia
                                }

                                // Actualiza el calendario con la estructura anidada modificada
                                val calendarioActualizado = it.copy(calendarioEntrenamiento = diasEntrenamientoActualizados)
                                exerciseDao.actualizarCalendario(calendarioActualizado)

                                // Notifica éxito con la lista actualizada
                                withContext(Dispatchers.Main) {
                                    _datosDiariosUpdater.value = UiState.Sucess(datosDiariosActualizados)
                                }
                            } else {
                                // Manejo de error si el índice no es válido
                                withContext(Dispatchers.Main) {
                                    _datosDiariosUpdater.value = UiState.Failure("Índice de DatosDiarios no válido")
                                }
                            }
                        } ?: run {
                            // Manejo de error si no se encuentra el ejercicio específico
                            withContext(Dispatchers.Main) {
                                _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el ejercicio con ID: $ejercicioId")
                            }
                        }
                    } ?: run {
                        // Manejo de error si no se encuentra el DiaEntrenamiento específico
                        withContext(Dispatchers.Main) {
                            _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el DiaEntrenamiento con diaN: $diaN")
                        }
                    }
                } ?: run {
                    // Manejo de error si no se encuentra el calendario
                    withContext(Dispatchers.Main) {
                        _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _datosDiariosUpdater.value = UiState.Failure("Error al actualizar los DatosDiarios: ${e.message}")
                }
            }
        }
    }

    fun agregarDatosDiariosDeEjercicio(calendarioId: Long, ejercicioId: String, nuevoDatoDiario: DatosDiarios, diaN: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = exerciseDao.obtenerCalendarioPorId(calendarioId)

                calendario?.let {
                    // Encuentra el DiaEntrenamiento específico por diaN
                    val diaEntrenamientoEncontrado = it.calendarioEntrenamiento.find { diaEntrenamiento ->
                        diaEntrenamiento.diaN == diaN
                    }

                    diaEntrenamientoEncontrado?.let { diaEncontrado ->
                        // Busca el ejercicio específico por ID dentro de ese DiaEntrenamiento
                        val ejercicioActualizado = diaEncontrado.partesCuerpo
                            .flatMap { parteCuerpo -> parteCuerpo.ejercicios ?: emptyList() }
                            .find { ejercicio -> ejercicio.id == ejercicioId }

                        ejercicioActualizado?.let { ejercicioEncontrado ->
                            // Agrega el nuevo dato diario con la serie correlativa
                            val serieCorrelativa = ejercicioEncontrado.datosDiarios?.size ?: 0
                            val datosDiariosActualizados = ejercicioEncontrado.datosDiarios?.toMutableList() ?: mutableListOf()
                            nuevoDatoDiario.series = serieCorrelativa + 1
                            datosDiariosActualizados.add(nuevoDatoDiario)

                            // Actualiza el ejercicio dentro de la estructura anidada
                            val partesCuerpoActualizadas = diaEncontrado.partesCuerpo.map { parteCuerpo ->
                                val ejerciciosActualizados = parteCuerpo.ejercicios?.map { ejercicio ->
                                    if (ejercicio.id == ejercicioId) ejercicio.copy(datosDiarios = datosDiariosActualizados)
                                    else ejercicio
                                } ?: emptyList()

                                parteCuerpo.copy(ejercicios = ejerciciosActualizados)
                            }

                            // Actualiza el DiaEntrenamiento con la estructura anidada modificada
                            val diaEntrenamientoActualizado = diaEncontrado.copy(partesCuerpo = partesCuerpoActualizadas)
                            val diasEntrenamientoActualizados = it.calendarioEntrenamiento.map { dia ->
                                if (dia.diaN == diaN) diaEntrenamientoActualizado else dia
                            }

                            // Actualiza el calendario con la estructura anidada modificada
                            val calendarioActualizado = it.copy(calendarioEntrenamiento = diasEntrenamientoActualizados)
                            exerciseDao.actualizarCalendario(calendarioActualizado)

                            // Notifica éxito con la lista actualizada
                            withContext(Dispatchers.Main) {
                                _datosDiariosUpdater.value = UiState.Sucess(datosDiariosActualizados)
                            }
                        } ?: run {
                            // Manejo de error si no se encuentra el ejercicio específico
                            withContext(Dispatchers.Main) {
                                _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el ejercicio con ID: $ejercicioId")
                            }
                        }
                    } ?: run {
                        // Manejo de error si no se encuentra el DiaEntrenamiento específico
                        withContext(Dispatchers.Main) {
                            _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el DiaEntrenamiento con diaN: $diaN")
                        }
                    }
                } ?: run {
                    // Manejo de error si no se encuentra el calendario
                    withContext(Dispatchers.Main) {
                        _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _datosDiariosUpdater.value = UiState.Failure("Error al agregar los DatosDiarios: ${e.message}")
                }
            }
        }
    }

    // Quitar el último DatosDiarios de un ejercicio
    fun quitarDatosDiariosDeEjercicio(calendarioId: Long, ejercicioId: String, diaN: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = exerciseDao.obtenerCalendarioPorId(calendarioId)

                calendario?.let {
                    // Encuentra el DiaEntrenamiento específico por diaN
                    val diaEntrenamientoEncontrado = it.calendarioEntrenamiento.find { diaEntrenamiento ->
                        diaEntrenamiento.diaN == diaN
                    }

                    diaEntrenamientoEncontrado?.let { diaEncontrado ->
                        // Busca el ejercicio específico por ID dentro de ese DiaEntrenamiento
                        val ejercicioActualizado = diaEncontrado.partesCuerpo
                            .flatMap { parteCuerpo -> parteCuerpo.ejercicios ?: emptyList() }
                            .find { ejercicio -> ejercicio.id == ejercicioId }

                        ejercicioActualizado?.let { ejercicioEncontrado ->
                            // Verifica si hay DatosDiarios para quitar
                            val datosDiariosActualizados = ejercicioEncontrado.datosDiarios?.toMutableList() ?: mutableListOf()
                            if (datosDiariosActualizados.isNotEmpty()) {
                                // Elimina el último elemento en DatosDiarios
                                datosDiariosActualizados.removeAt(datosDiariosActualizados.size - 1)

                                // Actualiza el ejercicio dentro de la estructura anidada
                                val partesCuerpoActualizadas = diaEncontrado.partesCuerpo.map { parteCuerpo ->
                                    val ejerciciosActualizados = parteCuerpo.ejercicios?.map { ejercicio ->
                                        if (ejercicio.id == ejercicioId) ejercicio.copy(datosDiarios = datosDiariosActualizados)
                                        else ejercicio
                                    } ?: emptyList()

                                    parteCuerpo.copy(ejercicios = ejerciciosActualizados)
                                }

                                // Actualiza el DiaEntrenamiento con la estructura anidada modificada
                                val diaEntrenamientoActualizado = diaEncontrado.copy(partesCuerpo = partesCuerpoActualizadas)
                                val diasEntrenamientoActualizados = it.calendarioEntrenamiento.map { dia ->
                                    if (dia.diaN == diaN) diaEntrenamientoActualizado else dia
                                }

                                // Actualiza el calendario con la estructura anidada modificada
                                val calendarioActualizado = it.copy(calendarioEntrenamiento = diasEntrenamientoActualizados)
                                exerciseDao.actualizarCalendario(calendarioActualizado)

                                // Notifica éxito con la lista actualizada
                                withContext(Dispatchers.Main) {
                                    _datosDiariosUpdater.value = UiState.Sucess(datosDiariosActualizados)
                                }
                            } else {
                                // Manejo de error si no hay DatosDiarios para quitar
                                withContext(Dispatchers.Main) {
                                    _datosDiariosUpdater.value = UiState.Failure("No hay DatosDiarios para quitar en el ejercicio con ID: $ejercicioId")
                                }
                            }
                        } ?: run {
                            // Manejo de error si no se encuentra el ejercicio específico
                            withContext(Dispatchers.Main) {
                                _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el ejercicio con ID: $ejercicioId")
                            }
                        }
                    } ?: run {
                        // Manejo de error si no se encuentra el DiaEntrenamiento específico
                        withContext(Dispatchers.Main) {
                            _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el DiaEntrenamiento con diaN: $diaN")
                        }
                    }
                } ?: run {
                    // Manejo de error si no se encuentra el calendario
                    withContext(Dispatchers.Main) {
                        _datosDiariosUpdater.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _datosDiariosUpdater.value = UiState.Failure("Error al quitar los DatosDiarios: ${e.message}")
                }
            }
        }
    }




    ///////////////////////////////////////////////////////////////////////buscar diaespecifico
    private val _diaEntrenamientoActual = MutableLiveData<UiState<DiaEntrenamiento>>()
    val diaEntrenamientoActual: LiveData<UiState<DiaEntrenamiento>>
        get() = _diaEntrenamientoActual
    fun recuperarDiaEntreEspecifico(calendarioId: Long, diaN: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = exerciseDao.obtenerCalendarioPorId(calendarioId)

                withContext(Dispatchers.Main) {
                    calendario?.let {
                        // Encuentra el DiaEntrenamiento específico por diaN
                        val diaEntrenamientoEspecifico = it.calendarioEntrenamiento.find { diaEntrenamiento ->
                            diaEntrenamiento.diaN == diaN
                        }

                        diaEntrenamientoEspecifico?.let { diaEncontrado ->
                            // Notifica éxito con el DiaEntrenamiento encontrado
                            _diaEntrenamientoActual.value = UiState.Sucess(diaEncontrado)
                        } ?: run {
                            // Manejo de error si no se encuentra el DiaEntrenamiento específico
                            _diaEntrenamientoActual.value = UiState.Failure("No se pudo encontrar el DiaEntrenamiento con diaN: $diaN")
                        }
                    } ?: run {
                        // Manejo de error si no se encuentra el calendario
                        _diaEntrenamientoActual.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _diaEntrenamientoActual.value = UiState.Failure("Error al recuperar el DiaEntrenamiento: ${e.message}")
                }
            }
        }
    }



    ///finalizar dia


    private val _actualizarDiaEntrenamiento = MutableLiveData<UiState<Boolean>>()
    val actualizarDiaEntrenamiento: LiveData<UiState<Boolean>>
        get() = _actualizarDiaEntrenamiento
    fun finalizarDiaEntrenamiento(calendarioId: Long, diaN: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Obtén el calendario por ID
                val calendario = exerciseDao.obtenerCalendarioPorId(calendarioId)

                calendario?.let {
                    // Encuentra el DiaEntrenamiento específico por diaN
                    val diaEntrenamientoEncontrado = it.calendarioEntrenamiento.find { diaEntrenamiento ->
                        diaEntrenamiento.diaN == diaN
                    }

                    diaEntrenamientoEncontrado?.let { diaEncontrado ->
                        // Actualiza el campo estado a "finalizado"
                        val diaEntrenamientoFinalizado = diaEncontrado.copy(estado = "finalizado")

                        // Actualiza el calendario con el DiaEntrenamiento finalizado
                        val calendarioActualizado = it.copy(
                            calendarioEntrenamiento = it.calendarioEntrenamiento.map { dia ->
                                if (dia.diaN == diaN) diaEntrenamientoFinalizado else dia
                            }
                        )

                        // Verifica si todos los días, incluyendo el actual, están finalizados
                        val todosFinalizados = calendarioActualizado.calendarioEntrenamiento.all { dia ->
                            dia.estado == "finalizado"
                        }

                        // Actualiza el calendario en la base de datos
                        exerciseDao.actualizarCalendario(calendarioActualizado)

                        // Notifica éxito
                        if (todosFinalizados) {
                            // Muestra un Toast indicando que todos los días están finalizados
                            // Puedes reemplazar este Toast con la lógica que desees
                            exerciseDao.updateCalendarioEstadoById(calendarioId,"finalizado")
                            withContext(Dispatchers.Main){
                                _actualizarDiaEntrenamiento.value = UiState.Sucess(true)
                            }
                        } else {
                            // Finaliza el día actual si no todos los días están finalizados
                            _actualizarDiaEntrenamiento.value = UiState.Sucess(true)
                        }
                    } ?: run {
                        // Manejo de error si no se encuentra el DiaEntrenamiento específico
                        withContext(Dispatchers.Main) {
                            _actualizarDiaEntrenamiento.value = UiState.Failure("No se pudo encontrar el DiaEntrenamiento con diaN: $diaN")
                        }
                    }
                } ?: run {
                    // Manejo de error si no se encuentra el calendario
                    withContext(Dispatchers.Main) {
                        _actualizarDiaEntrenamiento.value = UiState.Failure("No se pudo encontrar el calendario")
                    }
                }
            } catch (e: Exception) {
                // Manejo de error
                withContext(Dispatchers.Main) {
                    _actualizarDiaEntrenamiento.value = UiState.Failure("Error al finalizar el DíaEntrenamiento: ${e.message}")
                }
            }
        }
    }




}
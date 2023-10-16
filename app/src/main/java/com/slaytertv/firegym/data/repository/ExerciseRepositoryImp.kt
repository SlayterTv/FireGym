package com.slaytertv.firegym.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.slaytertv.firegym.data.model.ExerciseItem
import com.slaytertv.firegym.util.UiState


class ExerciseRepositoryImp(
    val database: FirebaseDatabase
):ExerciseRepository{
    override fun getCateExcersice(Idcatego: String, result: (UiState<List<ExerciseItem>>) -> Unit) {

        val reference = database.getReference("ejercicios")
        val query = reference.orderByChild("category").equalTo(Idcatego)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exercises = mutableListOf<ExerciseItem>()
                for (exerciseSnapshot in dataSnapshot.children) {
                    // Obtener los datos de cada ejercicio
                    val ejercicio = exerciseSnapshot.getValue(ExerciseItem::class.java)
                    if (ejercicio != null) {
                        ejercicio.name = exerciseSnapshot.key.toString()
                        exercises.add(ejercicio)
                    }
                }
                result.invoke(UiState.Sucess(exercises))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores si es necesario
                println("Error: ${databaseError.message}")
            }
        })
    }

    override fun getAllExcersice(result: (UiState<List<ExerciseItem>>) -> Unit) {
        val reference = database.getReference("ejercicios")
        val query = reference.orderByChild("category")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exercises = mutableListOf<ExerciseItem>()
                for (exerciseSnapshot in dataSnapshot.children) {
                    // Obtener los datos de cada ejercicio
                    val ejercicio = exerciseSnapshot.getValue(ExerciseItem::class.java)
                    if (ejercicio != null) {
                        ejercicio.name = exerciseSnapshot.key.toString()
                        exercises.add(ejercicio)
                    }
                }
                result.invoke(UiState.Sucess(exercises))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar errores si es necesario
                println("Error: ${databaseError.message}")
            }
        })
    }


}
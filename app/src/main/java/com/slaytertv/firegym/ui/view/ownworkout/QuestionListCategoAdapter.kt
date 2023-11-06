package com.slaytertv.firegym.ui.view.ownworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.databinding.ItemExerciselistworkoutBinding

class QuestionListCategoAdapter(
    private val onItemClick: (Int, ExerciseEntity) -> Unit
) : RecyclerView.Adapter<QuestionListCategoAdapter.ViewHolder>() {
    private var list: MutableList<ExerciseEntity> = arrayListOf()
    private val selectedItems: MutableList<Ejercicio> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = ItemExerciselistworkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<ExerciseEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemExerciselistworkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseEntity) {
            binding.exerciseName.text = item.name
            Glide.with(binding.root).load(item.foto).transform(
                RoundedCorners(25)
            ).into(binding.exerciseImage)

            binding.exerciseImage.setOnClickListener {
                onItemClick.invoke(adapterPosition, item)
            }



            binding.add.setOnClickListener {
                val seriesEditText = binding.seriescant.text.toString()
                val repEditText = binding.repcant.text.toString()
                val pesoEditText = binding.peso.text.toString()

                val ejercicio = Ejercicio(
                    nombre = item.name,
                    series = seriesEditText.toIntOrNull() ?: 0, // Manejamos errores de conversión
                    imagen = item.foto.toString(),
                    repeticiones = repEditText.toIntOrNull() ?: 0, // Manejamos errores de conversión
                    peso = pesoEditText,
                    progresion = ""
                )
                if(seriesEditText.isNullOrEmpty() && repEditText.isNullOrEmpty() && pesoEditText.isNullOrEmpty()){
                    Toast.makeText(
                        binding.root.context,
                        "rellene todos los datos (Series, Repeticiones y Peso )",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    // Cambia el estado de selección del elemento y actualiza la vista
                    item.isSelected = !item.isSelected
                    onItemClick.invoke(adapterPosition, item)
                    // Cambia el color de fondo del elemento si está seleccionado
                    if (item.isSelected) {
                        binding.exerciseImage.alpha = 0.5f // 0.5f representa la opacidad, puedes ajustar el valor según tus preferencias
                        selectedItems.add(ejercicio)
            //            println(selectedItems)
                    } else {
                        // Restablece la opacidad original de la imagen si no está seleccionado
                        binding.exerciseImage.alpha = 1.0f
                        selectedItems.remove(ejercicio)
          //              println(selectedItems)
                    }
                }
            }
        }
    }

    fun getSelectedItems(): List<Ejercicio> {
        //println("lo q se manda $selectedItems")
        return selectedItems
    }
}

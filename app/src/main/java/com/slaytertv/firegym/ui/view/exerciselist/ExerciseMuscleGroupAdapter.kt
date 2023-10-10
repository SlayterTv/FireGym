package com.slaytertv.firegym.ui.view.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.databinding.ItemMuscleGroupBinding

class MuscleGroupAdapter(private val muscleGroups: List<String>) :
    RecyclerView.Adapter<MuscleGroupAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMuscleGroupBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMuscleGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val muscleGroup = muscleGroups[position]
        holder.binding.textViewMuscleGroup.text = muscleGroup
    }

    override fun getItemCount(): Int {
        return muscleGroups.size
    }
}

package com.slaytertv.firegym.ui.view.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.BarDataItem
import com.slaytertv.firegym.databinding.ItemProgressBarBinding

class ExerciseProgressBarAdapter(private val barDataItems: List<BarDataItem>) :
    RecyclerView.Adapter<ExerciseProgressBarAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProgressBarBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProgressBarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barDataItem = barDataItems[position]
        val percentage = barDataItem.percentage_value.toInt()
        holder.binding.progressBar.progress = percentage
        holder.binding.textname.setText(barDataItem.small_text)
    }

    override fun getItemCount(): Int {
        return barDataItems.size
    }
}
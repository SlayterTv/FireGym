package com.slaytertv.firegym.ui.view.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.databinding.ItemSpecGroupBinding

class SpecsGroupAdapter(private val specsGroups: List<String>) :
    RecyclerView.Adapter<SpecsGroupAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSpecGroupBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpecGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specGroup = specsGroups[position]
        holder.binding.textViewSpecsGroup.text = specGroup
    }

    override fun getItemCount(): Int {
        return specsGroups.size
    }
}

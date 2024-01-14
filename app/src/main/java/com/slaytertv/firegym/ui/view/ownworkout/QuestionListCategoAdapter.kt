package com.slaytertv.firegym.ui.view.ownworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.databinding.ItemExerciselistworkoutBinding

class QuestionListCategoAdapter(

) : RecyclerView.Adapter<QuestionListCategoAdapter.ViewHolder>() {
    private var list: MutableList<ExerciseEntity> = arrayListOf()

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
            binding.seriescant.text = item.serie.toString()
            binding.repcant.text = item.repeticion.toString()
            binding.peso.text = item.peso.toString()
            Glide.with(binding.root).load(item.foto).transform(
                RoundedCorners(25)
            ).into(binding.exerciseImage)


        }
    }


}

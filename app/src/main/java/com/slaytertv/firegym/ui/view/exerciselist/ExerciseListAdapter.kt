package com.slaytertv.firegym.ui.view.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.data.model.ExerciseItem
import com.slaytertv.firegym.databinding.ItemExerciselistBinding

class ExerciseListAdapter(
    val onItemClicked: (Int, ExerciseItem) -> Unit,
) :RecyclerView.Adapter<ExerciseListAdapter.MyViewHolder>() {
    private var list: MutableList<ExerciseItem> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemExerciselistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
    fun updateList(list: MutableList<ExerciseItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class MyViewHolder(val binding: ItemExerciselistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseItem){
            binding.exerciseName.setText(item.name)
            Glide.with(binding.root).load(item.foto) .transform(
                RoundedCorners(25)
            ).into(binding.exerciseImage)
            binding.exerciseImage.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }


}
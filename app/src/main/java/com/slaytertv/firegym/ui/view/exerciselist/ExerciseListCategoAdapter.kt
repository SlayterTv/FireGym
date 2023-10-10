package com.slaytertv.firegym.ui.view.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.data.model.ExerciseListItem
import com.slaytertv.firegym.databinding.ItemExerciselistCategoryBinding

class ExerciseListCategoAdapter(
    val onItemClicked: (Int, ExerciseListItem) -> Unit,
) : RecyclerView.Adapter<ExerciseListCategoAdapter.MyViewHolder>() {
    private var list: MutableList<ExerciseListItem> = arrayListOf(
        ExerciseListItem("Neck","","neck"),
        ExerciseListItem("Trapezius","","trapezius"),
        ExerciseListItem("Shoulders","","shoulders"),
        ExerciseListItem("Chest","","chest"),
        ExerciseListItem("Back/Wing","","back"),
        ExerciseListItem("Erector Spinae","","erector-spinae"),
        ExerciseListItem("Biceps","","biceps"),
        ExerciseListItem("Triceps","","triceps"),
        ExerciseListItem("Forearm","","forearm"),
        ExerciseListItem("Abs/Core","","abs"),
        ExerciseListItem("Leg","","leg"),
        ExerciseListItem("Calf","","calf"),
        ExerciseListItem("Hips","","hip"),
        ExerciseListItem("Cardio","","cardio"),
        ExerciseListItem("Full Body","","full-body"),
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemExerciselistCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
    fun updateList(list: MutableList<ExerciseListItem>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class MyViewHolder(val binding: ItemExerciselistCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseListItem){
            binding.name.setText(item.small_text)
            Glide.with(binding.root).load(item.image) .transform(
                RoundedCorners(25)
            ).into(binding.image)
            binding.image.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}
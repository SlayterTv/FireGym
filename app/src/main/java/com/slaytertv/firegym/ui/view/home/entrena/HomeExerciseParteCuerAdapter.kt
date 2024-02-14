package com.slaytertv.firegym.ui.view.home.entrena

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.ItemExerciserealizaBinding


class HomeExerciseParteCuerAdapter(
    val onItemClicked: (Int, Ejercicio) -> Unit
): RecyclerView.Adapter<HomeExerciseParteCuerAdapter.MyViewHolder>()  {
    private var list: MutableList<Ejercicio> = arrayListOf()


    inner class MyViewHolder(val binding: ItemExerciserealizaBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Ejercicio){
            binding.partecuerpohome.text = item.nombre
            binding.partecuerpohome.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemExerciserealizaBinding .inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list: List<Ejercicio>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
}
package com.slaytertv.firegym.ui.view.home.entrena

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.DatosDiarios

import com.slaytertv.firegym.databinding.ItemRecyclerdatosdiariosBinding

class HomeExerciseParteCRecyclerAdapter(
    val onItemClicked: (Int, DatosDiarios) -> Unit
): RecyclerView.Adapter<HomeExerciseParteCRecyclerAdapter.MyViewHolder>(){
    private var list: MutableList<DatosDiarios> = arrayListOf()

    inner class MyViewHolder(val binding: ItemRecyclerdatosdiariosBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DatosDiarios){
            binding.set.text = item.series.toString()
            binding.set.setOnClickListener {
                onItemClicked.invoke(adapterPosition,item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRecyclerdatosdiariosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list: List<DatosDiarios>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}
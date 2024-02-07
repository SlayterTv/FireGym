package com.slaytertv.firegym.ui.view.home.entrena

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.ItemEjercihomenetreiniBinding


class HomeEntreIniejerciciAdapter(
    val onItemClicked: (Int, Ejercicio) -> Unit
): RecyclerView.Adapter<HomeEntreIniejerciciAdapter.MyViewHolder>() {
    private var list: MutableList<Ejercicio> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =ItemEjercihomenetreiniBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
    fun updateList(list: MutableList<Ejercicio>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }



    inner class MyViewHolder(val binding: ItemEjercihomenetreiniBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Ejercicio){
            binding.tdiashomeentreini.text = item.id
            binding.tdiashomeentreini.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}
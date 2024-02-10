package com.slaytertv.firegym.ui.view.home.entrena

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.databinding.ItemDiahomeentreiniBinding

class HomeEntreIniDiasAdapter(
    val onItemClicked: (Int,DiaEntrenamiento) -> Unit
): RecyclerView.Adapter<HomeEntreIniDiasAdapter.MyViewHolder>() {
    private var list: MutableList<DiaEntrenamiento> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemDiahomeentreiniBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
    fun updateList(list: List<DiaEntrenamiento>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemDiahomeentreiniBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DiaEntrenamiento){

            when(item.estado){
                "pendiente" -> {
                    binding.tdiashomeentreini.setBackgroundColor(Color.TRANSPARENT)
                }
                "actualmente" -> {
                    binding.tdiashomeentreini.setBackgroundColor(Color.GREEN)
                }
                "finalizado" -> {
                    binding.tdiashomeentreini.setBackgroundColor(Color.RED)
                }
            }
            binding.tdiashomeentreini.text = item.dia
            binding.tNdiashomeentreini.text = item.diaN.toString()
            binding.tdiashomeentreini.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }

}
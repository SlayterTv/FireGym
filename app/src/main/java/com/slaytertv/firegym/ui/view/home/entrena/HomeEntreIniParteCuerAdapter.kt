package com.slaytertv.firegym.ui.view.home.entrena

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.ParteCuerpo
import com.slaytertv.firegym.databinding.ItemPartecuwehomeentreiniBinding

class HomeEntreIniParteCuerAdapter (
    val onItemClicked: (Int, ParteCuerpo) -> Unit,
    val onCheckBoxClicked: (Int, ParteCuerpo) -> Unit
):RecyclerView.Adapter<HomeEntreIniParteCuerAdapter.MyViewHolder>()  {
    private var list: MutableList<ParteCuerpo> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemPartecuwehomeentreiniBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list: List<ParteCuerpo>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class MyViewHolder(val binding: ItemPartecuwehomeentreiniBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ParteCuerpo){
            binding.partecuerpohome.text = item.nombre
            binding.partecuerpohome.setOnClickListener {
                onItemClicked.invoke(adapterPosition,item)
            }
            binding.iniciarhome.setOnClickListener {
                onCheckBoxClicked.invoke(adapterPosition,item)
            }
        }
    }

}
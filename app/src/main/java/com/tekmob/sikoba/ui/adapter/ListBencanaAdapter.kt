package com.tekmob.sikoba.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tekmob.sikoba.databinding.BencanaCardBinding
import com.tekmob.sikoba.model.Bencana

class ListBencanaAdapter(private val listBencana : List<Bencana>) : RecyclerView.Adapter<ListBencanaAdapter.ListBencanaHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    inner class ListBencanaHolder(var binding : BencanaCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBencanaHolder {
        val binding = BencanaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListBencanaHolder(binding)
    }

    override fun onBindViewHolder(holder: ListBencanaHolder, position: Int) {
        val bencana = listBencana[position]
        holder.apply {
            binding.apply {
                nama.text = bencana.nama
                lokasi.text = bencana.lokasi
                tanggalKejadian.text = bencana.tanggalKejadian.toString().slice(IntRange(0,9))
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    listBencana[adapterPosition]
                )
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listBencana.size

    interface OnItemClickCallback{
        fun onItemClicked(data : Bencana)
    }
}
package com.tekmob.sikoba.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tekmob.sikoba.R

import com.tekmob.sikoba.databinding.KorbanTersimpanCardBinding
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanLocal

class PencarianTerimpanAdapter(private val context: Context) : RecyclerView.Adapter<PencarianTerimpanAdapter.PencarianTersimpanViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback
    private lateinit var onDeleteClickCallback: OnItemClickCallback
    private var listKorban = ArrayList<KorbanLocal>()

    fun setListKorban(listKorban : List<KorbanLocal>) {
        val diffCallback = PencarianTersimpanDiffCallback(this.listKorban, listKorban)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listKorban.clear()
        this.listKorban.addAll(listKorban)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PencarianTersimpanViewHolder(
        var binding: KorbanTersimpanCardBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PencarianTersimpanViewHolder {
        val binding = KorbanTersimpanCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PencarianTersimpanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PencarianTersimpanViewHolder, position: Int) {
        val korban = listKorban[position]
        holder.apply {
            binding.apply {
                nama.text = korban.nama
                tempatTanggalLahir.text = context.getString(R.string.tempat_tanggal_lahir, (if (korban.tempatLahir == "") "Jakarta" else korban.tempatLahir), korban.tanggalLahir?.slice(IntRange(0,9)))
                namaIbuKandung.text = if (korban.namaIbuKandung == "") "Fulanah" else korban.namaIbuKandung
                kondisi.text = korban.kondisi
                Glide.with(holder.itemView.context)
                    .load(korban.foto)
                    .into(holder.binding.foto)
                deleteButton.setOnClickListener{
                    onDeleteClickCallback.onItemClicked(
                        listKorban[adapterPosition]
                    )
                }
            }
            itemView.setOnClickListener{
                onItemClickCallback.onItemClicked(
                    listKorban[adapterPosition]
                )
            }
        }
    }

    override fun getItemCount(): Int = listKorban.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDeleteClickCallback(onDeleteClickCallback: OnItemClickCallback){
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data : KorbanLocal)
    }

}
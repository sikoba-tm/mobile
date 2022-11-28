package com.tekmob.sikoba.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.tekmob.sikoba.R
import com.tekmob.sikoba.databinding.KorbanCardBinding
import com.tekmob.sikoba.model.Korban

class ListKorbanAdapter(private val listKorban : List<Korban>, private val context: Context) :
RecyclerView.Adapter<ListKorbanAdapter.ListKorbanHolder>()
{
    inner class ListKorbanHolder(var binding: KorbanCardBinding) :
            RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback : OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKorbanHolder {
        val binding = KorbanCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListKorbanHolder(binding)
    }

    override fun onBindViewHolder(holder: ListKorbanHolder, position: Int) {
        val korban = listKorban[position]
        holder.apply {
            binding.apply {
                nama.text = korban.nama
                tempatTanggalLahir.text = context.getString(R.string.tempat_tanggal_lahir, (if (korban.tempatLahir == "") "Jakarta" else korban.tempatLahir), korban.tanggalLahir?.slice(IntRange(0,9)))
                namaIbuKandung.text = if (korban.namaIbuKandung == "") "Fulanah" else korban.namaIbuKandung
                kondisi.text = korban.kondisi
                Glide.with(holder.itemView.context)
                    .load(korban.foto + "?c=" + System.currentTimeMillis())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions().signature(ObjectKey("updated")))
                    .into(holder.binding.foto)
            }
            itemView.setOnClickListener{
                onItemClickCallback.onItemClicked(
                    listKorban[adapterPosition]
                )
            }
        }
    }

    override fun getItemCount() : Int = listKorban.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data : Korban)
    }

    companion object {
        private const val TAG = "ListKorbanAdapter"
    }
}
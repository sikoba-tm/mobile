package com.tekmob.sikoba.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.tekmob.sikoba.R
import com.tekmob.sikoba.databinding.KorbanCardBinding
import com.tekmob.sikoba.model.Korban

class ListKorbanAdapter(private val listKorban : List<Korban>, private val context: Context) :
RecyclerView.Adapter<ListKorbanAdapter.ListKorbanHolder>()
{
    inner class ListKorbanHolder(var binding: KorbanCardBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKorbanHolder {
        val binding = KorbanCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListKorbanHolder(binding)
    }

    override fun onBindViewHolder(holder: ListKorbanHolder, position: Int) {
        val korban = listKorban[position]
        holder.apply {
            binding.apply {
                nama.text = korban.nama
                tempatTanggalLahir.text = context.getString(R.string.tempat_tanggal_lahir, korban.tempatLahir, korban.tanggalLahir)
                namaIbuKandung.text = korban.namaIbuKandung
                kondisi.text = korban.kondisi
                foto.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.korban_sample_1))
            }
        }
    }

    override fun getItemCount() : Int = listKorban.size


}
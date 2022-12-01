package com.tekmob.sikoba.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanLocal

class PencarianTersimpanDiffCallback(
    private val oldKorbanList: List<KorbanLocal>,
    private val newKorbanList: List<KorbanLocal>
    ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldKorbanList.size
    }

    override fun getNewListSize(): Int {
        return newKorbanList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldKorbanList[oldItemPosition].id == newKorbanList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldKorban = oldKorbanList[oldItemPosition]
        val newKorban = newKorbanList[newItemPosition]
        return oldKorban.foto == newKorban.foto
    }
}
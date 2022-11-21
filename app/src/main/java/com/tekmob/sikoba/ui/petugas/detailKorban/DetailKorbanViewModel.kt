package com.tekmob.sikoba.ui.petugas.detailKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DetailKorbanViewModel(private val repository: Repository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: String) = repository.getKorban(idBencana, idKorban)
    fun hapusKorban(idBencana: Int, idKorban: String) = repository.hapusKorban(idBencana, idKorban)
}
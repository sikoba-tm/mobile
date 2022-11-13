package com.tekmob.sikoba.ui.petugas.detailKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DetailKorbanViewModel(private val repository: Repository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: Int) = repository.getKorban(idBencana, idKorban)

}
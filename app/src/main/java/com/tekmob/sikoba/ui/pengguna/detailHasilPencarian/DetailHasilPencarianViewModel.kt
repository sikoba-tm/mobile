package com.tekmob.sikoba.ui.pengguna.detailHasilPencarian

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DetailHasilPencarianViewModel(private val repository: Repository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: String) = repository.getKorban(idBencana, idKorban)

}
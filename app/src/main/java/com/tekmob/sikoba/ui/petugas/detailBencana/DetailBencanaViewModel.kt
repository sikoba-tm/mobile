package com.tekmob.sikoba.ui.petugas.detailBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DetailBencanaViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarKorban(idBencana : Int) = repository.getDaftarKorban(idBencana)
}
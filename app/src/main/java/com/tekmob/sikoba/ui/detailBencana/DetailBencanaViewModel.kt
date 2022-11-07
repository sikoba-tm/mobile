package com.tekmob.sikoba.ui.detailBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DetailBencanaViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarKorban(idKorban : Int) = repository.getDaftarKorban(idKorban)
}
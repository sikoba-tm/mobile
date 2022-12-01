package com.tekmob.sikoba.ui.petugas.detailBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class DetailBencanaViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getDaftarKorban(idBencana : Int) = apiRepository.getDaftarKorban(idBencana)
}
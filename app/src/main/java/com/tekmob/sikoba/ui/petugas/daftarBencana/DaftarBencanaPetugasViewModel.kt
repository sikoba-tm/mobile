package com.tekmob.sikoba.ui.petugas.daftarBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class DaftarBencanaPetugasViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getDaftarBencana() = apiRepository.getDaftarBencana()
}
package com.tekmob.sikoba.ui.pengguna.daftarBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class DaftarBencanaViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getDaftarBencana() = apiRepository.getDaftarBencana()


}
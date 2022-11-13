package com.tekmob.sikoba.ui.pengguna.daftarBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DaftarBencanaViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarBencana() = repository.getDaftarBencana()


}
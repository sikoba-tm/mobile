package com.tekmob.sikoba.ui.petugas.daftarBencana

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class DaftarBencanaPetugasViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarBencana() = repository.getDaftarBencana()
}
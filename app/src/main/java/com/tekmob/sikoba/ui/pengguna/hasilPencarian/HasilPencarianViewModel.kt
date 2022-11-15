package com.tekmob.sikoba.ui.pengguna.hasilPencarian

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class HasilPencarianViewModel(private val repository: Repository) : ViewModel() {
    fun getDaftarKorban(idBencana : Int) = repository.getDaftarKorban(idBencana)
}
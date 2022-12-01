package com.tekmob.sikoba.ui.pengguna.hasilPencarian

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class HasilPencarianViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun getDaftarKorban(idBencana : Int) = apiRepository.getDaftarKorban(idBencana)
}
package com.tekmob.sikoba.ui.petugas.detailKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class DetailKorbanViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: String) = apiRepository.getKorban(idBencana, idKorban)
    fun hapusKorban(idBencana: Int, idKorban: String) = apiRepository.hapusKorban(idBencana, idKorban)
}
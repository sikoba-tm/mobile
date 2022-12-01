package com.tekmob.sikoba.ui.pengguna.detailHasilPencarian

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository

class DetailHasilPencarianViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: String) = apiRepository.getKorban(idBencana, idKorban)

}
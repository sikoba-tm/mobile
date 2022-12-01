package com.tekmob.sikoba.ui.petugas.ubahKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UbahKorbanViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun getKorban(idBencana : Int, idKorban: String) = apiRepository.getKorban(idBencana, idKorban)
    fun getDaftarPosko(idBencana: Int) = apiRepository.getDaftarPosko(idBencana)
    fun ubahKorban(idBencana: Int, idKorban: String, foto: MultipartBody.Part?, data: Map<String, RequestBody>) =
        apiRepository.ubahKorban(idBencana, idKorban, foto, data)
}
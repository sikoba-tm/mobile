package com.tekmob.sikoba.ui.petugas.ubahKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UbahKorbanViewModel(private val repository: Repository) : ViewModel() {
    fun getKorban(idBencana : Int, idKorban: String) = repository.getKorban(idBencana, idKorban)
    fun getDaftarPosko(idBencana: Int) = repository.getDaftarPosko(idBencana)
    fun ubahKorban(idBencana: Int, idKorban: String, foto: MultipartBody.Part?, data: Map<String, RequestBody>) =
        repository.ubahKorban(idBencana, idKorban, foto, data)
}
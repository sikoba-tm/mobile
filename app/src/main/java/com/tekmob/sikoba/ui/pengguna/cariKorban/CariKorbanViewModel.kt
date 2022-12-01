package com.tekmob.sikoba.ui.pengguna.cariKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CariKorbanViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    fun cariKorban(idBencana: Int, foto: MultipartBody.Part, data: Map<String, RequestBody>) =
        apiRepository.cariKorban(idBencana, foto, data)
}
package com.tekmob.sikoba.ui.pengguna.cariKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CariKorbanViewModel(private val repository: Repository) : ViewModel() {
    fun cariKorban(idBencana: Int, foto: MultipartBody.Part, data: Map<String, RequestBody>) =
        repository.cariKorban(idBencana, foto, data)
}
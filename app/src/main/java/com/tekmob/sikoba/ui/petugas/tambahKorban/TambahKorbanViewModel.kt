package com.tekmob.sikoba.ui.petugas.tambahKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.remote.repository.ApiRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class TambahKorbanViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getDaftarPosko(idBencana: Int) = apiRepository.getDaftarPosko(idBencana)

    fun tambahKorban(idBencana: Int, idPosko: Int, foto: MultipartBody.Part, data: Map<String, RequestBody>) =
        apiRepository.tambahKorban(idBencana, idPosko, foto, data)

}
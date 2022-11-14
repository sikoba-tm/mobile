package com.tekmob.sikoba.ui.petugas.tambahKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class TambahKorbanViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarPosko(idBencana: Int) = repository.getDaftarPosko(idBencana)

    fun tambahKorban(idBencana: Int, idPosko: Int, foto: MultipartBody.Part, data: Map<String, RequestBody>) =
        repository.tambahKorban(idBencana, idPosko, foto, data)

}
package com.tekmob.sikoba.ui.tambahKorban

import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository

class TambahKorbanViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarPosko(idBencana: Int) = repository.getDaftarPosko(idBencana)

}
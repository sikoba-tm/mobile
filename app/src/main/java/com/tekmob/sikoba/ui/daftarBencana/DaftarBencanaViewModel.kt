package com.tekmob.sikoba.ui.daftarBencana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import java.util.*

class DaftarBencanaViewModel(private val repository: Repository) : ViewModel() {

    fun getDaftarBencana() = repository.getDaftarBencana()


}
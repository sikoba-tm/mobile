package com.tekmob.sikoba.ui.detailKorban

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import com.tekmob.sikoba.model.Korban

class DetailKorbanViewModel(private val repository: Repository) : ViewModel() {

    fun getKorban(idBencana : Int, idKorban: Int) = repository.getKorban(idBencana, idKorban)

}
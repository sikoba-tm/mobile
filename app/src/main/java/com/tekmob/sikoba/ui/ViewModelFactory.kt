package com.tekmob.sikoba.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.di.Injection
import com.tekmob.sikoba.ui.daftarBencana.DaftarBencanaViewModel
import com.tekmob.sikoba.ui.detailBencana.DetailBencanaViewModel
import com.tekmob.sikoba.ui.detailKorban.DetailKorbanViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DaftarBencanaViewModel::class.java) -> {
                DaftarBencanaViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(DetailBencanaViewModel::class.java) -> {
                DetailBencanaViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(DetailKorbanViewModel::class.java) -> {
                DetailKorbanViewModel(Injection.provideRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
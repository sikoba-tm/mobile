package com.tekmob.sikoba.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.di.Injection
import com.tekmob.sikoba.ui.pengguna.cariKorban.CariKorbanViewModel
import com.tekmob.sikoba.ui.pengguna.daftarBencana.DaftarBencanaViewModel
import com.tekmob.sikoba.ui.pengguna.detailHasilPencarian.DetailHasilPencarianViewModel
import com.tekmob.sikoba.ui.pengguna.hasilPencarian.HasilPencarianViewModel
import com.tekmob.sikoba.ui.petugas.daftarBencana.DaftarBencanaPetugasViewModel
import com.tekmob.sikoba.ui.petugas.detailBencana.DetailBencanaViewModel
import com.tekmob.sikoba.ui.petugas.detailKorban.DetailKorbanViewModel
import com.tekmob.sikoba.ui.petugas.tambahKorban.TambahKorbanViewModel

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
            modelClass.isAssignableFrom(TambahKorbanViewModel::class.java) -> {
                TambahKorbanViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(DaftarBencanaPetugasViewModel::class.java) -> {
                DaftarBencanaPetugasViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(CariKorbanViewModel::class.java) -> {
                CariKorbanViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(HasilPencarianViewModel::class.java) -> {
                HasilPencarianViewModel(Injection.provideRepository()) as T
            }
            modelClass.isAssignableFrom(DetailHasilPencarianViewModel::class.java) -> {
                DetailHasilPencarianViewModel(Injection.provideRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
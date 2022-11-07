package com.tekmob.sikoba.ui.daftarBencana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.repository.Repository
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import java.util.*

class DaftarBencanaViewModel(private val repository: Repository) : ViewModel() {


    private val listKorbanTemp : List<Korban> = listOf(
        Korban(
            id = 1,
            createdAt = Calendar.getInstance().time,
            updatedAt = Calendar.getInstance().time,
            rentangUsia = "dewasa",
            nama = "Joko",
            tempatLahir = "Jakarta",
            tanggalLahir = "17 Agustus 2001",
            namaIbuKandung = "Fulanah binti Fulan",
            kondisi = "Luka Ringan",
            foto = null
        ),
        Korban(
            id = 2,
            createdAt = Calendar.getInstance().time,
            updatedAt = Calendar.getInstance().time,
            rentangUsia = "tua",
            nama = "Susilo",
            tempatLahir = "Los Angeles",
            tanggalLahir = "3 Maret 1981",
            namaIbuKandung = "Fulanah binti Fulan",
            kondisi = "Luka Berat",
            foto = null
        ),
        Korban(
            id = 3,
            createdAt = Calendar.getInstance().time,
            updatedAt = Calendar.getInstance().time,
            rentangUsia = "remaja",
            nama = "Frans",
            tempatLahir = "Kepulauan Karibia",
            tanggalLahir = "20 September 2005",
            namaIbuKandung = "Fulanah binti Fulan",
            kondisi = "Tidak Sadarkan Diri",
            foto = null
        ),
    )

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is Daftar Bencana Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _bencana = MutableLiveData<Bencana>()
    val bencana : LiveData<Bencana> = _bencana

    fun getDaftarBencana() = repository.getDaftarBencana()

    fun setBencana(bencana: Bencana){
        _bencana.value = bencana
    }

    private val _listKorban = MutableLiveData<List<Korban>>().apply {
        value = listKorbanTemp
    }
    val listKorban : LiveData<List<Korban>> = _listKorban

}
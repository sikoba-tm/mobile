package com.tekmob.sikoba.ui.daftarBencana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.model.Bencana
import java.util.*

class DaftarBencanaViewModel : ViewModel() {

    private val listBencanaTemp : List<Bencana> = listOf(
        Bencana(
            1,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "Banjir",
            Calendar.getInstance().time,
            "Jakarta"
        ),
        Bencana(
            2,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "Tanah Longsor",
            Calendar.getInstance().time,
            "Bandung"
        ),
        Bencana(
            3,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "Tsunami",
            Calendar.getInstance().time,
            "Makassar"
        )

    )

    private val _text = MutableLiveData<String>().apply {
        value = "This is Daftar Bencana Fragment"
    }
    val text: LiveData<String> = _text

    private val _listBencana = MutableLiveData<List<Bencana>>().apply {
        value = listBencanaTemp
    }
    val listBencana : LiveData<List<Bencana>> = _listBencana


}
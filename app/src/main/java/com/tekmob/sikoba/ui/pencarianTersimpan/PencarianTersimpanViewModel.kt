package com.tekmob.sikoba.ui.pencarianTersimpan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PencarianTersimpanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pencarian Tersimpan"
    }
    val text: LiveData<String> = _text
}
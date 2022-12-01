package com.tekmob.sikoba.ui.pengguna.pencarianTersimpan

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tekmob.sikoba.data.local.LocalRepository
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanAndPosko
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.model.PoskoLocal

class PencarianTersimpanViewModel(application: Application) : ViewModel() {

    private val mLocalRepository: LocalRepository = LocalRepository(application)

    fun getAllKorban(): LiveData<List<KorbanLocal>> = mLocalRepository.getAllKorban()

    fun getKorbanById(id: String) : LiveData<KorbanLocal?> = mLocalRepository.getKorbanById(id)

    fun getKorbanAndPoskoById(id: String) : LiveData<KorbanAndPosko?> = mLocalRepository.getKorbanAndPoskoById(id)

    fun insertPosko(poskoLocal: PoskoLocal) {
        mLocalRepository.insertPosko(poskoLocal)
    }

    fun insertKorban(korban: KorbanLocal) {
        mLocalRepository.insertKorban(korban)
    }

    fun deleteKorban(korban: KorbanLocal) {
        mLocalRepository.deleteKorban(korban)
    }

    fun deleteKorbanById(id: String){
        mLocalRepository.deleteKorbanById(id)
    }
}
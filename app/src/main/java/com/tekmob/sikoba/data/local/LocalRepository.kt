package com.tekmob.sikoba.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanAndPosko
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.model.PoskoLocal
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository(application: Application) {
    
    private val mKorbanDao : KorbanDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = KorbanDatabase.getDatabase(application)
        mKorbanDao = db.korbanDao()
    }

    fun getAllKorban() : LiveData<List<KorbanLocal>> = mKorbanDao.getAllKorban()

    fun getKorbanById(id: String) : LiveData<KorbanLocal?> = mKorbanDao.getKorbanById(id)

    fun getKorbanAndPoskoById(id : String) : LiveData<KorbanAndPosko?> = mKorbanDao.getKorbanAndPoskoById(id)

    fun insertPosko(poskoLocal: PoskoLocal){
        executorService.execute {
            mKorbanDao.insertPosko(poskoLocal)
        }
    }

    fun insertKorban(korban: KorbanLocal) {
        executorService.execute{
            mKorbanDao.insertKorban(korban)
        }
    }

    fun deleteKorban(korban: KorbanLocal){
        executorService.execute {
            mKorbanDao.deleteKorban(korban)
        }
    }

    fun deleteKorbanById(id: String){
        executorService.execute {
            mKorbanDao.deleteKorbanById(id)
        }
    }
}
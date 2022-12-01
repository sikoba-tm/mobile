package com.tekmob.sikoba.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tekmob.sikoba.model.*

@Dao
interface KorbanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKorban(korban: KorbanLocal)

    @Delete
    fun deleteKorban(korban: KorbanLocal)

    @Query("DELETE FROM korban WHERE id = :id")
    fun deleteKorbanById(id : String)

    @Query("SELECT * FROM korban")
    fun getAllKorban() : LiveData<List<KorbanLocal>>

    @Query("SELECT * FROM korban WHERE ID = :id")
    fun getKorbanById(id : String) : LiveData<KorbanLocal?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosko(posko: PoskoLocal)

    @Transaction
    @Query("SELECT * FROM korban WHERE ID = :id")
    fun getKorbanAndPoskoById(id : String) : LiveData<KorbanAndPosko?>

}
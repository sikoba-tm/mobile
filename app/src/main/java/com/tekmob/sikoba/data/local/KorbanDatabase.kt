package com.tekmob.sikoba.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.KorbanLocal
import com.tekmob.sikoba.model.PoskoLocal

@Database(entities = [KorbanLocal::class, PoskoLocal::class], version = 1)
abstract class KorbanDatabase : RoomDatabase() {

    abstract fun korbanDao() : KorbanDao

    companion object{
        @Volatile
        private var INSTANCE: KorbanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : KorbanDatabase {
            if (INSTANCE == null){
                synchronized(KorbanDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        KorbanDatabase::class.java,
                        "sikoba_database"
                    ).build()
                }
            }
            return INSTANCE as KorbanDatabase
        }
    }
}
package com.tekmob.sikoba.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "korban")
@Parcelize
data class KorbanLocal(

    val tanggalLahir: String? = null,

    val foto: String? = null,

    val nama: String? = null,

    val tempatLahir: String? = null,

    val kondisi: String? = null,

    val namaIbuKandung: String? = null,

    @PrimaryKey
    val id: String,

    val rentangUsia: String? = null,

    val poskoId: Int? = null

) : Parcelable

@Entity(tableName = "posko")
@Parcelize
data class PoskoLocal(

    val namaPj: String? = null,

    val nama: String? = null,

    @PrimaryKey
    val id: Int,

    val alamat: String? = null,

    val notelpPj: String? = null

) : Parcelable

data class KorbanAndPosko(
    @Embedded
    val korban: KorbanLocal,

    @Relation(
        parentColumn = "poskoId",
        entityColumn = "id"
    )
    val posko: PoskoLocal
)
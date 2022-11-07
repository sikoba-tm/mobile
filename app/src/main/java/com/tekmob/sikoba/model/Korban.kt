package com.tekmob.sikoba.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Korban(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("created_at")
    val createdAt: Date,

    @field:SerializedName("id")
    val updatedAt: Date,

    @field:SerializedName("nama")
    val nama: String?,

    @field:SerializedName("rentang_usia")
    val rentangUsia: String,

    @field:SerializedName("tempat_lahir")
    val tempatLahir : String?,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir : String?,

    @field:SerializedName("foto")
    val foto: String?,

    @field:SerializedName("nama_ibu_kandung")
    val namaIbuKandung : String?,

    @field:SerializedName("kondisi")
    val kondisi : String

)
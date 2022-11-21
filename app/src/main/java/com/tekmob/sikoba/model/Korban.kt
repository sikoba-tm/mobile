package com.tekmob.sikoba.model

import com.google.gson.annotations.SerializedName

data class Korban(

    @field:SerializedName("tangal_lahir")
    val tangalLahir: String? = null,

    @field:SerializedName("Posko")
    val posko: Posko? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("tempat_lahir")
    val tempatLahir: String? = null,

    @field:SerializedName("kondisi")
    val kondisi: String? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String? = null,

    @field:SerializedName("nama_ibu_kandung")
    val namaIbuKandung: String? = null,

    @field:SerializedName("ID")
    val id: String? = null,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("rentang_usia")
    val rentangUsia: String? = null

)
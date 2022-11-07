package com.tekmob.sikoba.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bencana(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("tanggal_kejadian")
    val tanggalKejadian: String? = null,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String? = null

) : Parcelable

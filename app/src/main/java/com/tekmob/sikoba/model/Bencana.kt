package com.tekmob.sikoba.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bencana(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("created_at")
    val createdAt: Date,

    @field:SerializedName("id")
    val updatedAt: Date,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("tanggal_kejadian")
    val tanggalKejadian: Date,

    @field:SerializedName("lokasi")
    val lokasi: String

) : Parcelable

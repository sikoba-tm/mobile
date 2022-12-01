package com.tekmob.sikoba.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Posko(

	@field:SerializedName("nama_pj")
	val namaPj: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("BencanaID")
	val bencanaID: Int? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@PrimaryKey
	@field:SerializedName("ID")
	val id: Int? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("notelp_pj")
	val notelpPj: String? = null

) : Parcelable

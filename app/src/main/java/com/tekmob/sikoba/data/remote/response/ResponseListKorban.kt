package com.tekmob.sikoba.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseListKorban(

	@field:SerializedName("ResponseListKorban")
	val responseListKorban: List<ResponseListKorbanItem?>? = null
)

data class Posko(

	@field:SerializedName("nama_pj")
	val namaPj: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("BencanaID")
	val bencanaID: Int? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("notelp_pj")
	val notelpPj: String? = null
)

data class ResponseListKorbanItem(

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
	val iD: Int? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("rentang_usia")
	val rentangUsia: String? = null
)

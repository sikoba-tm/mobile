package com.tekmob.sikoba.model

import java.util.*

data class Posko(
    val id: Long,
    val updatedAt : Date,
    val createdAt: Date,
    val nama: String,
    val alamat: String,
    val namaPj: String,
    val noTelpPj: String
)

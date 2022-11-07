package com.tekmob.sikoba.data.remote.retrofit

import com.tekmob.sikoba.model.Bencana
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/bencana")
    fun getAllBencana() : Call<List<Bencana>>
}
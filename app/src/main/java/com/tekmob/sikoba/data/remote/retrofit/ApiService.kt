package com.tekmob.sikoba.data.remote.retrofit

import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/bencana")
    fun getDaftarBencana() : Call<List<Bencana>>

    @GET("/bencana/{id}")
    fun getDetailBencana(
        @Path("id") id : Int
    ) : Call<Bencana>

    @GET("bencana/{id_bencana}/korban")
    fun getDaftarKorban(
        @Path("id_bencana") id : Int
    ) : Call<List<Korban>>
}
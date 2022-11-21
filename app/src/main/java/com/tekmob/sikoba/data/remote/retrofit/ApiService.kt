package com.tekmob.sikoba.data.remote.retrofit

import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.Posko
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/bencana")
    fun getDaftarBencana() : Call<List<Bencana>>

    @GET("/bencana/{id}")
    fun getDetailBencana(
        @Path("id") id : Int
    ) : Call<Bencana>

    @GET("/bencana/{id_bencana}/korban")
    fun getDaftarKorban(
        @Path("id_bencana") id : Int
    ) : Call<List<Korban>>

    @GET("/bencana/{id_bencana}/korban/{id_korban}")
    fun getDetailKorban(
        @Path("id_bencana") idBencana : Int,
        @Path("id_korban") idKorban : String
    ) : Call<Korban>

    @GET("/bencana/{id_bencana}/posko")
    fun getDaftarPosko(
        @Path("id_bencana") idBencana : Int
    ) : Call<List<Posko>>

    @Multipart
    @POST("/bencana/{id_bencana}/posko/{id_posko}")
    fun tambahKorban(
        @Path("id_bencana") idBencana: Int,
        @Path("id_posko") idPosko : Int,
        @Part foto: MultipartBody.Part,
        @PartMap data : Map<String, @JvmSuppressWildcards RequestBody>
    ) : Call<Korban>

    @DELETE("/bencana/{id_bencana}/korban/{id_korban}/")
    fun hapusKorban(
        @Path("id_bencana") idBencana : Int,
        @Path("id_korban") idKorban : String
    ) : Call<ResponseBody>
}
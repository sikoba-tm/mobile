package com.tekmob.sikoba.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tekmob.sikoba.data.remote.retrofit.ApiService
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.Posko
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val apiService: ApiService
){

    private val resultDaftarBencana = MediatorLiveData<Result<List<Bencana>>>()
    private val _responseDaftarBencana = MutableLiveData<List<Bencana>>()
    private val responseDaftarBencana : LiveData<List<Bencana>> = _responseDaftarBencana

    private val resultDaftarKorban = MediatorLiveData<Result<List<Korban>>>()
    private val _responseDaftarKorban = MutableLiveData<List<Korban>>()
    private val responseDaftarKorban : LiveData<List<Korban>> = _responseDaftarKorban

    private val resultKorban = MediatorLiveData<Result<Korban>>()
    private val _responseKorban = MutableLiveData<Korban>()
    private val responseKorban : LiveData<Korban> = _responseKorban

    private val resultDaftarPosko = MediatorLiveData<Result<List<Posko>>>()
    private val _responseDaftarPosko = MutableLiveData<List<Posko>>()
    private val responseDaftarPosko : LiveData<List<Posko>> = _responseDaftarPosko

    fun getDaftarBencana() : LiveData<Result<List<Bencana>>> {
        resultDaftarBencana.value = Result.Loading
        val client = apiService.getDaftarBencana()
        client.enqueue(object : Callback<List<Bencana>> {
            override fun onResponse(call: Call<List<Bencana>>, response: Response<List<Bencana>>) {
                if (response.isSuccessful){
                    _responseDaftarBencana.value = response.body()
                    resultDaftarBencana.addSource(responseDaftarBencana) { res ->
                        resultDaftarBencana.value = Result.Success(res)
                    }
                    resultDaftarBencana.removeSource(responseDaftarBencana)
                } else {
                    resultDaftarBencana.value = Result.Error("Error get daftar bencana")
                }
            }

            override fun onFailure(call: Call<List<Bencana>>, t: Throwable) {
                resultDaftarBencana.value = t.message?.let { Result.Error(it) }
            }
        })
        return resultDaftarBencana
    }

    fun getDaftarKorban(idBencana : Int) : LiveData<Result<List<Korban>>> {
        resultDaftarKorban.value = Result.Loading
        apiService.getDaftarKorban(idBencana).enqueue(object : Callback<List<Korban>> {
            override fun onResponse(call: Call<List<Korban>>, response: Response<List<Korban>>) {
                if (response.isSuccessful) {
                    _responseDaftarKorban.value = response.body()
                    resultDaftarKorban.addSource(responseDaftarKorban) { res ->
                        resultDaftarKorban.value = Result.Success(res)
                    }
                    resultDaftarKorban.removeSource(responseDaftarKorban)
                } else {
                    resultDaftarBencana.value = Result.Error("Error get daftar korban")
                }
            }

            override fun onFailure(call: Call<List<Korban>>, t: Throwable) {
                resultDaftarKorban.value = t.message?.let { Result.Error(it) }
            }

        })

        return resultDaftarKorban
    }

    fun getKorban(idBencana: Int, idKorban : Int) : LiveData<Result<Korban>>{
        resultKorban.value = Result.Loading
        apiService.getDetailKorban(idBencana, idKorban).enqueue(object : Callback<Korban> {
            override fun onResponse(call: Call<Korban>, response: Response<Korban>) {
                if (response.isSuccessful) {
                    _responseKorban.value = response.body()
                    resultKorban.addSource(responseKorban) { res ->
                        resultKorban.value = Result.Success(res)
                    }
                    resultKorban.removeSource(responseKorban)
                } else {
                    resultKorban.value = Result.Error("Error get detail korban")
                }
            }

            override fun onFailure(call: Call<Korban>, t: Throwable) {
                resultKorban.value = t.message?.let { Result.Error(it) }
            }
        })

        return resultKorban
    }

    fun getDaftarPosko(idBencana: Int) : LiveData<Result<List<Posko>>>{
        resultDaftarPosko.value = Result.Loading
        apiService.getDaftarPosko(idBencana).enqueue(object : Callback<List<Posko>> {
            override fun onResponse(call: Call<List<Posko>>, response: Response<List<Posko>>) {
                if (response.isSuccessful) {
                    _responseDaftarPosko.value = response.body()
                    resultDaftarPosko.addSource(responseDaftarPosko) { res ->
                        resultDaftarPosko.value = Result.Success(res)
                    }
                    resultDaftarPosko.removeSource(responseDaftarPosko)
                } else {
                    resultDaftarPosko.value = Result.Error("Error get daftar posko")
                }
            }

            override fun onFailure(call: Call<List<Posko>>, t: Throwable) {
                resultDaftarPosko.value = t.message?.let { Result.Error(it) }
            }

        })

        return resultDaftarPosko
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}
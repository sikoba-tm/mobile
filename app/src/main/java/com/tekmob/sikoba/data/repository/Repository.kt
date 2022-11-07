package com.tekmob.sikoba.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tekmob.sikoba.data.remote.retrofit.ApiService
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.data.remote.response.ResponseListBencana
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val apiService: ApiService
){

    private val resultDaftarBencana = MediatorLiveData<Result<List<Bencana>>>()
    private val _responseDaftarBencana = MutableLiveData<List<Bencana>>()
    private val responseDaftarBencana : LiveData<List<Bencana>> = _responseDaftarBencana

    fun getDaftarBencana() : LiveData<Result<List<Bencana>>> {
        resultDaftarBencana.value = Result.Loading
        val client = apiService.getAllBencana()
        client.enqueue(object : Callback<List<Bencana>> {
            override fun onResponse(call: Call<List<Bencana>>, response: Response<List<Bencana>>) {
                if (response.isSuccessful){
                    _responseDaftarBencana.value = response.body()
                    resultDaftarBencana.addSource(responseDaftarBencana) { res ->
                        resultDaftarBencana.value = Result.Success(res)
                    }
                } else {
                    resultDaftarBencana.value = Result.Error("Error")
                }
            }

            override fun onFailure(call: Call<List<Bencana>>, t: Throwable) {
                resultDaftarBencana.value = t.message?.let { Result.Error(it) }
            }
        })
        return resultDaftarBencana
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
package com.tekmob.sikoba.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tekmob.sikoba.data.remote.retrofit.ApiService
import com.tekmob.sikoba.data.Result
import com.tekmob.sikoba.model.Bencana
import com.tekmob.sikoba.model.Korban
import com.tekmob.sikoba.model.Posko
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository private constructor(
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

    private val resultTambahKorban = MediatorLiveData<Result<Korban>>()
    private val _responseTambahKorban = MutableLiveData<Korban>()
    private val responseTambahKorban : LiveData<Korban> = _responseTambahKorban

    private val resultUbahKorban = MediatorLiveData<Result<Korban>>()
    private val _responseUbahKorban = MutableLiveData<Korban>()
    private val responseUbahKorban : LiveData<Korban> = _responseUbahKorban

    private val resultCariKorban = MediatorLiveData<Result<List<Korban>>>()
    private val _responseCariKorban = MutableLiveData<List<Korban>>()
    private val responseCariKorban : LiveData<List<Korban>> = _responseCariKorban

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

    fun getKorban(idBencana: Int, idKorban : String) : LiveData<Result<Korban>>{
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

    fun tambahKorban(
        idBencana: Int,
        idPosko: Int,
        foto: MultipartBody.Part,
        data: Map<String, RequestBody>
    ) : LiveData<Result<Korban>> {
        resultTambahKorban.value = Result.Loading
        apiService.tambahKorban(idBencana, idPosko, foto, data).enqueue(object : Callback<Korban> {
            override fun onResponse(call: Call<Korban>, response: Response<Korban>) {
                if (response.isSuccessful) {
                    _responseTambahKorban.value = response.body()
                    resultTambahKorban.addSource(responseTambahKorban) { res ->
                        resultTambahKorban.value = Result.Success(res)
                    }
                    resultTambahKorban.removeSource(responseTambahKorban)
                } else {
                    resultTambahKorban.value = Result.Error("Error tambah korban")
                }
            }

            override fun onFailure(call: Call<Korban>, t: Throwable) {
                resultTambahKorban.value = t.message?.let { Result.Error(it) }
            }

        })

        return resultTambahKorban
    }

    fun ubahKorban(
        idBencana: Int,
        idKorban: String,
        foto: MultipartBody.Part?,
        data: Map<String, RequestBody>
    ) : LiveData<Result<Korban>> {
        resultUbahKorban.value = Result.Loading
        apiService.ubahKorban(idBencana, idKorban, foto, data).enqueue(object : Callback<Korban> {
            override fun onResponse(call: Call<Korban>, response: Response<Korban>) {
                if (response.isSuccessful) {
                    _responseUbahKorban.value = response.body()
                    resultUbahKorban.addSource(responseUbahKorban) { res ->
                        resultUbahKorban.value = Result.Success(res)
                    }
                    resultUbahKorban.removeSource(responseUbahKorban)
                } else {
                    resultUbahKorban.value = Result.Error("Error ubah korban")
                }
            }
            override fun onFailure(call: Call<Korban>, t: Throwable) {
                resultUbahKorban.value = t.message?.let { Result.Error(it) }
            }
        })
        return resultUbahKorban
    }

    fun hapusKorban(idBencana: Int, idKorban : String){
        apiService.hapusKorban(idBencana, idKorban).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("ApiRepository", "Success hapus")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.message?.let { Log.e("ApiRepository", it) }
            }

        })
    }

    fun cariKorban(
        idBencana: Int,
        foto: MultipartBody.Part,
        data: Map<String, RequestBody>
    ) : LiveData<Result<List<Korban>>> {
        resultCariKorban.value = Result.Loading
        apiService.cariKorban(idBencana, foto, data).enqueue(object : Callback<List<Korban>> {
            override fun onResponse(call: Call<List<Korban>>, response: Response<List<Korban>>) {
                if (response.isSuccessful) {
                    _responseCariKorban.value = response.body()
                    resultCariKorban.addSource(responseCariKorban) { res ->
                        resultCariKorban.value = Result.Success(res)
                    }
                    resultCariKorban.removeSource(responseCariKorban)
                } else {
                    resultCariKorban.value = Result.Error("Error cari korban")
                }
            }

            override fun onFailure(call: Call<List<Korban>>, t: Throwable) {
                resultCariKorban.value = t.message?.let { Result.Error(it) }
            }
        })
        return resultCariKorban
    }

    companion object {
        @Volatile
        private var instance: ApiRepository? = null
        fun getInstance(
            apiService: ApiService
        ) : ApiRepository =
            instance ?: synchronized(this) {
                instance ?: ApiRepository(apiService)
            }.also { instance = it }
    }
}
package com.tekmob.sikoba.di

import com.tekmob.sikoba.data.remote.retrofit.ApiConfig
import com.tekmob.sikoba.data.remote.repository.ApiRepository

object Injection {

    fun provideRepository(): ApiRepository {
        val apiService = ApiConfig.getApiService()
        return ApiRepository.getInstance(apiService)
    }
}
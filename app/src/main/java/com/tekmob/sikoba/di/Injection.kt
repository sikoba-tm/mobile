package com.tekmob.sikoba.di

import android.content.Context
import com.tekmob.sikoba.data.remote.retrofit.ApiConfig
import com.tekmob.sikoba.data.repository.Repository

object Injection {

    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}
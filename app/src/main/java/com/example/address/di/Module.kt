package com.example.address.di

import com.example.address.BuildConfig
import com.example.address.data.AddressRepository
import com.example.address.data.InfoRepository
import com.example.address.data.network.*
import com.example.address.presentation.features.address.AddressViewModel
import com.example.address.presentation.features.map.MapsViewModel
import okhttp3.Credentials
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiInterceptor(Credentials.basic(BuildConfig.USERNAME, BuildConfig.PASSWORD)))
                    .build()
            )
            .build().create(ApiServices::class.java)
    }
}
val mainModule = module {
    viewModel { MapsViewModel(get()) }
    single { InfoRepository(get()) }
    viewModel { AddressViewModel(get()) }
    single { AddressRepository(get()) }
}
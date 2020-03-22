package com.ing.repoapp.core.di

import android.content.Context
import com.ing.repoapp.AndroidApplication
import com.ing.repoapp.BuildConfig
import com.ing.repoapp.feature.data.Network
import com.ing.repoapp.feature.domain.repositories.ReposRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides @Singleton fun provideReposRepository(dataSource: Network): ReposRepository = dataSource

}

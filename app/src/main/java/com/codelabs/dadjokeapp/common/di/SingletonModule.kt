package com.codelabs.dadjokeapp.common.di

import android.content.Context
import com.codelabs.dadjokeapp.common.RxSchedulers
import com.codelabs.dadjokeapp.common.StringLoader
import com.codelabs.dadjokeapp.networking.DadJokeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://icanhazdadjoke.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideDadJokeService(retrofit: Retrofit): DadJokeService = retrofit.create(DadJokeService::class.java)

    @Singleton
    @Provides
    fun provideRxSchedulers() = RxSchedulers.invoke()

    @Singleton
    @Provides
    fun provideStringLoader(@ApplicationContext context: Context) = StringLoader.invoke(context)

}
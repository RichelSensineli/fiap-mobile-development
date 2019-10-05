package br.com.fiap.mycontactlist.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    private val okHttp = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://vast-earth-70498.herokuapp.com/")
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun userService(): UserService = retrofit.create(UserService::class.java)

    fun contactService(): ContactService = retrofit.create(ContactService::class.java)
}
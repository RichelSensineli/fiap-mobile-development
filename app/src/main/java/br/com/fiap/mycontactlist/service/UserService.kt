package br.com.fiap.mycontactlist.service

import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("users")
    fun getUsersList(@QueryMap filter: HashMap<String, String>) : Call<List<User>>

    @GET("users/{uid}")
    fun getUser(@Path("id") id: Int): Call<User>

    @POST("users")
    fun addUser(@Body newUser: User): Call<User>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("phone") phone: Int,
        @Field("email") email: String
    ): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ): Call<Unit>
}
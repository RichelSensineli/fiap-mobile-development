package br.com.fiap.mycontactlist.service

import br.com.fiap.mycontactlist.model.Contact
import retrofit2.Call
import retrofit2.http.*

interface ContactService {

    @GET("list")
    fun getContactList(@QueryMap filter: HashMap<String, String>) : Call<List<Contact>>

    @GET("contact/{id}")
    fun getContact(@Path("id") id: Int): Call<Contact>

    @POST("https://vast-earth-70498.herokuapp.com/users/{id}/contact")
    fun addContact(@Path("id") id: String, @Body newContact: Contact): Call<Contact>

    @PUT("contact/{id}")
    fun updateContact(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("phone") phone: Int,
        @Field("email") email: String
    ): Call<Contact>

    @DELETE("contact/{id}")
    fun deleteContact(@Path("id") id: Int): Call<Unit>
}
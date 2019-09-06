package br.com.fiap.mycontactlist.service

import br.com.fiap.mycontactlist.model.Contact
import retrofit2.Call
import retrofit2.http.*

interface ContactService {

    @GET("list")
    fun getContactList(@QueryMap filter: HashMap<String, String>) : Call<List<Contact>>

    @GET("contact/{id}")
    fun getContact(@Path("id") id: Int): Call<Contact>

    @POST("contact")
    fun addContact(@Body newContact: Contact): Call<Contact>

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
package br.com.fiap.mycontactlist.service

import br.com.fiap.mycontactlist.model.Contact
import retrofit2.Call
import retrofit2.http.*

interface ContactService {

    @GET("users/{uid}/contacts")
    fun getContactList(@QueryMap filter: HashMap<String, String>) : Call<List<Contact>>

    @GET("users/{uid}/contacts/{id}")
    fun getContact(@Path("uid") uid: Int, @Path("id") id: Int): Call<Contact>

    @POST("users/{uid}/contact")
    fun addContact(@Path("uid") id: String, @Body newContact: Contact): Call<Contact>

    @PUT("users/{uid}/contacts/{id}")
    fun updateContact(
        @Path("uid") uid: Int,
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("phone") phone: Int,
        @Field("email") email: String
    ): Call<Contact>

    @DELETE("users/{uid}/contacts/{id}")
    fun deleteContact(@Path("uid") uid: Int,@Path("id") id: Int): Call<Unit>
}

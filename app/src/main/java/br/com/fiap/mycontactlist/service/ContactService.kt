package br.com.fiap.mycontactlist.service

import br.com.fiap.mycontactlist.model.Contact
import retrofit2.Call
import retrofit2.http.*

interface ContactService {

    @GET("users/{uid}/contacts")
    fun getContactList(
        @Path("uid") uid: String
    ) : Call<ArrayList<Contact>>

    @GET("users/{uid}/contacts/{id}")
    fun getContact(
        @Path("uid") uid: String,
        @Path("id") id: Int
    ): Call<Contact>

    @POST("users/{uid}/contact")
    fun addContact(
        @Path("uid") uid: String,
        @Body newContact: Contact
    ): Call<Contact>

    @PUT("users/{uid}/contacts/{id}")
    fun updateContact(
        @Path("uid") uid: String,
        @Path("id") id: Int,
        @Body updateContact: Contact
    ): Call<Contact>

    @DELETE("users/{uid}/contacts/{id}")
    fun deleteContact(
        @Path("uid") uid: String,
        @Path("id") id: Int
    ) : Call<Unit>

}

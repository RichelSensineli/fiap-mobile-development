package br.com.fiap.mycontactlist.model

data class Contact(
    var id: Int = 0,
    var name: String? = null,
    var phone: Int? = 0,
    var email: String? = null
)
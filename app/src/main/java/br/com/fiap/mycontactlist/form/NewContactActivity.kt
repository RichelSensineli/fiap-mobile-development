package br.com.fiap.mycontactlist.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.ContactService
import br.com.fiap.mycontactlist.service.ServiceBuilder
import kotlinx.android.synthetic.main.activity_new_contact.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        btSave.setOnClickListener {
            val newContact = Contact()
            newContact.name = etName.text.toString()
            newContact.phone = Integer.parseInt(etPhone.text.toString())
            newContact.email = etEmail.text.toString()

            val contactService = ServiceBuilder.buildService(ContactService::class.java)
            val requestCall = contactService.addContact(newContact)

            val context = this

            requestCall.enqueue(object: Callback<Contact> {

                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var newlyCreatedContact = response.body() // Use it or ignore it
                        Toast.makeText(context, "Contact Successfully Saved", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to Save Contact", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    Toast.makeText(context, "Failed to Save Contact", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

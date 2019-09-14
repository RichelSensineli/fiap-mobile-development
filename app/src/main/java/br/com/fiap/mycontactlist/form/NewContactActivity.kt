package br.com.fiap.mycontactlist.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_new_contact.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        btSaveNewContact.setOnClickListener {
            val newContact = Contact()
            val userid = intent.getStringExtra("userId")

            System.out.println("USER ID: "+ userid)

            newContact.name = etNameNewContact.text.toString()
            newContact.phone = Integer.parseInt(etPhoneNewContact.text.toString())
            newContact.email = etEmailNewContact.text.toString()

            val context = this

            val call = RetrofitInitializer().contactService().addContact(userid, newContact)
            call.enqueue(object: Callback<Contact?> {
                override fun onResponse(call: Call<Contact?>?,
                                        response: Response<Contact?>) {
                    if (response.isSuccessful) {
                        finish()
                        response?.let {
                            val Contact = it.body()
                        }
                        Toast.makeText(context, "Contact Successfully Saved", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(context, "Failed to Save Contact", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Contact?>?,
                                       t: Throwable?) {
                    Toast.makeText(context, "Failed to Save Contact", Toast.LENGTH_SHORT).show()
                    Log.e("onFailure error", t?.message)
                }
            })
        }
    }
}

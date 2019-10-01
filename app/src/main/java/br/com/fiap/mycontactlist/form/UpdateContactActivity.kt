package br.com.fiap.mycontactlist.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.model.Contact
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_contact.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateContactActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var userid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_contact)

        carregaDados()

        btUpdateContact.setOnClickListener {

            mAuth = FirebaseAuth.getInstance()

            //Capturando informacoes do contato da tela anterior
            val contactId = intent.getIntExtra("contactId", 0)

            val updateContact = Contact()

            //Definindo os novos atributos do contato
            updateContact.name = etUpdateContactName.text.toString()
            updateContact.phone = Integer.parseInt(etUpdateContactPhone.text.toString())
            updateContact.email = etUpdateContactEmail.text.toString()
            updateContact.id = contactId

            //Chamar o método de atualização do contato
            updateContact(contactId, updateContact)
        }
    }

    private fun carregaDados() {

        val contactName = intent.getStringExtra("contactName")
        val contactPhone = intent.getStringExtra("contactPhone")
        val contactEmail = intent.getStringExtra("contactEmail")

        etUpdateContactName.setText(contactName)
        etUpdateContactPhone.setText(contactPhone)
        etUpdateContactEmail.setText(contactEmail)
    }

    private fun updateContact(contactId: Int, updateContact: Contact) {
        userid = mAuth.currentUser?.uid ?: ""
        val context = this
        val call =
            RetrofitInitializer().contactService().updateContact(userid, contactId, updateContact)

        call.enqueue(object : Callback<Contact?> {
            override fun onResponse(
                call: Call<Contact?>?, response: Response<Contact?>
            ) {
                if (response.isSuccessful) {
                    finish()
                    response?.let {
                        val Contact = it.body()
                    }
                    Toast.makeText(context, "Contact Successfully updated", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Failed to update Contact", Toast.LENGTH_SHORT).show()
                    System.err.println(response)
                }
            }

            override fun onFailure(call: Call<Contact?>?, t: Throwable?) {
                Toast.makeText(context, "Failed to update Contact", Toast.LENGTH_SHORT).show()
                Log.e("onFailure error", t?.message)
            }
        })
    }
}

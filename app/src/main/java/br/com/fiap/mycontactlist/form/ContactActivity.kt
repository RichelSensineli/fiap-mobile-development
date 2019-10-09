package br.com.fiap.mycontactlist.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.fiap.mycontactlist.R
import br.com.fiap.mycontactlist.service.RetrofitInitializer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact.btUpdateContact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var userid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        mAuth = FirebaseAuth.getInstance()
        val contactId = intent.getIntExtra("contactId", 0)
        val contactName = intent.getStringExtra("contactName")
        val contactPhone = intent.getStringExtra("contactPhone")
        val contactEmail = intent.getStringExtra("contactEmail")

        tvContactName.text = contactName
        tvContactPhone.text = contactPhone
        tvContactEmail.text = contactEmail

        //Chamar próxima tela passando o contactId
        btUpdateContact.setOnClickListener {
            val proximaTela = Intent(this, UpdateContactActivity::class.java)
            
            proximaTela.putExtra("contactId", contactId)
            proximaTela.putExtra("contactName", contactName)
            proximaTela.putExtra("contactPhone", contactPhone)
            proximaTela.putExtra("contactEmail", contactEmail)

            startActivity(proximaTela)
            finish()
        }

        btDeleteContact.setOnClickListener {
            userid = mAuth.currentUser?.uid ?: ""
            deleteContact(contactId)
        }
    }

    private fun deleteContact(contactId: Int){
        val context = this

        val call = RetrofitInitializer().contactService().deleteContact(userid, contactId)
        call.enqueue(object: Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>?,response: Response<Unit?>
            ) {
                if (response.isSuccessful) {
                    finish()
                    response?.let {
                        val Contact = it.body()
                    }
                    Toast.makeText(context, R.string.contact_deleted, Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, R.string.failed_delete, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit?>?, t: Throwable?) {
                Toast.makeText(context, R.string.failed_delete, Toast.LENGTH_SHORT).show()
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
